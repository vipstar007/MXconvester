package com.vn.napas.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vn.napas.config.PropertiesConfig;
import com.vn.napas.config.kafka.producer.KafkaProducer;
import com.vn.napas.model.pacs08.JsonObjectPacs08;
import com.vn.napas.model.pacs08.JsonObjectPacs08XML;
import com.vn.napas.model.response.AchSendPacs008Response;
import com.vn.napas.model.response.ValidateXMConverterResponse;
import com.vn.napas.service.HttpService;
import com.vn.napas.service.NrtService;
import com.vn.napas.service.SignatureService;
import com.vn.napas.service.ValidateService;
import com.vn.napas.utils.Convert;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class NrtServiceImpl implements NrtService {

    private final PropertiesConfig propertiesConfig;
    private final HttpService httpService;
    private final SignatureService signatureService;
    private final KafkaProducer kafkaProducer;
    @Autowired
    ValidateService validateService;

    @Override
    public ValidateXMConverterResponse callApiBankSenderMXConverter(String kindOfMessage, String senderId, String service, String messageIdentifier, String refId, JsonObjectPacs08 objectPacs08) {
        try {
            log.info("Nrt Call Receiver Bank");
            String body = Convert.convertJsonToXml(objectPacs08);
            log.info("convert Json -> xml: {}", body);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_XML);
//            log.info("slpeep 5 s");
//            Thread.sleep(50000);
            ResponseEntity<String> response = httpService.exchange(propertiesConfig.getApiACH008(), HttpMethod.PUT, body, headers);
            return getResponseEntity(response);
        } catch (Exception exception) {
            log.error("Exception");
            return null;
        }
    }

    @Override
    public ValidateXMConverterResponse callApiMXConverterCallACH(String body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        log.info("Thực hiện chuyển bản tin pacs.008 sau conver từ json sang XML từ NHTV");
        log.info("Tiền hành gửi ACH");
        log.info("B4");
        log.info("MXConverter calling :",propertiesConfig.getApiACH008());
        ResponseEntity<String> response = httpService.exchange(propertiesConfig.getApiACH008(), HttpMethod.PUT, body, headers);
        return getResponseEntity(response);
    }



    private static ValidateXMConverterResponse getResponseEntity(ResponseEntity<String> response) {
        if (response != null && response.getStatusCode().value() == 200) {
            log.info("status code: {}", response.getStatusCode());
            return ValidateXMConverterResponse
                    .builder()
                    .type("success")
                    .message("Message successfully processed")
                    .duplicate(false).build();
        }
        return ValidateXMConverterResponse
                .builder()
                .type("false")
                .message("Message false processed")
                .duplicate(true).build();
    }

    @Override
    public ValidateXMConverterResponse processAchSendPacs008(String request) {
        log.info("ACH gửi bản tin pacs.008 tới MX Convert -> Convert MXL sang Json -> request: {}", request);
        try {

            String data = Convert.convertXmlToJson(request);
            log.info("data convert: {}", data);

            JsonNode payload = Convert.convertStringToJsonNode(data);
            log.info("node: {}", payload.get("Body"));

            JsonNode header = signatureService.getHeaderAchSendPacs008();
            log.info("header: {}", header);

            AchSendPacs008Response dataConvert = AchSendPacs008Response.builder().Header(header).Payload(payload.get("Body")).build();
            log.info("response: {}", dataConvert);

            String json = Convert.convertObjectToJson(dataConvert);
            //            xu ly data
//            JsonNode newData = Convert.convertStringToJsonNode(json);
            JsonObjectPacs08XML newData = Convert.convertJsonToJsonObjectPacs08XML(json);
            JsonObjectPacs08 newPacs008 = Convert.convertJsonObjectPacs08XMLToJsonObjectPacs08(newData);
//            JsonObjectPacs08 newPacs008 = Convert.convertJsonToJsonObjectPacs08(json);
            JsonObjectPacs08 newObjectPac  = validateService.RountPaymentForNRTFromACH(newPacs008);
            String strNewData = Convert.convertObjectToJson(newObjectPac);
            return kafkaProducer.sendMessage(propertiesConfig.getTopicAchSendPacs008ToBankSender(), propertiesConfig.getTopicConsumer(), strNewData);
        } catch (Exception ex) {
            log.error("Ach Send Pacs008 error: {}", ex.getMessage());
            return ValidateXMConverterResponse
                    .builder()
                    .type("false")
                    .message("Message false processed")
                    .duplicate(true).build();
        }
    }

    @Override
    public ValidateXMConverterResponse callApiMxConvertSendPacs008(String body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<String> response = httpService.exchange(propertiesConfig.getApiAchSendPacs008ToBankReceiver(), HttpMethod.PUT, body, headers);
        return getResponseEntity(response);
    }


    @Override
    public ValidateXMConverterResponse processJSONPacs008ToXML(String request) {

        try {

            log.info("B1. Bank sender gửi pacs008 sang cho MX -> converst sang xml rồi chuyển cho ACH", request);

            return kafkaProducer.sendMessage(propertiesConfig.getTopicBankSenderMxConvert(), propertiesConfig.getTopicConsumer(), request);
        } catch (Exception ex) {
            log.error("data convert json -> xml error: {}", ex.getMessage());
            System.out.println(ex.getMessage());
            return ValidateXMConverterResponse
                    .builder()
                    .type("false")
                    .message("Message false processed")
                    .duplicate(true).build();
        }
    }
    @Override
    public ValidateXMConverterResponse processJSONPacs002ToXML(String request) {

        try {

            log.info("B11. Bank receiver chuyen pacs002 đến ACH", request);

            return kafkaProducer.sendMessage(propertiesConfig.getTopicBankSenderProcessPacs008(), propertiesConfig.getTopicConsumer(), request);
        } catch (Exception ex) {
            log.error("data convert json -> xml error: {}", ex.getMessage());
            System.out.println(ex.getMessage());
            return ValidateXMConverterResponse
                    .builder()
                    .type("false")
                    .message("Message false processed")
                    .duplicate(true).build();
        }
    }



    @Override
    public ValidateXMConverterResponse processAchConfirmBank(String message) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        log.info("B7.ACH Sent Confirm message ACK to Bank");

        String data = Convert.convertXmlToJson(message);
        log.info("put request message ACK nack (Json)",data);
        log.info("send request message to Bank Sender");
        log.info("MX Converter calling Bank Sender:",propertiesConfig.getTopicMxConvertAch());
        try {
            //        ResponseEntity<String> response = httpService.exchange(propertiesConfig.getApiAchConfrmBankSender(), HttpMethod.PUT, message, headers);
            return kafkaProducer.sendMessage(propertiesConfig.getTopicMxConvertAch(), propertiesConfig.getTopicConsumer(), data);
        }catch (Exception ex){
            log.error("ACH gửi bản tin Ack error: {}", ex.getMessage());
            return ValidateXMConverterResponse
                    .builder()
                    .type("false")
                    .message("Message false processed")
                    .duplicate(true).build();
        }
    }

    @Override
    public ValidateXMConverterResponse processAchSendCamt025BankReceiver(String request) {
        log.info("ACH gửi bản tin camt.025 sang MX Converter để gửi sang ngân hàng nhận lệnh -> request: {}", request);
        try {
            String json = Convert.convertXmlToJson(request);
            log.info("convert XML -> Json: {}", json);

            return kafkaProducer.sendMessage(propertiesConfig.getTopicAchSentCamt025(), propertiesConfig.getTopicConsumer(), json);
        } catch (Exception ex) {
            log.error("ACH gửi bản tin camt.025 error: {}", ex.getMessage());
            return ValidateXMConverterResponse
                    .builder()
                    .type("false")
                    .message("Message false processed")
                    .duplicate(true).build();
        }
    }

    @Override
    public ValidateXMConverterResponse processAchSendPacs002ToBank(String request) {
        log.info("B20. Put request message pacs.002: {}", request);
        try {
            String json = Convert.convertXmlToJson(request);
            log.info("convert XML -> Json: {}", json);

            return kafkaProducer.sendMessage(propertiesConfig.getTopicAchSendPacs002ToBank(), propertiesConfig.getTopicConsumer(), json);
        } catch (Exception ex) {
            log.error("ACH gửi bản tin pacs.002 error: {}", ex.getMessage());
            return ValidateXMConverterResponse
                    .builder()
                    .type("false")
                    .message("Message false processed")
                    .duplicate(true).build();
        }
    }



}
