package com.vn.napas.service;

import com.vn.napas.model.response.ValidateXMConverterResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface HttpService {
    ResponseEntity<String> exchange(String url, HttpMethod method, String body, HttpHeaders headers);
    ValidateXMConverterResponse callApiMXConverterCallACH(String body,String messageType);
    ValidateXMConverterResponse callApiMxConvertSendPacs008(String body) throws InterruptedException;

    ValidateXMConverterResponse callApiBankSenderProcessPacs008(String message) throws InterruptedException;

    ValidateXMConverterResponse callApiAchConfrmBank(String message);
    ValidateXMConverterResponse callApiAchSendCamt025(String message);
    ValidateXMConverterResponse callApiAchSend002(String message);
}
