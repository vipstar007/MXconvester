package com.vn.napas.controller;

import com.vn.napas.config.PropertiesConfig;
import com.vn.napas.config.kafka.producer.KafkaProducer;
import com.vn.napas.model.kafka.Pacs08Message;
import com.vn.napas.model.pacs08.JsonObjectPacs08;
import com.vn.napas.model.response.ValidateXMConverterResponse;
import com.vn.napas.service.ValidateService;
import com.vn.napas.utils.Convert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequestMapping("/ach/v1")
@RequiredArgsConstructor
public class BankSenderMXConverterController {

    //private final KafkaTemplate<String, Pacs08Message> kafkaProducer;
    private final PropertiesConfig propertiesConfig;
    private final ValidateService validateService;
    private final KafkaProducer kafkaProducer;

    /*
      Ngân hàng thành viện gửi bản tin pacs008 và thực hiện convert Json sang XML gửi sang ACH
   */
    @PutMapping("/{kindOfMessage}/{senderId}/{service}/{messageIdentifier}/{refId}")
    public ResponseEntity<?> MXConverter(
            @PathVariable("kindOfMessage") String kindOfMessage,
            @PathVariable("senderId") String senderId,
            @PathVariable("service") String service,
            @PathVariable("messageIdentifier") String messageIdentifier,
            @PathVariable("refId") String refId,
            @RequestBody JsonObjectPacs08 objectPacs08) throws Exception{

        Pacs08Message message = Pacs08Message.builder().pacs08(objectPacs08)
                .kindOfMessage(kindOfMessage)
                .senderId(senderId)
                .service(service)
                .messageIdentifier(messageIdentifier)
                .refId(refId).build();

        ValidateXMConverterResponse validateData = validateService.validateXMConverter(kindOfMessage, senderId, service, messageIdentifier, refId, objectPacs08);
        if (validateData != null) {
            return new ResponseEntity<>(validateData, HttpStatus.valueOf(validateData.getCode()));
        }

        String body = Convert.convertObjectToJson(message);

        ValidateXMConverterResponse response = kafkaProducer.sendMessage(propertiesConfig.getTopicBankSenderMxConvert(),propertiesConfig.getTopicConsumer(), body);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
