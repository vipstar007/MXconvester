package com.vn.napas.model.pacs08;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ObjectPmtId {
	@JsonProperty("InstrId")
    private String instrId;
	
	@JsonProperty("EndToEndId")
    private String endToEndId;
	
	@JsonProperty("TxId")
    private String txId;
}
