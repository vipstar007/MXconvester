package com.vn.napas.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.vn.napas.config.PropertiesConfig;
import com.vn.napas.service.HttpService;
import com.vn.napas.service.SignatureService;
import com.vn.napas.utils.Convert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SignatureServiceImpl implements SignatureService {
    private final HttpService httpService;
    private final PropertiesConfig propertiesConfig;

    @Override
    public JsonNode getHeaderAchSendPacs008() throws Exception {
        ResponseEntity<String> response = httpService.exchange(propertiesConfig.getApiSignature(), HttpMethod.GET, null, HttpHeaders.EMPTY);
        log.info("response: {}", response.getBody());
        return Convert.convertStringToJsonNode(response.getBody());
    }
}
