package spring.hi_hello_spring.evaluation.command.application.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.hi_hello_spring.common.exception.CustomException;
import spring.hi_hello_spring.common.exception.ErrorCodeType;
import spring.hi_hello_spring.employee.command.domain.repository.EmployeeRepository;
import spring.hi_hello_spring.evaluation.command.application.dto.TaskEvalCreateDTO;
import spring.hi_hello_spring.evaluation.command.domain.aggregate.entity.EvalList;
import spring.hi_hello_spring.evaluation.command.domain.aggregate.entity.TaskEval;
import spring.hi_hello_spring.evaluation.command.domain.repository.EvalListRepository;
import spring.hi_hello_spring.evaluation.command.domain.repository.TaskEvalRepository;
import spring.hi_hello_spring.evaluation.command.domain.repository.TaskSubmitRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskEvalService {

    private final TaskSubmitRepository taskSubmitRepository;
    private final TaskEvalRepository taskEvalRepository;
    private final EvalListRepository evalListRepository;

    @Transactional
    public void createTaskEval(Long taskSubmitSeq, List<TaskEvalCreateDTO> taskEvalCreateDTOS) {
        // taskSubmitSeq에 대한 데이터가 존재하는지 확인
        if (!taskSubmitRepository.existsById(taskSubmitSeq)) {
            throw new CustomException(ErrorCodeType.DATA_NOT_FOUND);
        }

        for (TaskEvalCreateDTO taskEvalCreateDTO : taskEvalCreateDTOS) {
            Long evalListSeq = taskEvalCreateDTO.getEvalListSeq();

            // evalListSeq에 대한 데이터가 존재하는지 확인
            EvalList evalList = evalListRepository.findById(evalListSeq)
                    .orElseThrow(() -> new CustomException(ErrorCodeType.DATA_NOT_FOUND));

            // 제출된 과제에서 해당 평가 항목에 대한 평가가 이미 존재하는지 확인
            if (taskEvalRepository.existsByTaskSubmitSeqAndEvalListSeq(taskSubmitSeq, evalListSeq)) {
                throw new CustomException(ErrorCodeType.DUPLICATE_DATA);
            }

            int taskScore = taskEvalCreateDTO.getTaskScore();
            int maxTaskScore = evalList.getEvalListScore();

            // 점수가 유효한 값인지 확인
            if (taskScore < 0 || taskScore > maxTaskScore) {
                throw new CustomException(ErrorCodeType.INVALID_VALUE);
            }

            TaskEval taskEval = TaskEval.builder()
                    .evalListSeq(evalListSeq)
                    .taskSubmitSeq(taskSubmitSeq)
                    .taskScore(taskScore)
                    .build();

            taskEvalRepository.save(taskEval);
        }
    }
}