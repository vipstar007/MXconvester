package com.vn.napas.model.pacs08;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ObjectCtgyPurp {
	@JsonProperty("Prtry")
    private String prtry;
}
