package com.vn.napas.model.pacs08;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ObjectReceiverPacs08 {
    @JsonProperty("ID")
    private String id;
    @JsonProperty("Name")
    private String name;
}
