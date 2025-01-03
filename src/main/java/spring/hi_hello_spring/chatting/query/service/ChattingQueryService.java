package spring.hi_hello_spring.chatting.query.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.hi_hello_spring.chatting.query.mapper.ChattingMapper;

@Service
@RequiredArgsConstructor
public class ChattingQueryService {

    private final ChattingMapper chattingMapper;

    public String getUserMentoringChatRooms(Long userSeq) {
        return chattingMapper.findMentoringChatRoomByUserSeq(userSeq);
    }

    public String getUserGroupingChatRooms(Long userSeq) {
        return chattingMapper.findGroupingChatRoomsByUserSeq(userSeq);
    }
}
