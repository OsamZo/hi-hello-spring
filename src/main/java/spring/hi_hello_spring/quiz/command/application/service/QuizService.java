package spring.hi_hello_spring.quiz.command.application.service;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.hi_hello_spring.quiz.command.application.dto.QuizCreateDTO;
import spring.hi_hello_spring.quiz.command.application.dto.QuizUpdateDTO;
import spring.hi_hello_spring.quiz.command.domain.aggregate.entity.Quiz;
import spring.hi_hello_spring.quiz.command.domain.aggregate.entity.QuizCategory;
import spring.hi_hello_spring.quiz.command.domain.repository.QuizCategoryRepository;
import spring.hi_hello_spring.quiz.command.domain.repository.QuizRepository;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuizCategoryRepository quizCategoryRepository;
    private final ModelMapper modelMapper;

    /* 카테고리 별 퀴즈 등록 */
    @Transactional
    public QuizCreateDTO createQuiz(Long quizCategorySeq, QuizCreateDTO createDTO) {

        QuizCategory quizCategory = quizCategoryRepository.findById(quizCategorySeq)
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리를 찾을 수 없습니다."));

        Quiz quiz = modelMapper.map(createDTO, Quiz.class);

        quiz.Category(quizCategory);

        Quiz savedQuiz = quizRepository.save(quiz);

        return modelMapper.map(savedQuiz, QuizCreateDTO.class);
    }

    /* 카테고리 별 퀴즈 수정 */
    @Transactional
    public QuizUpdateDTO updateQuiz(Long quizSeq, Long quizCategorySeq, QuizUpdateDTO updateDTO){

        QuizCategory quizCategory = quizCategoryRepository.findById(quizCategorySeq)
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리를 찾을 수 없습니다."));

        Quiz quiz = quizRepository.findById(quizSeq)
                .orElseThrow(() -> new IllegalArgumentException("해당 퀴즈를 찾을 수 없습니다."));

        if(!quiz.getQuizCategorySeq().equals(quizCategory.getQuizCategorySeq())){
            throw new IllegalArgumentException("해당 퀴즈는 주어진 카테고리에 속하지 않습니다.");
        }

        modelMapper.map(updateDTO, quiz);

        return modelMapper.map(quiz, QuizUpdateDTO.class);
    }

    /* 카테고리 별 퀴즈 삭제 */
    @Transactional
    public boolean deleteQuiz(Long quizSeq, Long quizCategorySeq){

        QuizCategory quizCategory = quizCategoryRepository.findById(quizCategorySeq)
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리를 찾을 수 없습니다."));

        Quiz quiz = quizRepository.findById(quizSeq)
                .orElseThrow(() -> new IllegalArgumentException("해당 퀴즈를 찾을 수 없습니다."));

        if(!quiz.getQuizCategorySeq().equals(quizCategory.getQuizCategorySeq())){
            throw new IllegalArgumentException("해당 퀴즈는 주어진 카테고리에 속하지 않습니다.");
        }

        quizRepository.delete(quiz);

        return true;
    }
}
