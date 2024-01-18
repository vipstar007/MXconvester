package com.vn.napas.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.vn.napas.model.kafka.Pacs08Message;
import com.vn.napas.model.pacs08.*;
import com.vn.napas.model.response.ValidateXMConverterResponse;

import java.util.Arrays;

public class Convert {
    public static String convertXmlToJson(String xmlRequest) throws Exception {
        ObjectMapper mapper = new XmlMapper();
        Object jsonObject = mapper.readValue(xmlRequest, Object.class);
        mapper = new ObjectMapper();
        String response = mapper.writeValueAsString(jsonObject);
        return response.replace("\"\":","\"Value\":");
    }

    public static JsonNode convertStringToJsonNode(String data) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(data);
    }

    public static String convertJsonToXml(JsonObjectPacs08 objectPacs08) throws Exception {
        XmlMapper xmlMapper = new XmlMapper();
        return xmlMapper.writeValueAsString(objectPacs08);
    }

    public static String convertJsonToXml(String jsonString) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonString);
        return new XmlMapper().writeValueAsString(jsonNode);
    }

    public static String convertObjectToJson(Object object) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    public static ValidateXMConverterResponse convertJsonToObject(String json) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, ValidateXMConverterResponse.class);
    }

    public static JsonObjectPacs08 convertJsonToJsonObjectPacs08(String pacs08) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(pacs08, JsonObjectPacs08.class);
    }
    public static JsonObjectPacs08XML convertJsonToJsonObjectPacs08XML(String pacs08) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(pacs08, JsonObjectPacs08XML.class);
    }
    public static JsonObjectPacs08 convertJsonObjectPacs08XMLToJsonObjectPacs08(JsonObjectPacs08XML data)throws Exception{
        JsonObjectPacs08 newData = new JsonObjectPacs08();
        ObjectPayloadPacs08 newObjectPayloadPacs08 = new ObjectPayloadPacs08();
        ObjectDocumentPacs08 newObjectDocumentPacs08 = new ObjectDocumentPacs08();
        ObjectFIToFICstmrCdtTrfPacs08 newObjectFIToFICstmrCdtTrfPacs08  = new ObjectFIToFICstmrCdtTrfPacs08();
        ObjectCdtTrfTxInf[] list = new ObjectCdtTrfTxInf[1];
        ObjectCdtTrfTxInf objectCdtTrfTxInf = data.getPayload().getObjectDocumentPacs08().getObjectFIToFICstmrCdtTrfPacs08().getObjectCdtTrfTxInf();
        list  = addElement(list, objectCdtTrfTxInf);
        newObjectFIToFICstmrCdtTrfPacs08.setObjectCdtTrfTxInf(list);
        newObjectFIToFICstmrCdtTrfPacs08.setObjectGrpHdr(data.getPayload().getObjectDocumentPacs08().getObjectFIToFICstmrCdtTrfPacs08().getObjectGrpHdr());
        newObjectDocumentPacs08.setObjectFIToFICstmrCdtTrfPacs08(newObjectFIToFICstmrCdtTrfPacs08);
        newObjectPayloadPacs08.setObjectAppHdrPacs08(data.getPayload().getObjectAppHdrPacs08());
        newObjectPayloadPacs08.setObjectDocumentPacs08(newObjectDocumentPacs08);
        newData.setPayload(newObjectPayloadPacs08);
        newData.setHeader(data.getHeader());
        return newData;
    }
    // Phương thức để thêm một phần tử vào mảng và trả về mảng mới
    private static ObjectCdtTrfTxInf[] addElement(ObjectCdtTrfTxInf[] array, ObjectCdtTrfTxInf element) {
        int length = array.length;
        ObjectCdtTrfTxInf[] newArray = Arrays.copyOf(array, length +1 );
        newArray[length] = element;
        return newArray;
    }
}
