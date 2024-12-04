package spring.hi_hello_spring.quiz.query.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class QuizAllQueryDTO {

    private Long quizSeq;
    private String quizQuestion;
    private Boolean quizAnswer;
    private String quizExplanation;
}
