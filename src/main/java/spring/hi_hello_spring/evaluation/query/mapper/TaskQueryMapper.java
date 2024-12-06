package spring.hi_hello_spring.evaluation.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import spring.hi_hello_spring.evaluation.query.dto.TaskMenteeDetailQueryDTO;
import spring.hi_hello_spring.evaluation.query.dto.TaskMentorDetailQueryDTO;
import spring.hi_hello_spring.evaluation.query.dto.TaskAllListQueryDTO;

import java.util.List;
import java.util.Map;

@Mapper
public interface TaskQueryMapper {

    List<TaskAllListQueryDTO> findHrAllTask();
    List<TaskAllListQueryDTO> findMentorAllTask(Long employeeSeq);
    List<TaskMentorDetailQueryDTO> findMentorTaskDetail(Map<String, Object> params);
    List<TaskMenteeDetailQueryDTO> findMenteeTaskDetail(Map<String, Object> params);
}
