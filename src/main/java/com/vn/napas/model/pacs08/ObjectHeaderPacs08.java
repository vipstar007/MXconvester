package com.vn.napas.model.pacs08;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ObjectHeaderPacs08 {
    @JsonProperty("SenderReference")
    private String senderReference="";
    @JsonProperty("MessageIdentifier")
    private String messageIdentifier="";
    @JsonProperty("Format")
    private String format="";
    @JsonProperty("Sender")
    private ObjectSenderPacs08 sender;
    @JsonProperty("Receiver")
    private ObjectReceiverPacs08 receiver;
    @JsonProperty("Timestamp")
    private String timestamp="";
    @JsonProperty("Signature")
    private String signature="";
}
