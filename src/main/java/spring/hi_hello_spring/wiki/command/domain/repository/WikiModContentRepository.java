package spring.hi_hello_spring.wiki.command.domain.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import spring.hi_hello_spring.wiki.command.domain.aggregate.entity.WikiModContent;

import java.util.List;

public interface WikiModContentRepository {

    WikiModContent save(WikiModContent wikiModContent);

    @Query("SELECT wm.modContent FROM WikiModContent wm WHERE wm.wikiSeq = :wikiSeq AND wm.wikiSnapshotSeq = :latestSnapshotSeq ORDER BY wm.wikiModContentSeq")
    List<String> findModContentsByWikiSeqAndWikiSnapshotSeqOrderByWikiModContentSeq(@Param("wikiSeq") Long wikiSeq, @Param("latestSnapshotSeq") Long latestSnapshotSeq);

    Long countByWikiSeqAndWikiSnapshotSeq(Long wikiSeq, Long latestSnapshotSeq);
}