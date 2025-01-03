package spring.hi_hello_spring.chatting.command.application.serivce;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import spring.hi_hello_spring.chatting.command.application.dto.ChatResponseMessage;

@Service
@Slf4j
public class KafkaProducerService {
    private final KafkaTemplate<String, ChatResponseMessage> kafkaTemplate;

    @Autowired
    public KafkaProducerService(KafkaTemplate<String, ChatResponseMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessageToKafka(ChatResponseMessage message) {
        log.info("Sending Message to Kafka : {}-{}", message.getMessage(),message.getUserName());
        String topic = "chat-messages";  // 채팅방별 토픽
        kafkaTemplate.send(topic, message);  // Kafka에 메시지 전송
    }
}
