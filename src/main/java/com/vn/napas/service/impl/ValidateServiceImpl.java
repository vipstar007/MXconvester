package com.vn.napas.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.vn.napas.config.PropertiesConfig;
import com.vn.napas.model.jsonbase.JsonDataObject;
import com.vn.napas.model.jsonbase.JsonValidateObject;
import com.vn.napas.model.pacs08.JsonObjectPacs08;
import com.vn.napas.model.pacs08.ObjectCdtTrfTxInf;
import com.vn.napas.model.response.ValidateXMConverterResponse;
import com.vn.napas.service.ValidateService;
import com.vn.napas.utils.Constants;
import com.vn.napas.utils.Convert;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.xml.stream.events.Comment;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ValidateServiceImpl implements ValidateService {
    private final PropertiesConfig propertiesConfig;

    /**
     * validate data json
     */
    @Override
    public ValidateXMConverterResponse validateXMConverter(String kindOfMessage, String senderId, String service, String messageIdentifier, String refId, JsonObjectPacs08 objectPacs08)throws Exception {
        log.info("validate XMConverter");
        JsonNode json = readFile(propertiesConfig.getPathFileConfigValidate());
        //Check Url
        List<JsonValidateObject> val = new Gson().fromJson(json.get("Nrt").get("checkURL").toString(), new TypeToken<ArrayList<JsonValidateObject>>() {
        }.getType());
        for (JsonValidateObject obj : val) {
            JsonValidateObject.ValidateContent v = obj.getValidateContent();
            if (v.getValidateKey().equals("KindOfMessage") && !v.getValues().contains(kindOfMessage.toUpperCase())) {
                log.info("KindOfMessage:" +v.getMessage());
                return ValidateXMConverterResponse.builder().type(Constants.TYPE_FAIL).message(v.getMessage()).code(v.getCode()).messageIdentifier(messageIdentifier).build();
            } else if (v.getValidateKey().equals("Service") && !v.getValues().contains(service)) {
                log.info("Service:"+v.getMessage());
                return ValidateXMConverterResponse.builder().type(Constants.TYPE_FAIL).message(v.getMessage()).code(v.getCode()).messageIdentifier(messageIdentifier).build();
            } else if (v.getValidateKey().equals("MessageIdentifier") && !v.getValues().contains(messageIdentifier)) {
                log.info("MessageIdentifier:"+v.getMessage());
                return ValidateXMConverterResponse.builder().type(Constants.TYPE_FAIL).message(v.getMessage()).code(v.getCode()).messageIdentifier(messageIdentifier).build();
            } else if (v.getValidateKey().equals("FormatUrl")) {
                String strCompare = kindOfMessage.toUpperCase() + "/" + service + "/" + messageIdentifier;
                log.info("FormatUrl:"+strCompare);
                if (!v.getValues().contains(strCompare)) {
                    log.info("FormatUrl:" + v.getMessage());
                    return ValidateXMConverterResponse.builder().type(Constants.TYPE_FAIL).message(v.getMessage()).code(v.getCode()).messageIdentifier(messageIdentifier).build();
                }
            }
        }
        //Check body
        JsonNode body = Convert.convertStringToJsonNode(Convert.convertObjectToJson(objectPacs08));
        //Check data
        JsonNode data = json.get("Nrt").get("checkData").get("mxConverter");
        String messHeader = validateData("Header", data, body);
        if (!messHeader.isEmpty()) {
            log.info("Header:"+messHeader);
            return ValidateXMConverterResponse.builder().type(Constants.TYPE_FAIL).message(messHeader).code(502).messageIdentifier(messageIdentifier).build();

        }
        //Check Routing
        //RountPaymentForNRT
        if(messageIdentifier.contentEquals(Constants.Pass008)) {
            JsonDataObject dataValues = new Gson().fromJson(json.get("Nrt").get("dataList").toString(), new TypeToken<JsonDataObject>() {}.getType());
            log.info("validateRountPaymentForNRT");
            messHeader = validateRountPaymentForNRT(dataValues,objectPacs08);
             if (!messHeader.isEmpty()) {
                log.info(messHeader);
                return ValidateXMConverterResponse.builder().type(Constants.TYPE_FAIL).message(messHeader).code(502).messageIdentifier(messageIdentifier).build();
            }
        }
        log.info("Check validate pass");
        return ValidateXMConverterResponse.builder().type(Constants.TYPE_SUCCESS).message(Constants.MESS_SUCCESSFULLY).code(200).messageIdentifier(messageIdentifier).build();
    }

    /**
     * validate Structure file json
     */

    //Validate body
    //Validate data
    private String validateData(String type, JsonNode json, JsonNode data) throws Exception {
        List<String> keys = new Gson().fromJson(json.get(type).get("validateKey").toString(), new TypeToken<ArrayList<String>>() {
        }.getType());
        for (String key : keys) {
            String value = "";
            if (!key.contains("/"))
                value = data.get(type).get(key).textValue();
            else {
                List<String> subkeys = Arrays.stream(key.split("/")).toList();
                JsonNode node = data.get(type);
                for (String sub : subkeys)
                    node = node.get(sub);
                value = node.textValue();
            }
            log.info("Key:"+key+"/Value:"+value);
            if (value.isEmpty())
                return json.get("message").asText();
        }
        return "";
    }

    //Validate Routing
    private String validateRountPaymentForNRT(JsonDataObject json, JsonObjectPacs08 data) {
        String memberto = data.getPayload().getObjectAppHdrPacs08().getObjectFr().getObjectFIId().getObjectFinInstnId().getObjectClrSysMmbId().getMmbId();
        String memberfrom = data.getPayload().getObjectAppHdrPacs08().getObjectTo().getObjectFIId().getObjectFinInstnId().getObjectClrSysMmbId().getMmbId();
        if(json.getBicParticipant().contains(memberto) || json.getBicParticipant().contains(memberfrom)){
            log.info("MemberId:" + Constants.MESS_WRONGNRTFORMAT);
            return Constants.MESS_WRONGNRTFORMAT;
        }
        ObjectCdtTrfTxInf[] CdtTrfTxInf = data.getPayload().getObjectDocumentPacs08().getObjectFIToFICstmrCdtTrfPacs08().getObjectCdtTrfTxInf();
        if(CdtTrfTxInf.length > 1){
            log.info("Length:" + Constants.MESS_WRONGNRTFORMAT);
            return Constants.MESS_WRONGNRTFORMAT;
        }
        for (ObjectCdtTrfTxInf v : CdtTrfTxInf) {
            String Prtry = v.getObjectPmtTpInf().getObjectSvcLvl().getPrtry();
            String EndToEndID = v.getObjectPmtId().getEndToEndId().substring(12,20);
            //EndToEndId: = INPLIBFT phát lệnh từ NHTV IBFT;  <> INPLIBFT phát lệnh từ NHTV ACH
            if (!Prtry.equals("0100")) {
                log.info("0100:" + Constants.MESS_WRONGNRTFORMAT);
                return Constants.MESS_WRONGNRTFORMAT;
            }
            if(EndToEndID.equals("INPLIBFT")){
                log.info("INPLIBFT:" + Constants.MESS_WRONGFORMAT);
                return Constants.MESS_WRONGFORMAT;
            }
        }
        return "";
    }

    @Override
    public JsonObjectPacs08 RountPaymentForNRTFromBank(JsonObjectPacs08 data) throws Exception {
        JsonNode json = readFile(propertiesConfig.getPathFileConfigValidate());
        String strDataList = json.get("Nrt").get("dataList").toString();
        JsonDataObject valuesData = new Gson().fromJson(strDataList , new TypeToken<JsonDataObject>() {
        }.getType());
        ObjectCdtTrfTxInf[] CdtTrfTxInf = data.getPayload().getObjectDocumentPacs08().getObjectFIToFICstmrCdtTrfPacs08().getObjectCdtTrfTxInf();
        for (ObjectCdtTrfTxInf v : CdtTrfTxInf) {
            String memberReceive = v.getObjectInstdAgt().getObjectFinInstnId().getObjectClrSysMmbId().getMmbId();
            String memberSent = v.getObjectInstgAgt().getObjectFinInstnId().getObjectClrSysMmbId().getMmbId();
            String CdtrAcct = v.getObjectCdtrAcct().getObjectTp().getPrtry();
            //CdtrAcct: ACC chuyển đến account;  PAN chuyển đến card
            if (valuesData.getBicParticipant().contains(memberReceive)) {
                int index = valuesData.getBicParticipant().indexOf(memberReceive);
                Boolean senderACH = valuesData.getSenderACH().get(index);
                Boolean senderIBFT = valuesData.getSenderIBFT().get(index);
                if (CdtrAcct.equals("ACC")) {
                    if (!senderACH && senderIBFT) {
                        v.getObjectCdtrAgt().getObjectFinInstnId().getObjectClrSysMmbId().setMmbId(v.getObjectInstdAgt().getObjectFinInstnId().getObjectClrSysMmbId().getMmbId());
                        v.getObjectInstdAgt().getObjectFinInstnId().getObjectClrSysMmbId().setMmbId(valuesData.getBicIBFT().get(index));

                    } else {
                        v.getObjectCdtrAgt().getObjectFinInstnId().getObjectClrSysMmbId().setMmbId(v.getObjectInstdAgt().getObjectFinInstnId().getObjectClrSysMmbId().getMmbId());
                        v.getObjectInstdAgt().getObjectFinInstnId().getObjectClrSysMmbId().setMmbId(valuesData.getBicACH().get(index));
                    }
                } else {
                    if (senderACH && !senderIBFT) {
                        v.getObjectCdtrAgt().getObjectFinInstnId().getObjectClrSysMmbId().setMmbId(valuesData.getBicACH().get(index));
                        v.getObjectInstdAgt().getObjectFinInstnId().getObjectClrSysMmbId().setMmbId(valuesData.getBicACH().get(index));
                    } else {
                        v.getObjectCdtrAgt().getObjectFinInstnId().getObjectClrSysMmbId().setMmbId(valuesData.getBicIBFT().get(index));
                        v.getObjectInstdAgt().getObjectFinInstnId().getObjectClrSysMmbId().setMmbId(valuesData.getBicIBFT().get(index));
                    }
                }
            }
            if (valuesData.getBicParticipant().contains(memberSent)) {
                int index = valuesData.getBicParticipant().indexOf(memberSent);
                v.getObjectInstgAgt().getObjectFinInstnId().getObjectClrSysMmbId().setMmbId(valuesData.getBicACH().get(index));
                v.getObjectDbtrAgt().getObjectFinInstnId().getObjectClrSysMmbId().setMmbId(valuesData.getBicACH().get(index));
            }
        }

        return data;
    }


    //From ACH là dạng XML
    @Override
    public JsonObjectPacs08 RountPaymentForNRTFromACH(JsonObjectPacs08 data) throws Exception {
         JsonNode json = readFile(propertiesConfig.getPathFileConfigValidate());
        JsonDataObject values = new Gson().fromJson(json.get("Nrt").get("dataList").toString(), new TypeToken<JsonDataObject>() {
        }.getType());
        ObjectCdtTrfTxInf[] CdtTrfTxInf = data.getPayload().getObjectDocumentPacs08().getObjectFIToFICstmrCdtTrfPacs08().getObjectCdtTrfTxInf();
        for (ObjectCdtTrfTxInf v : CdtTrfTxInf) {
            if(v == null)
                continue;
             String memberReceive = v.getObjectInstdAgt().getObjectFinInstnId().getObjectClrSysMmbId().getMmbId();
            String memberSent = v.getObjectInstgAgt().getObjectFinInstnId().getObjectClrSysMmbId().getMmbId();
            String dbtrAgt = v.getObjectDbtrAgt().getObjectFinInstnId().getObjectClrSysMmbId().getMmbId();
            String cdtrAgt = v.getObjectCdtrAgt().getObjectFinInstnId().getObjectClrSysMmbId().getMmbId();
            if (values.getBicParticipant().contains(memberSent)) {
                int index = values.getBicParticipant().indexOf(memberSent);
                if (!memberSent.equals(values.getBicACH().get(index)))
                    v.getObjectInstgAgt().getObjectFinInstnId().getObjectClrSysMmbId().setMmbId(values.getBicACH().get(index));
            }

            if (values.getBicParticipant().contains(memberReceive)) {
                int index = values.getBicParticipant().indexOf(memberReceive);
                if (!memberReceive.equals(values.getBicACH().get(index)))
                    v.getObjectInstdAgt().getObjectFinInstnId().getObjectClrSysMmbId().setMmbId(values.getBicACH().get(index));
            }

            if (values.getBicParticipant().contains(dbtrAgt)) {
                int index = values.getBicParticipant().indexOf(dbtrAgt);
                if (!dbtrAgt.equals(values.getBicACH().get(index)))
                    v.getObjectDbtrAgt().getObjectFinInstnId().getObjectClrSysMmbId().setMmbId(values.getBicACH().get(index));
            }

            if (values.getBicParticipant().contains(cdtrAgt)) {
                int index = values.getBicParticipant().indexOf(cdtrAgt);
                if (!cdtrAgt.equals(values.getBicACH().get(index)))
                    v.getObjectCdtrAgt().getObjectFinInstnId().getObjectClrSysMmbId().setMmbId(values.getBicACH().get(index));
            }
        }
        return data;
    }

    private JsonNode readFile(String path) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(path));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null)
            sb.append(line + "\n");
        return Convert.convertStringToJsonNode(sb.toString());
    }
}
