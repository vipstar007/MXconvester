package com.vn.napas.model.pacs08;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ObjectCdtrAgt {
	@JsonProperty("FinInstnId")
    private ObjectFinInstnId objectFinInstnId;
}
