package spring.hi_hello_spring.chatting.query.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChattingMapper {

    Long findChatRoomsByUserSeq(Long userSeq);
}