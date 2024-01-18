package com.vn.napas.config.kafka.consumer;

import com.fasterxml.jackson.databind.JsonNode;
import com.vn.napas.model.pacs08.JsonObjectPacs08;
import com.vn.napas.model.response.ValidateXMConverterResponse;
import com.vn.napas.service.HttpService;
import com.vn.napas.service.NrtService;
import com.vn.napas.utils.Convert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class ReplyingKafkaConsumer {
    private final HttpService httpService;
    private final NrtService nrtService;

    /**
     * Ngân hàng thành viện gửi bản tin pacs008 và thực hiện convert Json sang XML gửi sang ACH
     */
    @KafkaListener(topics = "${kafka.topic.mx-convert}", groupId = "${kafka.consumergroup}", containerFactory = "kafkaListenerContainerFactory")
    @SendTo("outputTopic")
    public String listen(@Payload String message) throws Exception {
        log.info("**********CONSUMER 1**********");
        log.info("B4 MX convester sent pacs008 message to ACH");
        log.info("Topic Bank Sender - MXConvert Received message: " + message);
        ValidateXMConverterResponse response = httpService.callApiMXConverterCallACH(message,"008");
        return Convert.convertObjectToJson(response);
    }
    @KafkaListener(topics = "${kafka.topic.mx-convert}", groupId = "${kafka.consumergroup}", containerFactory = "kafkaListenerContainerFactory2")
    @SendTo("outputTopic")
    public String listen2(@Payload String message) throws Exception {
        log.info("**********CONSUMER 2**********");
        log.info("B4 MX convester sent pacs008 message to ACH");
        log.info("Topic Bank Sender - MXConvert Received message: " + message);
        ValidateXMConverterResponse response = httpService.callApiMXConverterCallACH(message,"008");
        return Convert.convertObjectToJson(response);
    }
//    @KafkaListener(topics = "${kafka.topic.mx-convert}", groupId = "${kafka.consumergroup}", containerFactory = "kafkaListenerContainerFactory3")
//    @SendTo("outputTopic")
//    public String listen3(@Payload String message) throws Exception {
//        log.info("B4 MX convester sent pacs008 message to ACH");
//        log.info("Topic Bank Sender - MXConvert Received message: " + message);
//        ValidateXMConverterResponse response = httpService.callApiMXConverterCallACH(message,"008");
//        return Convert.convertObjectToJson(response);
//    }
//    @KafkaListener(topics = "${kafka.topic.mx-convert}", groupId = "${kafka.consumergroup}", containerFactory = "kafkaListenerContainerFactory4")
//    @SendTo("outputTopic")
//    public String listen4(@Payload String message) throws Exception {
//        log.info("B4 MX convester sent pacs008 message to ACH");
//        log.info("Topic Bank Sender - MXConvert Received message: " + message);
//        ValidateXMConverterResponse response = httpService.callApiMXConverterCallACH(message,"008");
//        return Convert.convertObjectToJson(response);
//    }
//    @KafkaListener(topics = "${kafka.topic.mx-convert}", groupId = "${kafka.consumergroup}", containerFactory = "kafkaListenerContainerFactory5")
//    @SendTo("outputTopic")
//    public String listen5(@Payload String message) throws Exception {
//        log.info("B4 MX convester sent pacs008 message to ACH");
//        log.info("Topic Bank Sender - MXConvert Received message: " + message);
//        ValidateXMConverterResponse response = httpService.callApiMXConverterCallACH(message,"008");
//        return Convert.convertObjectToJson(response);
//    }
//    @KafkaListener(topics = "${kafka.topic.mx-convert}", groupId = "${kafka.consumergroup}", containerFactory = "kafkaListenerContainerFactory6")
//    @SendTo("outputTopic")
//    public String listen6(@Payload String message) throws Exception {
//        log.info("B4 MX convester sent pacs008 message to ACH");
//        log.info("Topic Bank Sender - MXConvert Received message: " + message);
//        ValidateXMConverterResponse response = httpService.callApiMXConverterCallACH(message,"008");
//        return Convert.convertObjectToJson(response);
//    }
//    @KafkaListener(topics = "${kafka.topic.mx-convert}", groupId = "${kafka.consumergroup}", containerFactory = "kafkaListenerContainerFactory7")
//    @SendTo("outputTopic")
//    public String listen7(@Payload String message) throws Exception {
//        log.info("B4 MX convester sent pacs008 message to ACH");
//        log.info("Topic Bank Sender - MXConvert Received message: " + message);
//        ValidateXMConverterResponse response = httpService.callApiMXConverterCallACH(message,"008");
//        return Convert.convertObjectToJson(response);
//    }

    /**
     * ACH Xác nhận đã nhận bản tin với ngân hàng gửi lệnh
     */
    @KafkaListener(topics = "${kafka.topic.mx-convert-ach}", groupId = "${kafka.consumergroup}", containerFactory = "kafkaListenerContainerFactory")
    @SendTo("outputTopic")
    public String achConfrmBankSender(@Payload String message) throws Exception {
        log.info("**********CONSUMER 1**********");
        log.info("ACH gửi message nack để xác nhận đối với Bank sender " + message);
//        ValidateXMConverterResponse response = httpService.callApiMXConverterCallACH(message);
        ValidateXMConverterResponse response = httpService.callApiAchConfrmBank(message);
        return Convert.convertObjectToJson(response);
    }
    @KafkaListener(topics = "${kafka.topic.mx-convert-ach}", groupId = "${kafka.consumergroup}", containerFactory = "kafkaListenerContainerFactory2")
    @SendTo("outputTopic")
    public String achConfrmBankSender2(@Payload String message) throws Exception {
        log.info("**********CONSUMER 2**********");
        log.info("ACH gửi message nack để xác nhận đối với Bank sender " + message);
//        ValidateXMConverterResponse response = httpService.callApiMXConverterCallACH(message);
        ValidateXMConverterResponse response = httpService.callApiAchConfrmBank(message);
        return Convert.convertObjectToJson(response);
    }
//    @KafkaListener(topics = "${kafka.topic.mx-convert-ach}", groupId = "${kafka.consumergroup}", containerFactory = "kafkaListenerContainerFactory3")
//    @SendTo("outputTopic")
//    public String achConfrmBankSender3(@Payload String message) throws Exception {
//        log.info("ACH gửi message nack để xác nhận đối với Bank sender " + message);
////        ValidateXMConverterResponse response = httpService.callApiMXConverterCallACH(message);
//        ValidateXMConverterResponse response = httpService.callApiAchConfrmBank(message);
//        return Convert.convertObjectToJson(response);
//    }
//    @KafkaListener(topics = "${kafka.topic.mx-convert-ach}", groupId = "${kafka.consumergroup}", containerFactory = "kafkaListenerContainerFactory4")
//    @SendTo("outputTopic")
//    public String achConfrmBankSender4(@Payload String message) throws Exception {
//        log.info("ACH gửi message nack để xác nhận đối với Bank sender " + message);
////        ValidateXMConverterResponse response = httpService.callApiMXConverterCallACH(message);
//        ValidateXMConverterResponse response = httpService.callApiAchConfrmBank(message);
//        return Convert.convertObjectToJson(response);
//    }
//    @KafkaListener(topics = "${kafka.topic.mx-convert-ach}", groupId = "${kafka.consumergroup}", containerFactory = "kafkaListenerContainerFactory5")
//    @SendTo("outputTopic")
//    public String achConfrmBankSende5(@Payload String message) throws Exception {
//        log.info("ACH gửi message nack để xác nhận đối với Bank sender " + message);
////        ValidateXMConverterResponse response = httpService.callApiMXConverterCallACH(message);
//        ValidateXMConverterResponse response = httpService.callApiAchConfrmBank(message);
//        return Convert.convertObjectToJson(response);
//    }
//    @KafkaListener(topics = "${kafka.topic.mx-convert-ach}", groupId = "${kafka.consumergroup}", containerFactory = "kafkaListenerContainerFactory6")
//    @SendTo("outputTopic")
//    public String achConfrmBankSender6(@Payload String message) throws Exception {
//        log.info("ACH gửi message nack để xác nhận đối với Bank sender " + message);
////        ValidateXMConverterResponse response = httpService.callApiMXConverterCallACH(message);
//        ValidateXMConverterResponse response = httpService.callApiAchConfrmBank(message);
//        return Convert.convertObjectToJson(response);
//    }
//    @KafkaListener(topics = "${kafka.topic.mx-convert-ach}", groupId = "${kafka.consumergroup}", containerFactory = "kafkaListenerContainerFactory7")
//    @SendTo("outputTopic")
//    public String achConfrmBankSender7(@Payload String message) throws Exception {
//        log.info("ACH gửi message nack để xác nhận đối với Bank sender " + message);
////        ValidateXMConverterResponse response = httpService.callApiMXConverterCallACH(message);
//        ValidateXMConverterResponse response = httpService.callApiAchConfrmBank(message);
//        return Convert.convertObjectToJson(response);
//    }


    /**
     * ACH gửi bản tin pacs008 đến MX Converter để gửi sang ngân hàng nhận lệnh
     */
    @KafkaListener(topics = "${kafka.topic.ach-send-pacs008-mx-convert-bank-sender}", groupId = "${kafka.consumergroup}", containerFactory = "kafkaListenerContainerFactory")
    @SendTo("outputTopic")
    public String topicAchSendPacs008ToBankSender(@Payload String message) throws Exception {
        log.info("**********CONSUMER 1**********");
        log.info("ACH gửi pacs008 xml cho bank receiver " + message);
         ValidateXMConverterResponse response = httpService.callApiMxConvertSendPacs008(message);
        return Convert.convertObjectToJson(response);
    }
    @KafkaListener(topics = "${kafka.topic.ach-send-pacs008-mx-convert-bank-sender}", groupId = "${kafka.consumergroup}", containerFactory = "kafkaListenerContainerFactory2")
    @SendTo("outputTopic")
    public String topicAchSendPacs008ToBankSender2(@Payload String message) throws Exception {
        log.info("**********CONSUMER 2**********");
        log.info("ACH gửi pacs008 xml cho bank receiver " + message);
        ValidateXMConverterResponse response = httpService.callApiMxConvertSendPacs008(message);
        return Convert.convertObjectToJson(response);
    }
//    @KafkaListener(topics = "${kafka.topic.ach-send-pacs008-mx-convert-bank-sender}", groupId = "${kafka.consumergroup}", containerFactory = "kafkaListenerContainerFactory3")
//    @SendTo("outputTopic")
//    public String topicAchSendPacs008ToBankSender3(@Payload String message) throws Exception {
//        log.info("**********CONSUMER 3**********");
//        log.info("ACH gửi pacs008 xml cho bank receiver " + message);
//        ValidateXMConverterResponse response = httpService.callApiMxConvertSendPacs008(message);
//        return Convert.convertObjectToJson(response);
//    }
//    @KafkaListener(topics = "${kafka.topic.ach-send-pacs008-mx-convert-bank-sender}", groupId = "${kafka.consumergroup}", containerFactory = "kafkaListenerContainerFactory4")
//    @SendTo("outputTopic")
//    public String topicAchSendPacs008ToBankSender4(@Payload String message) throws Exception {
//        log.info("**********CONSUMER 4**********");
//        log.info("ACH gửi pacs008 xml cho bank receiver " + message);
//        ValidateXMConverterResponse response = httpService.callApiMxConvertSendPacs008(message);
//        return Convert.convertObjectToJson(response);
//    }
//    @KafkaListener(topics = "${kafka.topic.ach-send-pacs008-mx-convert-bank-sender}", groupId = "${kafka.consumergroup}", containerFactory = "kafkaListenerContainerFactory5")
//    @SendTo("outputTopic")
//    public String topicAchSendPacs008ToBankSender5(@Payload String message) throws Exception {
//        log.info("ACH gửi pacs008 xml cho bank receiver " + message);
//        ValidateXMConverterResponse response = httpService.callApiMxConvertSendPacs008(message);
//        return Convert.convertObjectToJson(response);
//    }
//    @KafkaListener(topics = "${kafka.topic.ach-send-pacs008-mx-convert-bank-sender}", groupId = "${kafka.consumergroup}", containerFactory = "kafkaListenerContainerFactory6")
//    @SendTo("outputTopic")
//    public String topicAchSendPacs008ToBankSender6(@Payload String message) throws Exception {
//        log.info("ACH gửi pacs008 xml cho bank receiver " + message);
//        ValidateXMConverterResponse response = httpService.callApiMxConvertSendPacs008(message);
//        return Convert.convertObjectToJson(response);
//    }
//    @KafkaListener(topics = "${kafka.topic.ach-send-pacs008-mx-convert-bank-sender}", groupId = "${kafka.consumergroup}", containerFactory = "kafkaListenerContainerFactory7")
//    @SendTo("outputTopic")
//    public String topicAchSendPacs008ToBankSender7(@Payload String message) throws Exception {
//        log.info("ACH gửi pacs008 xml cho bank receiver " + message);
//        ValidateXMConverterResponse response = httpService.callApiMxConvertSendPacs008(message);
//        return Convert.convertObjectToJson(response);
//    }


    /**
     * Ngân hàng nhận lệnh sử lí bản tin pacs.008 và gửi lại bản tin pacs.002 về ACH
     */
    @KafkaListener(topics = "${kafka.topic.bank-receiver-process-pacs008}", groupId = "${kafka.consumergroup}", containerFactory = "kafkaListenerContainerFactory")
    @SendTo("outputTopic")
    public String processPacs008(@Payload String message) throws Exception {
        log.info("**********CONSUMER 1**********");
        log.info("Bank receiver gửi bản tin pacs002 đến ACH " + message);
        ValidateXMConverterResponse response = httpService.callApiMXConverterCallACH(message,"002");
        return Convert.convertObjectToJson(response);
    }
    @KafkaListener(topics = "${kafka.topic.bank-receiver-process-pacs008}", groupId = "${kafka.consumergroup}", containerFactory = "kafkaListenerContainerFactory2")
    @SendTo("outputTopic")
    public String processPacs008V2(@Payload String message) throws Exception {
        log.info("**********CONSUMER 2**********");
        log.info("Bank receiver gửi bản tin pacs002 đến ACH " + message);
        ValidateXMConverterResponse response = httpService.callApiMXConverterCallACH(message,"002");
        return Convert.convertObjectToJson(response);
    }
//    @KafkaListener(topics = "${kafka.topic.bank-receiver-process-pacs008}", groupId = "${kafka.consumergroup}", containerFactory = "kafkaListenerContainerFactory3")
//    @SendTo("outputTopic")
//    public String processPacs008v3(@Payload String message) throws Exception {
//        log.info("Bank receiver gửi bản tin pacs002 đến ACH " + message);
//        ValidateXMConverterResponse response = httpService.callApiMXConverterCallACH(message,"002");
//        return Convert.convertObjectToJson(response);
//    }
//    @KafkaListener(topics = "${kafka.topic.bank-receiver-process-pacs008}", groupId = "${kafka.consumergroup}", containerFactory = "kafkaListenerContainerFactory4")
//    @SendTo("outputTopic")
//    public String processPacs008V4(@Payload String message) throws Exception {
//        log.info("Bank receiver gửi bản tin pacs002 đến ACH " + message);
//        ValidateXMConverterResponse response = httpService.callApiMXConverterCallACH(message,"002");
//        return Convert.convertObjectToJson(response);
//    }
//    @KafkaListener(topics = "${kafka.topic.bank-receiver-process-pacs008}", groupId = "${kafka.consumergroup}", containerFactory = "kafkaListenerContainerFactory5")
//    @SendTo("outputTopic")
//    public String processPacs008v5(@Payload String message) throws Exception {
//        log.info("Bank receiver gửi bản tin pacs002 đến ACH " + message);
//        ValidateXMConverterResponse response = httpService.callApiMXConverterCallACH(message,"002");
//        return Convert.convertObjectToJson(response);
//    }
//    @KafkaListener(topics = "${kafka.topic.bank-receiver-process-pacs008}", groupId = "${kafka.consumergroup}", containerFactory = "kafkaListenerContainerFactory6")
//    @SendTo("outputTopic")
//    public String processPacs008V6(@Payload String message) throws Exception {
//        log.info("Bank receiver gửi bản tin pacs002 đến ACH " + message);
//        ValidateXMConverterResponse response = httpService.callApiMXConverterCallACH(message,"002");
//        return Convert.convertObjectToJson(response);
//    }
//    @KafkaListener(topics = "${kafka.topic.bank-receiver-process-pacs008}", groupId = "${kafka.consumergroup}", containerFactory = "kafkaListenerContainerFactory7")
//    @SendTo("outputTopic")
//    public String processPacs008V7(@Payload String message) throws Exception {
//        log.info("Bank receiver gửi bản tin pacs002 đến ACH " + message);
//        ValidateXMConverterResponse response = httpService.callApiMXConverterCallACH(message, "002");
//        return Convert.convertObjectToJson(response);
//    }

        /**
         * ACH xác nhận đã nhận bản tin với ngân hàng nhận lệnh
         */
    @KafkaListener(topics = "${kafka.topic.mx-convert-ach}", groupId = "${kafka.consumergroup}", containerFactory = "kafkaListenerContainerFactory")
    @SendTo("outputTopic")
    public String achConfrmBankReceiver(@Payload String message) throws Exception {
        log.info("**********CONSUMER 1**********");
        log.info("ACH gửi message ack để xác nhận với Bank Receiver " + message);
        ValidateXMConverterResponse response = httpService.callApiAchConfrmBank(message);
        return Convert.convertObjectToJson(response);
    }
    @KafkaListener(topics = "${kafka.topic.mx-convert-ach}", groupId = "${kafka.consumergroup}", containerFactory = "kafkaListenerContainerFactory2")
    @SendTo("outputTopic")
    public String achConfrmBankReceiver2(@Payload String message) throws Exception {
        log.info("**********CONSUMER 2**********");
        log.info("ACH gửi message ack để xác nhận với Bank Receiver " + message);
        ValidateXMConverterResponse response = httpService.callApiAchConfrmBank(message);
        return Convert.convertObjectToJson(response);
    }
//    @KafkaListener(topics = "${kafka.topic.mx-convert-ach}", groupId = "${kafka.consumergroup}", containerFactory = "kafkaListenerContainerFactory3")
//    @SendTo("outputTopic")
//    public String achConfrmBankReceiver3(@Payload String message) throws Exception {
//        log.info("ACH gửi message ack để xác nhận với Bank Receiver " + message);
//        ValidateXMConverterResponse response = httpService.callApiAchConfrmBank(message);
//        return Convert.convertObjectToJson(response);
//    }
//    @KafkaListener(topics = "${kafka.topic.mx-convert-ach}", groupId = "${kafka.consumergroup}", containerFactory = "kafkaListenerContainerFactory4")
//    @SendTo("outputTopic")
//    public String achConfrmBankReceiver4(@Payload String message) throws Exception {
//        log.info("ACH gửi message ack để xác nhận với Bank Receiver " + message);
//        ValidateXMConverterResponse response = httpService.callApiAchConfrmBank(message);
//        return Convert.convertObjectToJson(response);
//    }
//    @KafkaListener(topics = "${kafka.topic.mx-convert-ach}", groupId = "${kafka.consumergroup}", containerFactory = "kafkaListenerContainerFactory5")
//    @SendTo("outputTopic")
//    public String achConfrmBankReceiver5(@Payload String message) throws Exception {
//        log.info("ACH gửi message ack để xác nhận với Bank Receiver " + message);
//        ValidateXMConverterResponse response = httpService.callApiAchConfrmBank(message);
//        return Convert.convertObjectToJson(response);
//    }
//    @KafkaListener(topics = "${kafka.topic.mx-convert-ach}", groupId = "${kafka.consumergroup}", containerFactory = "kafkaListenerContainerFactory6")
//    @SendTo("outputTopic")
//    public String achConfrmBankReceiver6(@Payload String message) throws Exception {
//        log.info("ACH gửi message ack để xác nhận với Bank Receiver " + message);
//        ValidateXMConverterResponse response = httpService.callApiAchConfrmBank(message);
//        return Convert.convertObjectToJson(response);
//    }
//    @KafkaListener(topics = "${kafka.topic.mx-convert-ach}", groupId = "${kafka.consumergroup}", containerFactory = "kafkaListenerContainerFactory7")
//    @SendTo("outputTopic")
//    public String achConfrmBankReceiver7(@Payload String message) throws Exception {
//        log.info("ACH gửi message ack để xác nhận với Bank Receiver " + message);
//        ValidateXMConverterResponse response = httpService.callApiAchConfrmBank(message);
//        return Convert.convertObjectToJson(response);
//    }


    /**
     * ACH gửi bản tin camt.025 sang MX Converter để gửi sang ngân hàng nhận lệnh
     */
    @KafkaListener(topics = "${kafka.topic.ach-confirm-send-camt-025}", groupId = "${kafka.consumergroup}", containerFactory = "kafkaListenerContainerFactory")
    @SendTo("outputTopic")
    public String achSendCamt025(@Payload String message) throws Exception {
        log.info("**********CONSUMER 1**********");
        log.info("ACH gửi bản tin camt025 cho Bank Receiver: " + message);
        ValidateXMConverterResponse response = httpService.callApiAchSendCamt025(message);
        return Convert.convertObjectToJson(response);
    }
    @KafkaListener(topics = "${kafka.topic.ach-confirm-send-camt-025}", groupId = "${kafka.consumergroup}", containerFactory = "kafkaListenerContainerFactory2")
    @SendTo("outputTopic")
    public String achSendCamt025v2(@Payload String message) throws Exception {
        log.info("**********CONSUMER 2**********");
        log.info("ACH gửi bản tin camt025 cho Bank Receiver: " + message);
        ValidateXMConverterResponse response = httpService.callApiAchSendCamt025(message);
        return Convert.convertObjectToJson(response);
    }
//    @KafkaListener(topics = "${kafka.topic.ach-confirm-send-camt-025}", groupId = "${kafka.consumergroup}", containerFactory = "kafkaListenerContainerFactory3")
//    @SendTo("outputTopic")
//    public String achSendCamt025v3(@Payload String message) throws Exception {
//        log.info("ACH gửi bản tin camt025 cho Bank Receiver: " + message);
//        ValidateXMConverterResponse response = httpService.callApiAchSendCamt025(message);
//        return Convert.convertObjectToJson(response);
//    }
//    @KafkaListener(topics = "${kafka.topic.ach-confirm-send-camt-025}", groupId = "${kafka.consumergroup}", containerFactory = "kafkaListenerContainerFactory4")
//    @SendTo("outputTopic")
//    public String achSendCamt025v4(@Payload String message) throws Exception {
//        log.info("ACH gửi bản tin camt025 cho Bank Receiver: " + message);
//        ValidateXMConverterResponse response = httpService.callApiAchSendCamt025(message);
//        return Convert.convertObjectToJson(response);
//    }
//    @KafkaListener(topics = "${kafka.topic.ach-confirm-send-camt-025}", groupId = "${kafka.consumergroup}", containerFactory = "kafkaListenerContainerFactory5")
//    @SendTo("outputTopic")
//    public String achSendCamt025v5(@Payload String message) throws Exception {
//        log.info("ACH gửi bản tin camt025 cho Bank Receiver: " + message);
//        ValidateXMConverterResponse response = httpService.callApiAchSendCamt025(message);
//        return Convert.convertObjectToJson(response);
//    }
//    @KafkaListener(topics = "${kafka.topic.ach-confirm-send-camt-025}", groupId = "${kafka.consumergroup}", containerFactory = "kafkaListenerContainerFactory6")
//    @SendTo("outputTopic")
//    public String achSendCamt025v6(@Payload String message) throws Exception {
//        log.info("ACH gửi bản tin camt025 cho Bank Receiver: " + message);
//        ValidateXMConverterResponse response = httpService.callApiAchSendCamt025(message);
//        return Convert.convertObjectToJson(response);
//    }
//    @KafkaListener(topics = "${kafka.topic.ach-confirm-send-camt-025}", groupId = "${kafka.consumergroup}", containerFactory = "kafkaListenerContainerFactory7")
//    @SendTo("outputTopic")
//    public String achSendCamt025v7(@Payload String message) throws Exception {
//        log.info("ACH gửi bản tin camt025 cho Bank Receiver: " + message);
//        ValidateXMConverterResponse response = httpService.callApiAchSendCamt025(message);
//        return Convert.convertObjectToJson(response);
//    }
    /**
     * ACH gửi bản tin pacs002 sang MX Converter để gửi sang ngân hàng thành vien
     */
    @KafkaListener(topics = "${kafka.topic.ach-confirm-send-pacs-002ToBank}", groupId = "${kafka.consumergroup}", containerFactory = "kafkaListenerContainerFactory")
    @SendTo("outputTopic")
    public String achSendPacs002(@Payload String message) throws Exception {
        log.info("**********CONSUMER 1**********");
        log.info("ACH gửi pacs002 vs pacs002 update cho NHTV !!!! message: " + message);
        ValidateXMConverterResponse response = httpService.callApiAchSend002(message);
        return Convert.convertObjectToJson(response);
    }
    @KafkaListener(topics = "${kafka.topic.ach-confirm-send-pacs-002ToBank}", groupId = "${kafka.consumergroup}", containerFactory = "kafkaListenerContainerFactory2")
    @SendTo("outputTopic")
    public String achSendPacs002v2(@Payload String message) throws Exception {
        log.info("**********CONSUMER 2**********");
        log.info("ACH gửi pacs002 vs pacs002 update cho NHTV !!!! message: " + message);
        ValidateXMConverterResponse response = httpService.callApiAchSend002(message);
        return Convert.convertObjectToJson(response);
    }
//    @KafkaListener(topics = "${kafka.topic.ach-confirm-send-pacs-002ToBank}", groupId = "${kafka.consumergroup}", containerFactory = "kafkaListenerContainerFactory3")
//    @SendTo("outputTopic")
//    public String achSendPacs002v3(@Payload String message) throws Exception {
//        log.info("ACH gửi pacs002 vs pacs002 update cho NHTV !!!! message: " + message);
//        ValidateXMConverterResponse response = httpService.callApiAchSend002(message);
//        return Convert.convertObjectToJson(response);
//    }
//    @KafkaListener(topics = "${kafka.topic.ach-confirm-send-pacs-002ToBank}", groupId = "${kafka.consumergroup}", containerFactory = "kafkaListenerContainerFactory4")
//    @SendTo("outputTopic")
//    public String achSendPacs002v4(@Payload String message) throws Exception {
//        log.info("ACH gửi pacs002 vs pacs002 update cho NHTV !!!! message: " + message);
//        ValidateXMConverterResponse response = httpService.callApiAchSend002(message);
//        return Convert.convertObjectToJson(response);
//    }
//    @KafkaListener(topics = "${kafka.topic.ach-confirm-send-pacs-002ToBank}", groupId = "${kafka.consumergroup}", containerFactory = "kafkaListenerContainerFactory5")
//    @SendTo("outputTopic")
//    public String achSendPacs002v5(@Payload String message) throws Exception {
//        log.info("ACH gửi pacs002 vs pacs002 update cho NHTV !!!! message: " + message);
//        ValidateXMConverterResponse response = httpService.callApiAchSend002(message);
//        return Convert.convertObjectToJson(response);
//    }
//    @KafkaListener(topics = "${kafka.topic.ach-confirm-send-pacs-002ToBank}", groupId = "${kafka.consumergroup}", containerFactory = "kafkaListenerContainerFactory6")
//    @SendTo("outputTopic")
//    public String achSendPacs002v6(@Payload String message) throws Exception {
//        log.info("ACH gửi pacs002 vs pacs002 update cho NHTV !!!! message: " + message);
//        ValidateXMConverterResponse response = httpService.callApiAchSend002(message);
//        return Convert.convertObjectToJson(response);
//    }
//    @KafkaListener(topics = "${kafka.topic.ach-confirm-send-pacs-002ToBank}", groupId = "${kafka.consumergroup}", containerFactory = "kafkaListenerContainerFactory7")
//    @SendTo("outputTopic")
//    public String achSendPacs002v7(@Payload String message) throws Exception {
//        log.info("ACH gửi pacs002 vs pacs002 update cho NHTV !!!! message: " + message);
//        ValidateXMConverterResponse response = httpService.callApiAchSend002(message);
//        return Convert.convertObjectToJson(response);
//    }
}
