package com.vn.napas.model.pacs08;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ObjectDbtrAcct {
	@JsonProperty("Id")
    private ObjectId objectId;
	
	@JsonProperty("Tp")
    private ObjectTp objectTp;
}
