package spring.hi_hello_spring.evaluation.command.application.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.hi_hello_spring.common.exception.CustomException;
import spring.hi_hello_spring.common.exception.ErrorCodeType;
import spring.hi_hello_spring.evaluation.command.application.dto.EvalListCreateDTO;
import spring.hi_hello_spring.evaluation.command.domain.aggregate.entity.EvalInd;
import spring.hi_hello_spring.evaluation.command.domain.aggregate.entity.EvalList;
import spring.hi_hello_spring.evaluation.command.domain.repository.EvalIndRepository;
import spring.hi_hello_spring.evaluation.command.domain.repository.EvalListRepository;
import spring.hi_hello_spring.evaluation.command.domain.repository.TaskRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EvalListService {

    private final EvalListRepository evalListRepository;
    private final EvalIndRepository evalIndRepository;
    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public void createEvalList(Long evalIndSeq, Long taskSeq, EvalListCreateDTO evalListCreateDTO) {
        if (!taskRepository.existsById(taskSeq)) {
            throw new CustomException(ErrorCodeType.DATA_NOT_FOUND);
        }

        EvalInd evalInd = evalIndRepository.findById(evalIndSeq)
                .orElseThrow(() -> new CustomException(ErrorCodeType.DATA_NOT_FOUND));

        List<EvalList> evalLists = evalListRepository.findByEvalIndSeq(evalIndSeq);

        int leftEvalIndScore = evalInd.getEvalIndScore();  // ((evalInd 배점) - (해당 evalInd에 대해 현재까지 존재하는 evalList의 합))
        for (EvalList evalList : evalLists) {
            leftEvalIndScore -= evalList.getEvalListScore();
        }

        if (evalListCreateDTO.getEvalListScore() <= leftEvalIndScore) {
            EvalList evalList = modelMapper.map(evalListCreateDTO, EvalList.class);
            evalList.updateEvalIndSeq(evalIndSeq);
            evalList.updateTaskSeq(taskSeq);
            evalListRepository.save(evalList);
        } else {
            throw new CustomException(ErrorCodeType.INVALID_VALUE);
        }
    }

    @Transactional
    public void deleteEvalList(Long evalListSeq) {
        if(evalListRepository.existsById(evalListSeq)) {
            evalListRepository.deleteById(evalListSeq);
        }else{
            throw new CustomException(ErrorCodeType.DATA_NOT_FOUND);
        }
    }
}