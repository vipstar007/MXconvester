package com.vn.napas.config.kafka.producer;

import com.vn.napas.model.response.ValidateXMConverterResponse;
import com.vn.napas.utils.Convert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProducer {
    private final ReplyingKafkaTemplate<String, String, String> kafkaTemplateReply;

    public ValidateXMConverterResponse sendMessage(String topicProducer,String topicConsumer, String data) {
        try {
            ProducerRecord<String, String> record = new ProducerRecord<>(topicProducer, data);
            // set reply topic in header
            record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, topicConsumer.getBytes()));
            // Set the reply timeout in milliseconds
//           long replyTimeout = 10000; // 10 seconds
            // post in kafka topic
            RequestReplyFuture<String, String, String> sendAndReceive = kafkaTemplateReply.sendAndReceive(record,Duration.ofSeconds(10));

            // confirm if producer produced successfully
            SendResult<String, String> sendResult = sendAndReceive.getSendFuture().get();
//            kafkaTemplateReply.setDefaultReplyTimeout(Duration.ofSeconds(5));


            //print all headers
            sendResult.getProducerRecord().headers().forEach(header -> System.out.println(header.key() + ":" + Arrays.toString(header.value())));
            // get consumer record
            ConsumerRecord<String, String> consumerRecord = sendAndReceive.get();
            log.info("consumer record: {}", consumerRecord.value());
            return Convert.convertJsonToObject(consumerRecord.value());
        } catch (Exception ex) {
            log.error("KafkaProducer Exception: {}", ex.getMessage());
            ex.printStackTrace();
            return ValidateXMConverterResponse.builder().message("false").type("success").message("Message false processed").build();
        }
    }
}
