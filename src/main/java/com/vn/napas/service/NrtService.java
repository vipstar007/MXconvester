package com.vn.napas.service;

import com.vn.napas.model.pacs08.JsonObjectPacs08;
import com.vn.napas.model.response.ValidateXMConverterResponse;
import org.springframework.stereotype.Service;

@Service
public interface NrtService {
    ValidateXMConverterResponse callApiBankSenderMXConverter(String kindOfMessage, String senderId, String service, String messageIdentifier, String refId, JsonObjectPacs08 objectPacs08) throws Exception;

    ValidateXMConverterResponse callApiMXConverterCallACH(String body);

    ValidateXMConverterResponse processAchSendPacs008(String request);

    ValidateXMConverterResponse callApiMxConvertSendPacs008(String body);

    ValidateXMConverterResponse processJSONPacs008ToXML(String request);
    ValidateXMConverterResponse processJSONPacs002ToXML(String request);

    ValidateXMConverterResponse processAchConfirmBank(String request) throws Exception;

    ValidateXMConverterResponse processAchSendCamt025BankReceiver(String request);

    ValidateXMConverterResponse processAchSendPacs002ToBank(String request);
}
