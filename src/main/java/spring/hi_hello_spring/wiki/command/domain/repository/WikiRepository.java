package spring.hi_hello_spring.wiki.command.domain.repository;

import spring.hi_hello_spring.wiki.command.domain.aggregate.entity.Wiki;

import java.util.Optional;

public interface WikiRepository {

    Wiki save(Wiki wiki);
    Optional<Wiki> findById(Long wikiSeq);
}