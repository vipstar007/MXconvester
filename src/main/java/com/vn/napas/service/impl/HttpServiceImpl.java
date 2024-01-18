package com.vn.napas.service.impl;

import com.vn.napas.config.PropertiesConfig;
import com.vn.napas.model.response.ValidateXMConverterResponse;
import com.vn.napas.service.HttpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class HttpServiceImpl implements HttpService {
    private final PropertiesConfig propertiesConfig;
    @Override
    public ResponseEntity<String> exchange(String url, HttpMethod method, String request, HttpHeaders headers) {
        log.info("call api: {} - method: {}", url, method.name());
        try {
            HttpEntity<String> requestEntity = new HttpEntity<>(request, headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, method, requestEntity, String.class);
            HttpStatusCode statusCode = responseEntity.getStatusCode();

            if (statusCode.value() == 200) {
                log.info("call service success full");
                return responseEntity;
            }
            return null;
        } catch (Exception ex) {
            log.error("call api: {} - exception: {}", url, ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }
    @Override
    public ValidateXMConverterResponse callApiMXConverterCallACH(String body,String messageType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        ResponseEntity<String> response = null;
        switch (messageType){
            case "008":
                 response = exchange(propertiesConfig.getApiACH008(), HttpMethod.PUT, body, headers);
                break;
            case "002":
                 response = exchange(propertiesConfig.getApiAch002(), HttpMethod.PUT, body, headers);
                break;
        }

        return getResponseEntity(response);
    }

    @Override
    public ValidateXMConverterResponse callApiMxConvertSendPacs008(String body) throws InterruptedException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
//        log.info("sleep///////");
//        Thread.sleep(6000);
        log.info("MXConverter nhận lệnh từ ACH gửi cho Bank Receiver");
        ResponseEntity<String> response = exchange(propertiesConfig.getApiAchSendPacs008ToBankReceiver(), HttpMethod.PUT, body, headers);
        return getResponseEntity(response);
    }

    @Override
    public ValidateXMConverterResponse callApiBankSenderProcessPacs008(String message) throws InterruptedException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
//        log.info("sleep///////");
//        Thread.sleep(6000);
        ResponseEntity<String> response = exchange(propertiesConfig.getApiAchSendPacs008ToBankReceiver(), HttpMethod.PUT, message, headers);
        return getResponseEntity(response);
    }

    @Override
    public ValidateXMConverterResponse callApiAchConfrmBank(String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<String> response = null;

            log.info("gui ban tin Nack sang bank:"+propertiesConfig.getApiAchConfirmBank());
            response =   exchange(propertiesConfig.getApiAchConfirmBank(), HttpMethod.PUT, message, headers);

        return getResponseEntity(response);
    }

    @Override
    public ValidateXMConverterResponse callApiAchSendCamt025(String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<String> response = exchange(propertiesConfig.getApiAchSentCamt025(), HttpMethod.PUT, message, headers);
        return getResponseEntity(response);
    }

    @Override
    public ValidateXMConverterResponse callApiAchSend002(String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<String> response = null;

            log.info("ACH put message pacs002 to bank "+message);

           String url = propertiesConfig.getApiAchSendPacs002ToBank() ;
            log.info("XM calling : " + url);
            response = exchange(url, HttpMethod.PUT, message, headers);

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
}
