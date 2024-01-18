package com.vn.napas.controller;

import com.vn.napas.config.PropertiesConfig;
import com.vn.napas.config.kafka.producer.KafkaProducer;
import com.vn.napas.model.kafka.Pacs08Message;
import com.vn.napas.model.pacs08.JsonObjectPacs08;
import com.vn.napas.model.response.ApiXMConverterResponse;
import com.vn.napas.model.response.ResponseObjectFalse;
import com.vn.napas.model.response.ValidateXMConverterResponse;
import com.vn.napas.service.NrtService;
import com.vn.napas.service.ValidateService;
import com.vn.napas.utils.Constants;
import com.vn.napas.utils.Convert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("ACH (VAS Online)/v1")
@RequiredArgsConstructor
@Slf4j
public class MXConverterController {
    @Autowired
    ValidateService validateService;

    @Autowired
    NrtService nrtService;
    @Autowired
    KafkaProducer kafkaProducer;
    @Autowired
    PropertiesConfig propertiesConfig;
    @PutMapping(value = "/{kindOfMessage}/{senderId}/{service}/{messageIdentifier}/{refId}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> ConvertJsonToXML(
            @PathVariable("kindOfMessage") String kindOfMessage,
            @PathVariable("senderId") String senderId,
            @PathVariable("service") String service,
            @PathVariable("messageIdentifier") String messageIdentifier,
            @PathVariable("refId") String refId,
            @RequestBody String objectPacJson) throws Exception {
        String JsonObjData = "";
        log.info("Request for CONVERTJSONTOXML --- MX CONVESTER--TO ACH"+objectPacJson);
        ApiXMConverterResponse apiXMConverterResponse = new ApiXMConverterResponse();
        log.info("Converting JSONtoXML: ");
        if (messageIdentifier.contentEquals(Constants.Pass008)) {
            ValidateXMConverterResponse validateXMConverterResponse = new ValidateXMConverterResponse();
            JsonObjectPacs08 pacs08Obj = Convert.convertJsonToJsonObjectPacs08(objectPacJson);
            Pacs08Message message = Pacs08Message.builder().pacs08(pacs08Obj)
                    .kindOfMessage(kindOfMessage)
                    .senderId(senderId)
                    .service(service)
                    .messageIdentifier(messageIdentifier)
                    .refId(refId).build();
            log.info("validate Message: ");


            ValidateXMConverterResponse validateData = validateService.validateXMConverter(kindOfMessage, senderId, service, messageIdentifier, refId, pacs08Obj);
            apiXMConverterResponse.setType(validateData.getType());
            apiXMConverterResponse.setDuplicate(validateData.getDuplicate());
            apiXMConverterResponse.setMessage(validateData.getMessage());

            if (validateData.getCode() != 200) {
                switch (validateData.getCode()) {
                    case 404:
                        log.info("Reponse: {}", Convert.convertObjectToJson(apiXMConverterResponse));
                        return new ResponseEntity<>(apiXMConverterResponse, HttpStatus.NOT_FOUND);
                    default:
                        log.info("Reponse: {}", Convert.convertObjectToJson(apiXMConverterResponse));
                        return new ResponseEntity<>(apiXMConverterResponse, HttpStatus.BAD_REQUEST);
                }
            } else {
//            Xu ly data
                log.info("process data");

                JsonObjectPacs08 newObjectPac = validateService.RountPaymentForNRTFromBank(pacs08Obj);
//            JsonObjectPacs08 newObjectPac = objectPacJson;
                log.info("NHTV gửi lệnh Pacs008 thực hiện covert JSON sang XML:");
                JsonObjData = Convert.convertJsonToXml(newObjectPac);
                log.info("Xử lý xong:" + JsonObjData.toString());
                log.info("B4. Put request message Pacs.008");
                validateXMConverterResponse = nrtService.processJSONPacs008ToXML(JsonObjData.toString());

            }

        } else if (messageIdentifier.contentEquals(Constants.Pass002)) {
            ValidateXMConverterResponse validateXMConverterResponse = new ValidateXMConverterResponse();
            JsonObjData = Convert.convertJsonToXml(objectPacJson);
            log.info("Ngân hàng nhận lệnh xử lí bản tin json pacs.002 và gửi lại bản tin xml pacs.002 về ACH -> request: {}", JsonObjData.toString());
            validateXMConverterResponse = nrtService.processJSONPacs002ToXML(JsonObjData.toString());

            apiXMConverterResponse.setDuplicate(validateXMConverterResponse.getDuplicate());
            apiXMConverterResponse.setMessage(validateXMConverterResponse.getMessage());
            apiXMConverterResponse.setType(validateXMConverterResponse.getType());



        }else{
            ResponseObjectFalse rof = new ResponseObjectFalse();
            rof.setCode(500);
            rof.setMessage(Constants.SYSTEM_ERROR);
            return new ResponseEntity<>(rof, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("Reponse: {}", Convert.convertObjectToJson(apiXMConverterResponse));
        return new ResponseEntity<>(apiXMConverterResponse, HttpStatus.OK);
    }
    @PutMapping(value = "/{kindOfMessage}/{senderId}/{service}/{messageIdentifier}/{refId}",consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> ConvertXMLToJson(
            @PathVariable("kindOfMessage") String kindOfMessage,
            @PathVariable("senderId") String senderId,
            @PathVariable("service") String service,
            @PathVariable("messageIdentifier") String messageIdentifier,
            @PathVariable("refId") String refId,
            @RequestBody String objectPac) throws Exception {
    /*
        ACH Xác nhận đã nhận bản tin với ngân hàng gửi lệnh
     */
        log.info("Request for CONVERTJSONTOXML --- MX CONVESTER--TO BANK"+objectPac);
        log.info("Converting XML to JSON");
        ApiXMConverterResponse apiXMConverterResponse = new ApiXMConverterResponse();
        ValidateXMConverterResponse validateXMConverterResponse = new ValidateXMConverterResponse();
        switch (messageIdentifier){
            case Constants.stpack:
                log.info("ACH Xác nhận đã nhận bản tin");
                validateXMConverterResponse  = nrtService.processAchConfirmBank(objectPac);
                break;
            case  Constants.Pass008:

//       ACH gửi bản tin pacs008 đến MX Converter để gửi sang ngân hàng nhận lệnh
                log.info("B8. Put message Pacs.008(XML)");
                validateXMConverterResponse = nrtService.processAchSendPacs008(objectPac);
                 break;
            case  Constants.camt0025:

//       ACH gửi bản tin camt.025 sang MX Converter để gửi sang ngân hàng nhận lệnh

                validateXMConverterResponse = nrtService.processAchSendCamt025BankReceiver(objectPac);
                break;
            case Constants.Pass002:
                validateXMConverterResponse = nrtService.processAchSendPacs002ToBank(objectPac);
                break;
            default:
                validateXMConverterResponse.setDuplicate(false);
                validateXMConverterResponse.setMessage(Constants.MESS_WRONGNRTFORMAT);
                validateXMConverterResponse.setType(Constants.TYPE_FAIL);
        }

        apiXMConverterResponse.setDuplicate(validateXMConverterResponse.getDuplicate());
        apiXMConverterResponse.setMessage(validateXMConverterResponse.getMessage());
        apiXMConverterResponse.setType(validateXMConverterResponse.getType());
        log.info("Reponse: {}", Convert.convertObjectToJson(apiXMConverterResponse));
        return new ResponseEntity<>(apiXMConverterResponse, HttpStatus.OK);


    }
}
