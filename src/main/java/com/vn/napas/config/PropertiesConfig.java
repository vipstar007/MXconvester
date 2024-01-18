package com.vn.napas.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class PropertiesConfig {
    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${kafka.consumergroup}")
    private String consumerGroup;

    @Value("${kafka.topic.mx-convert}")
    private String topicBankSenderMxConvert;

    @Value("${kafka.topic.mx-convert-ach}")
    private String topicMxConvertAch;

    @Value("${auth.user.name}")
    private String usernameAuth;

    @Value("${auth.user.password}")
    private String passwordAuth;

    @Value("${napas.api.ach.confirmpasc008}")
    private String apiACH008;
    @Value("${napas.api.ach.confirmpasc002}")
    private String apiAch002;

    @Value("${napas.api.ach-confirm-bank}")
    private String apiAchConfirmBank;


    @Value("${napas.config.validate}")
    private String pathFileConfigValidate;

    @Value("${napas.api.signature}")
    private String apiSignature;

    @Value("${napas.api.ach-send-pacs008-bank-receiver}")
    private String apiAchSendPacs008ToBankReceiver;

    @Value("${kafka.topic.consumer}")
    private String topicConsumer;

    @Value("${kafka.topic.consumer-stream-2}")
    private String topicConsumerStream2;

    @Value("${kafka.topic.ach-send-pacs008-mx-convert-bank-sender}")
    private String topicAchSendPacs008ToBankSender;

    @Value("${kafka.topic.bank-receiver-process-pacs008}")
    private String topicBankSenderProcessPacs008;

    @Value("${kafka.topic.ach-confirm-send-camt-025}")
    private String topicAchSentCamt025;

    @Value("${napas.api.ach-send-camt-025-bank-receiver}")
    private String apiAchSentCamt025;

    @Value("${kafka.topic.ach-confirm-send-pacs-002ToBank}")
    private String topicAchSendPacs002ToBank;

    @Value("${napas.api.ach-send-pacs002}")
    private String apiAchSendPacs002ToBank;

}
