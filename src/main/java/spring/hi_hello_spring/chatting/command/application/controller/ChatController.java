package spring.hi_hello_spring.chatting.command.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import spring.hi_hello_spring.chatting.command.application.dto.ChatRequestMessage;
import spring.hi_hello_spring.chatting.command.application.serivce.ChatRoomService;
import spring.hi_hello_spring.chatting.command.application.serivce.ChatService;
import spring.hi_hello_spring.chatting.command.application.serivce.KafkaProducerService;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;
import spring.hi_hello_spring.chatting.command.application.dto.ChatResponseMessage;
import spring.hi_hello_spring.common.util.CustomUserUtils;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/v1/chat")
@RestController // https 사용 x -> restAPI 아님
@Tag(name = "Chat", description = "채팅 API")
public class ChatController {

    private final ChatService chatService;
    private final KafkaProducerService kafkaProducerService;

    // 메시지 전송
    @MessageMapping("/{roomId}")
//    @SendTo("/sub/{roomId}")
    public ResponseEntity<Void> sendMessage(@DestinationVariable String roomId, @Payload ChatRequestMessage message) {
        log.info("Received message: {}", message);

        message.setRoomId(roomId); // mongoDB에 저장
        ChatResponseMessage savedMessage = chatService.saveMessage(message);
        log.info("Saved message: {}", savedMessage.getMessage());

        // kafka로 메시지 전송
        kafkaProducerService.sendMessageToKafka(savedMessage);
//        try {
//            objectMapper.writeValueAsString(message);
//            chatRoomService.saveChatMessage(roomId, message);
//            // Kafka에 메시지 발행 (JSON 형태로)
//            kafkaTemplate.send("chat-messages", roomId, message);
////            chatMessageListener.sendMessage(message);
//
//        } catch (Exception e) {
//            log.error("Failed to serialize message", e);
//            return ResponseEntity.status(500).build();  // 직렬화 실패 시 500 오류 반환
//        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{roomId}/messages")
    @Operation(summary = "채팅 내역", description = "특정 채팅방 내 메시지 조회")
    public ResponseEntity<ApiResponse<List<ChatResponseMessage>>> loadMessage(@PathVariable("roomId") String roomId) {
        // 채팅 내역을 조회하여 반환
        // 본인이 속한 것만 가져와야 하는디....  + Util에서 빼오기
        List<ChatResponseMessage> messages = chatService.chattingMessageList(CustomUserUtils.getCurrentEmployeeSeq() ,roomId);
        return ResponseUtil.successResponse(messages); // 내용 확인용
    }

    @Operation(summary = "채팅 메시지 삭제", description = "채팅 메시지 삭제")
    @DeleteMapping("/{messageId}")
    public ResponseEntity<ApiResponse<Void>> deleteMessage(@PathVariable("messageId") String messageId) {
        chatService.deleteMessage(messageId);
        return ResponseEntity.ok().build();
    }

}
