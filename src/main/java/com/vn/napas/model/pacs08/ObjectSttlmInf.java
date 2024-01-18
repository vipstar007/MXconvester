package com.vn.napas.model.pacs08;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ObjectSttlmInf {
	@JsonProperty("SttlmMtd")
    private String sttlmMtd;
}
