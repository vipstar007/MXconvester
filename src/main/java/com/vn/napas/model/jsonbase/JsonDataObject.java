package com.vn.napas.model.jsonbase;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class JsonDataObject {
    @JsonProperty("bicParticipant")
    private List<String> bicParticipant;
    @JsonProperty("binClear")
    private List<String> binClear;
    @JsonProperty("binFake")
    private List<String> binFake;
    @JsonProperty("shortNameParticipant")
    private List<String> shortNameParticipant;
    @JsonProperty("bicACH")
    private List<String> bicACH;
    @JsonProperty("bicIBFT")
    private List<String> bicIBFT;
    @JsonProperty("senderACH")
    private List<Boolean> senderACH;
    @JsonProperty("senderIBFT")
    private List<Boolean> senderIBFT;
    @JsonProperty("receiverACH")
    private List<Boolean> receiverACH;
    @JsonProperty("receiverIBFT")
    private List<Boolean> receiverIBFT;
}
