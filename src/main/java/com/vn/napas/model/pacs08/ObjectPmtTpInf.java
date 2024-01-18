package com.vn.napas.model.pacs08;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ObjectPmtTpInf {
	@JsonProperty("ClrChanl")
    private String clrChanl;
	
	@JsonProperty("SvcLvl")
    private ObjectSvcLvl objectSvcLvl;
	
	@JsonProperty("LclInstrm")
    private ObjectLclInstrm objectLclInstrm;
	
	@JsonProperty("CtgyPurp")
    private ObjectCtgyPurp objectCtgyPurp;
}
