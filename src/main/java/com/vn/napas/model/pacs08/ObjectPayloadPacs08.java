package com.vn.napas.model.pacs08;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Data;

@Data
public class ObjectPayloadPacs08 {
	@JsonProperty("AppHdr")
	@JacksonXmlProperty(localName = "AppHdr")
    private ObjectAppHdrPacs08 objectAppHdrPacs08;
	
	@JsonProperty("Document")
	@JacksonXmlProperty(localName = "Document")
    private ObjectDocumentPacs08 objectDocumentPacs08;
}
