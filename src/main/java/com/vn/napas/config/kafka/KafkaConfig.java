package com.vn.napas.config.kafka;

import com.vn.napas.config.PropertiesConfig;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;


@Configuration
@RequiredArgsConstructor
public class KafkaConfig {

    private final PropertiesConfig propertiesConfig;

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, propertiesConfig.getBootstrapServers());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 30000);
        return props;
    }

    @Bean
    @Qualifier("consumerConfigs")
    @Primary
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, propertiesConfig.getBootstrapServers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, propertiesConfig.getConsumerGroup());
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 20000);
        return props;
    }

    /*Start Config topic MXConverter call ACH*/
    //KafkaEvent config start>>>

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactoryReply());
    }

    @Bean
    public ReplyingKafkaTemplate<String, String, String> replyKafkaTemplate(ProducerFactory<String, String> pf, KafkaMessageListenerContainer<String, String> container){
        return new ReplyingKafkaTemplate<>(pf, container);

    }
    @Bean
    @Qualifier("consumerFactory")
    @Primary
    public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs(),new StringDeserializer(),new JsonDeserializer<>(String.class));
    }

    @Bean
    @Qualifier("kafkaListenerContainerFactory")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setReplyTemplate(kafkaTemplate());

        return factory;
    }
    @Bean
    @Qualifier("kafkaListenerContainerFactory2")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory2() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setReplyTemplate(kafkaTemplate());
        return factory;
    }
    @Bean
    @Qualifier("kafkaListenerContainerFactory3")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory3() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setReplyTemplate(kafkaTemplate());
        return factory;
    }
    @Bean
    @Qualifier("kafkaListenerContainerFactory4")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory4() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setReplyTemplate(kafkaTemplate());
        return factory;
    }
    @Bean
    @Qualifier("kafkaListenerContainerFactory5")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory5() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setReplyTemplate(kafkaTemplate());
        return factory;
    }
    @Bean
    @Qualifier("kafkaListenerContainerFactory6")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory6() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setReplyTemplate(kafkaTemplate());
        return factory;
    }
    @Bean
    @Qualifier("kafkaListenerContainerFactory7")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory7() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setReplyTemplate(kafkaTemplate());
        return factory;
    }


    //<<<KafkaEvent config end
    //Consomer group2 config
//    @Bean
//    @Qualifier("consumerConfigs2")
//    public Map<String, Object> consumerConfigs2() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, propertiesConfig.getBootstrapServers());
//        props.put(ConsumerConfig.GROUP_ID_CONFIG, propertiesConfig.getConsumerGroup()+1);
//        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 20000);
//        return props;
//    }
//    @Bean
//    @Qualifier("consumerFactory2")
//    public ConsumerFactory<String, String> consumerFactory2() {
//        return new DefaultKafkaConsumerFactory<>(consumerConfigs2(),new StringDeserializer(),new JsonDeserializer<>(String.class));
//    }
//    @Bean
//    @Qualifier("kafkaListenerContainerFactory2")
//    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory2() {
//        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory2());
//        factory.setReplyTemplate(kafkaTemplate());
//        return factory;
//    }

    //KafMessage config start>>>
    @Bean
    public ProducerFactory<String, String> producerFactoryReply() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplateReply() {
        return new KafkaTemplate<>(producerFactoryReply());
    }

    @Bean
    public KafkaMessageListenerContainer<String, String> replyContainerReply(ConsumerFactory<String, String> cf) {
        ContainerProperties containerProperties = new ContainerProperties(propertiesConfig.getTopicConsumer());
        containerProperties.setAckTime(10000);
        containerProperties.setPollTimeout(10000);
        containerProperties.setShutdownTimeout(10000);
        return new KafkaMessageListenerContainer<>(cf, containerProperties);
    }


//    @Bean
//    public ReplyingKafkaTemplate<String, String, String> replyKafkaTemplateReply
//            (ProducerFactory<String,String > pf,KafkaMessageListenerContainer<String, String> container){
//        return new ReplyingKafkaTemplate<>(pf, container);
//
//    }


    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactoryReply() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setReplyTemplate(kafkaTemplateReply());
        return factory;
    }


}

