package com.vn.napas.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;

@Service
public interface SignatureService {
    JsonNode getHeaderAchSendPacs008() throws Exception;
}
