package com.vn.napas.model.pacs08;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import lombok.Data;

@Data
public class ObjectPstlAdr {
	@JsonProperty("AdrLine")
	@JacksonXmlElementWrapper(useWrapping = false)
    private String [] adrLine;
}
