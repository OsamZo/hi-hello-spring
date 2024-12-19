package spring.hi_hello_spring.wiki.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import spring.hi_hello_spring.wiki.query.dto.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface WikiMapper {

    List<WikiListQueryDTO> findAllWikis();
    
    List<WikiHistoryListQueryDTO> findWikiHistories(Long wikiSeq);
    
    WikiSnapshotQueryDTO findWikiSnapshotByWikiSeq(Long wikiSeq);
    
    List<WikiModContentQueryDTO> findWikiModContentsByWikiSeqAndWikiSnapshotSeq(Long wikiSeq, Long wikiSnapshotSeq);
    
    LocalDateTime findWikiLatestModDateByWikiSeq(Long wikiSeq);

    WikiSnapshotQueryDTO findLatestWikiSnapshot(Long wikiSeq, Long wikiModContentSeq);

    List<WikiModContentQueryDTO> findWikiModContentsLessThanWikiModContentSeq(Long wikiSeq, Long wikiSnapshotSeq, Long wikiModContentSeq);
}