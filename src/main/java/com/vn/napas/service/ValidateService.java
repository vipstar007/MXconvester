package com.vn.napas.service;

import com.vn.napas.model.jsonbase.JsonDataObject;
import com.vn.napas.model.pacs08.JsonObjectPacs08;
import com.vn.napas.model.response.ValidateXMConverterResponse;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public interface ValidateService {
    ValidateXMConverterResponse validateXMConverter(String kindOfMessage, String senderId, String service, String messageIdentifier, String refId, JsonObjectPacs08 objectPacs08)throws Exception;
    JsonObjectPacs08 RountPaymentForNRTFromBank(JsonObjectPacs08 objectPacs08) throws Exception;
    JsonObjectPacs08 RountPaymentForNRTFromACH(JsonObjectPacs08 objectPacs08)throws Exception;
}
