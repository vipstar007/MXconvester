package com.vn.napas.model.validate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataFile {
    private FormatUrl formatUrl;
    private KindOfMessage kindOfMessage;
    private MessageIdentifier messageIdentifier;
    private Service service;
    private List<String> bicParticipant;
    private List<String> binClear;
    private List<String> binFake;
    private List<String> shortNameParticipant;
    private List<String> bicACH;
    private List<String> bicIBFT;
    private List<Boolean> senderACH;
    private List<Boolean> senderIBFT;
    private List<Boolean> receiverACH;
    private List<Boolean> receiverIBFT;
    private List<String> configs;



}
