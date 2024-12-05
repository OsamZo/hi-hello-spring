package spring.hi_hello_spring.wiki.command.domain.repository;

import spring.hi_hello_spring.wiki.command.domain.aggregate.entity.WikiSnapshot;

import java.util.Optional;

public interface WikiSnapshotRepository {

    WikiSnapshot save(WikiSnapshot wikiSnapshot);
    Optional<WikiSnapshot> findByWikiSeqAndWikiSnapshotVer(Long wikiSeq, int wikiSnapshotVer);
}