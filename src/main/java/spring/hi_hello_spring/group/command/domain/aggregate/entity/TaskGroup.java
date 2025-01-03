package spring.hi_hello_spring.group.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.hi_hello_spring.common.aggregate.entity.BaseTimeEntity;

@Entity
@Table(name = "task_group")
@NoArgsConstructor
@Getter
public class TaskGroup extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskGroupSeq;

    private Long taskSeq;

    private Integer taskGroupNum;

    private Boolean taskGroupActiveStatus = true;

    private String chatRoomSeq;

    public void updateTaskGroupNum(int groupNum) {
        this.taskGroupNum = groupNum;
    }

    public void saveChatRoomSeq(String chatRoomSeq) {
        this.chatRoomSeq = chatRoomSeq;
    }


    public void setTaskGroupActiveStatus(boolean b) {
        this.taskGroupActiveStatus = b;
    }

    public void updateTaskSeq(Long taskSeq) {
        this.taskSeq = taskSeq;
    }
}
