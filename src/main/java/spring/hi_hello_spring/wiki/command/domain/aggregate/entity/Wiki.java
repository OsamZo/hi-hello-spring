package spring.hi_hello_spring.wiki.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.hi_hello_spring.common.aggregate.entity.BaseTimeEntity;

@Entity
@Table(name = "wiki")
@NoArgsConstructor
@Getter
public class Wiki extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wikiSeq;

    private String wikiTitle;

    private int wikiCurrentVer;

    private boolean wikiDeletedStatus = false;

    @Builder
    public Wiki(String wikiTitle, int wikiCurrentVer) {
        this.wikiTitle = wikiTitle;
        this.wikiCurrentVer = wikiCurrentVer;
    }

    public void updateWikiCurrentVer(int wikiCurrentVer) {
        this.wikiCurrentVer = wikiCurrentVer;
    }
}