package com.vn.napas.model.kafka;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vn.napas.model.pacs08.JsonObjectPacs08;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Pacs08Message implements Serializable {
    private JsonObjectPacs08 pacs08;
    private String kindOfMessage;
    private String senderId;
    private String service;
    private String messageIdentifier;
    private String refId;
}
