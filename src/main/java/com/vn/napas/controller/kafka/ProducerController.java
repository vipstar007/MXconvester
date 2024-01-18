//package com.vn.napas.controller.kafka;
//
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.dataformat.xml.XmlMapper;
//import com.vn.napas.config.PropertiesConfig;
//import com.vn.napas.model.kafka.KafkaEvent;
//import com.vn.napas.model.kafka.KafkaMessage;
//import com.vn.napas.model.pacs08.JsonObjectPacs08;
//import com.vn.napas.model.response.BaseResponse;
//import com.vn.napas.utils.Constants;
//import com.vn.napas.utils.ResponseUtils;
//import com.vn.napas.utils.ValidUtils;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.apache.kafka.clients.producer.ProducerRecord;
//import org.apache.kafka.common.header.internals.RecordHeader;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.ResponseEntity;
//import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
//import org.springframework.kafka.requestreply.RequestReplyFuture;
//import org.springframework.kafka.support.KafkaHeaders;
//import org.springframework.kafka.support.SendResult;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/v1")
//public class ProducerController {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerController.class);
//
//    @Autowired
//    ReplyingKafkaTemplate<String, KafkaEvent, KafkaEvent> kafkaTemplate;
//
//    @Autowired
//    ReplyingKafkaTemplate<String, KafkaMessage, BaseResponse> kafkaTemplateReply;
//
//    @Autowired
//    PropertiesConfig config;
//
//    @Value("${kafka.topic.request-topic}")
//    String requestTopic;
//
//
//    @Value("${kafka.topic.request-topic1}")
//    String requestTopic1;
//
//
//    @GetMapping("/welcome")
//    public String welcome() {
//        return "Welcome this is public api";
//    }
//
//
//    @RequestMapping(value = {"/vasnrtapi"}, method = RequestMethod.POST)
//    public ResponseEntity<Object> vasnrtapi(@RequestBody String dataReq, @RequestParam String sys) {
//        try {
//
//            LOGGER.info("system info: " + sys);
//            JsonObjectPacs08 jsonObject = new JsonObjectPacs08();
//            ObjectMapper objectMapper = new ObjectMapper();
//            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//            if (Constants.BANK.equals(sys)) {
//
//                jsonObject = objectMapper.readValue(dataReq, JsonObjectPacs08.class);
//                LOGGER.info("input request BANK: " + objectMapper.writeValueAsString(jsonObject));
//            } else if (Constants.ACH.equals(sys)) {
//                XmlMapper xmlMapper = new XmlMapper();
//                xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//                jsonObject = xmlMapper.readValue(dataReq, JsonObjectPacs08.class);
//                LOGGER.info("input request XML: " + xmlMapper.writeValueAsString(jsonObject));
//            } else {
//                return ResponseEntity.badRequest().body("Please check system param");
//            }
//
//
//            //validate data
//            BaseResponse checkRequest = validData(jsonObject);
//            if (checkRequest != null) {
//                return ResponseEntity.badRequest().body(checkRequest);
//            }
//
//            //set topic
//            String topicRequest = "";
//            String topicReply = "";
//            if ("pacs.008.001.07".equals(jsonObject.getHeader().getMessageIdentifier())) {
//                topicRequest = config.getPacs08TopicRequest();
//                topicReply = config.getPacs08TopicReply();
//            }
//
//
//            // create producer record
//            KafkaMessage data = new KafkaMessage();
//            data.setData(objectMapper.writeValueAsString(jsonObject));
//            data.setSysType(sys);
//            ProducerRecord<String, KafkaMessage> record = new ProducerRecord<String, KafkaMessage>(topicRequest, data);
//            // set reply topic in header
//            record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, topicReply.getBytes()));
//            // post in kafka topic
//            RequestReplyFuture<String, KafkaMessage, BaseResponse> sendAndReceive = kafkaTemplateReply.sendAndReceive(record);
//
//            // confirm if producer produced successfully
//            SendResult<String, KafkaMessage> sendResult = sendAndReceive.getSendFuture().get();
//
//            //print all headers
//            sendResult.getProducerRecord().headers().forEach(header -> System.out.println(header.key() + ":" + header.value().toString()));
//
//            // get consumer record
//            ConsumerRecord<String, BaseResponse> consumerRecord = sendAndReceive.get();
//            // return consumer value
//            return ResponseEntity.ok(consumerRecord.value());
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("Fail: " + e.toString());
//        }
//    }
//
//    public BaseResponse validData(JsonObjectPacs08 jsonObject) {
//
//        if (ValidUtils.isNullOrEmpty(jsonObject.getHeader().getMessageIdentifier())) {
//            return ResponseUtils.setResponseError(Constants.REQUEST_MESSAGEIDENTIFIER);
//        }
//        if (!ValidUtils.isTimeStamp(jsonObject.getHeader().getTimestamp())) {
//            return ResponseUtils.setResponseError(Constants.REQUEST_TIME_STAMP);
//        }
//
//        return null;
//    }
//
////	@PostMapping(value="/mxconverter")
////	public String sum(@RequestBody String request) throws InterruptedException, ExecutionException, JsonProcessingException {
////		ObjectMapper obj = new ObjectMapper();
////		XmlMapper xmlMapper = new XmlMapper();
////		BaseResponse bs = new BaseResponse();
////		KafkaEvent data = new KafkaEvent();
////
////		try {
////			Data dt = new Data();
////			data = obj.readValue(request, KafkaEvent.class);
////			log.info(data.toString());
////			dt = xmlMapper.readValue(data.getPayload().get("xml").toString(), Data.class);
////			log.info(dt.toString());
////		} catch (JsonProcessingException e) {
////			e.printStackTrace();
////			log.info(e.getMessage());
////			bs.setMessage("Nhập sai định dạng!");
////			bs.setCode(0);
////			KafkaEvent dataE = new KafkaEvent();
////			Map<String, Object> payload = new HashMap<>();
////			payload.put("xml", "<Data><num1>123</num1><num2>456</num2></Data>");
////			dataE.setPayload(payload);
////			bs.setData(obj.writeValueAsString(dataE));
////			return obj.writeValueAsString(bs);
////		}
////
////		// create producer record
////		ProducerRecord<String, KafkaEvent> record = new ProducerRecord<String, KafkaEvent>(requestTopic, data);
////		// set reply topic in header
////		record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, requestReplyTopic.getBytes()));
////		// post in kafka topic
////		RequestReplyFuture<String, KafkaEvent, KafkaEvent> sendAndReceive = kafkaTemplate.sendAndReceive(record);
////
////		// confirm if producer produced successfully
////		SendResult<String, KafkaEvent> sendResult = sendAndReceive.getSendFuture().get();
////
////		//print all headers
////		sendResult.getProducerRecord().headers().forEach(header -> System.out.println(header.key() + ":" + header.value().toString()));
////
////		// get consumer record
////		ConsumerRecord<String, KafkaEvent> consumerRecord = sendAndReceive.get();
////		// return consumer value
////		return consumerRecord.value().toString();
////	}
//
//    @RequestMapping(value = "/send", method = RequestMethod.POST)
//    public String sendMessage(@RequestBody KafkaEvent request) {
//        try {
//            kafkaTemplate.send(requestTopic1, request);
//            return "OK";
//        } catch (Exception e) {
//            return "Fail: " + e.toString();
//        }
//    }
//}
