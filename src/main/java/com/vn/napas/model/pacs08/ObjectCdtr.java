package com.vn.napas.model.pacs08;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ObjectCdtr {
	@JsonProperty("Nm")
    private String nm;
	
	@JsonProperty("PstlAdr")
    private ObjectPstlAdr objectPstlAdr;
}
