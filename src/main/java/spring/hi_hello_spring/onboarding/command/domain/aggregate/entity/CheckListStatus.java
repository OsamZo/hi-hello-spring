package spring.hi_hello_spring.onboarding.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.hi_hello_spring.common.aggregate.entity.BaseTimeEntity;

@Entity
@Table(name = "checklist_status")
@NoArgsConstructor
@Getter
public class CheckListStatus extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long checklistStatusSeq;

    private Long employeeSeq;

    private Long checklistSeq;

    private Boolean listCheckedStatus = false;

    @Builder
    public CheckListStatus(Long checklistStatusSeq, Long employeeSeq, Long checklistSeq, Boolean listCheckedStatus){
        this.checklistStatusSeq = checklistStatusSeq;
        this.employeeSeq = employeeSeq;
        this.checklistSeq = checklistSeq;
        this.listCheckedStatus = listCheckedStatus;
    }
}
