package com.vn.napas.model.pacs08;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ObjectCdtrAcct {
	@JsonProperty("Id")
    private ObjectId objectId;
	
	@JsonProperty("Tp")
    private ObjectTp objectTp;
}
