package spring.hi_hello_spring.quiz.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import spring.hi_hello_spring.quiz.query.dto.QuizResultAllQueryDTO;

import java.util.List;

@Mapper
public interface QuizResultMapper {
    List<QuizResultAllQueryDTO> findAllQuizResult(Long quizCategorySeq);
}
