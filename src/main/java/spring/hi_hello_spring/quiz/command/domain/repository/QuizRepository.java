package spring.hi_hello_spring.quiz.command.domain.repository;

import spring.hi_hello_spring.quiz.command.domain.aggregate.entity.Quiz;

import java.util.Optional;

public interface QuizRepository {
    Quiz save(Quiz quiz);

    Optional<Quiz> findById(Long quizSeq);

    void delete(Quiz quiz);
}
