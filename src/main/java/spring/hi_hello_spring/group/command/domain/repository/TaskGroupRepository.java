package spring.hi_hello_spring.group.command.domain.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import spring.hi_hello_spring.group.command.domain.aggregate.entity.TaskGroup;

import java.util.List;
import java.util.Optional;

public interface TaskGroupRepository {

    Optional<TaskGroup> findById(Long taskGroupSeq);

    TaskGroup save(TaskGroup taskSeq);

    TaskGroup findByChatRoomSeq(String roomId);

    List<TaskGroup> findByTaskSeq(Long taskSeq);
}