package com.vn.napas.model.pacs08;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ObjectGrpHdr {
	
	@JsonProperty("MsgId")
    private String msgId;
	
	@JsonProperty("CreDtTm")
    private String creDtTm;
	
	@JsonProperty("NbOfTxs")
    private Integer nbOfTxs;
	
	@JsonProperty("TtlIntrBkSttlmAmt")
    private ObjectTtlIntrBkSttlmAmt objectTtlIntrBkSttlmAmt;
	
	@JsonProperty("IntrBkSttlmDt")
    private String intrBkSttlmDt;
	
	@JsonProperty("SttlmInf")
    private ObjectSttlmInf objectSttlmInf;
}
