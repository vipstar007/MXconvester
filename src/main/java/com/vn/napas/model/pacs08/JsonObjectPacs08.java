package com.vn.napas.model.pacs08;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "DataPDU")
public class JsonObjectPacs08 {
	
	
	@JsonProperty("Header") 
    private ObjectHeaderPacs08 header;
	
	@JsonProperty("Payload")
	@JacksonXmlProperty(localName = "Body")
    private ObjectPayloadPacs08 payload;
}
