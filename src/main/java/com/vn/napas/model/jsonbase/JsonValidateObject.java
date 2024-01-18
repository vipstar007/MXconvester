package com.vn.napas.model.jsonbase;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vn.napas.model.pacs08.ObjectHeaderPacs08;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class JsonValidateObject {
    @JsonProperty("validateContent")
    private ValidateContent validateContent ;

    @Data
    public static class ValidateContent {
        @JsonProperty("validateKey")
        private String validateKey;
        @JsonProperty("values")
        private List<String> values;
        @JsonProperty("code")
        private int code;
        @JsonProperty("message")
        private String message;
    }
}
