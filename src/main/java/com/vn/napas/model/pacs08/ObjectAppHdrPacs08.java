package com.vn.napas.model.pacs08;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ObjectAppHdrPacs08 {
	@JsonProperty("Fr")
    private ObjectFr objectFr;
	
	@JsonProperty("To")
    private ObjectTo objectTo;
	
	@JsonProperty("BizMsgIdr")
    private String bizMsgIdr;
	
	@JsonProperty("MsgDefIdr")
    private String msgDefIdr;
	
	@JsonProperty("BizSvc")
    private String bizSvc;
	
	@JsonProperty("CreDt")
    private String creDt;
}
