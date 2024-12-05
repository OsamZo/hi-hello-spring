package spring.hi_hello_spring.quiz.query.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.hi_hello_spring.quiz.query.dto.QuizResultAllQueryDTO;
import spring.hi_hello_spring.quiz.query.mapper.QuizResultMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizResultQueryService {

    private final QuizResultMapper quizResultMapper;

    /* 퀴즈 결과 목록 전체 조회 */
    public List<QuizResultAllQueryDTO> getAllQuizResult(Long QuizCategorySeq){

        return quizResultMapper.findAllQuizResult(QuizCategorySeq);
    }
}
