package spring.hi_hello_spring.wiki.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.hi_hello_spring.common.aggregate.entity.BaseTimeEntity;

@Entity
@Table(name = "wiki_mod_content")
@NoArgsConstructor
@Getter
public class WikiModContent extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wikiModContentSeq;
    private Long wikiSeq;
    private Long employeeSeq;
    private Long wikiSnapshotSeq;

    @Lob // 대량 데이터를 처리하도록 지시
    @Column(columnDefinition = "LONGTEXT")
    private String modContent;

    @Builder
    public WikiModContent(Long wikiSeq, Long employeeSeq, Long wikiSnapshotSeq, String modContent) {
        this.wikiSeq = wikiSeq;
        this.employeeSeq = employeeSeq;
        this.wikiSnapshotSeq = wikiSnapshotSeq;
        this.modContent = modContent;
    }
}