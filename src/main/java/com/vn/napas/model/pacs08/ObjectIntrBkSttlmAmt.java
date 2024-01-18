package com.vn.napas.model.pacs08;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

import lombok.Data;

@Data
public class ObjectIntrBkSttlmAmt {
	@JsonProperty("Ccy")
	@JacksonXmlProperty(localName = "Ccy", isAttribute = true)
    private String ccy;
	
	@JsonProperty("Value")
	@JacksonXmlText
    private String value;
}
