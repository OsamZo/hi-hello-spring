package spring.hi_hello_spring.wiki.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.hi_hello_spring.common.aggregate.entity.BaseTimeEntity;

@Entity
@Table(name = "wiki_snapshot")
@NoArgsConstructor
@Getter
public class WikiSnapshot extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wikiSnapshotSeq;

    private Long wikiSeq;

    private int wikiSnapshotVer;

    @Lob // 대량 데이터를 처리하도록 지시
    @Column(columnDefinition = "LONGTEXT")
    private String wikiSnapshotContent;

    public void updateWikiSeq(Long wikiSeq) {
        this.wikiSeq = wikiSeq;
    }

    public void updateWikiSnapshotVer(int wikiSnapshotVer) {
        this.wikiSnapshotVer = wikiSnapshotVer;
    }

    @Builder
    public WikiSnapshot(Long wikiSeq, int wikiSnapshotVer, String wikiSnapshotContent) {
        this.wikiSeq = wikiSeq;
        this.wikiSnapshotVer = wikiSnapshotVer;
        this.wikiSnapshotContent = wikiSnapshotContent;
    }
}