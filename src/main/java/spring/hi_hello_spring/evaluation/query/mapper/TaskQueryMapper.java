package spring.hi_hello_spring.evaluation.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import spring.hi_hello_spring.evaluation.query.dto.GroupTaskAllQueryDTO;
import spring.hi_hello_spring.evaluation.query.dto.TaskAllListQueryDTO;

import java.util.List;

@Mapper
public interface TaskQueryMapper {

    List<TaskAllListQueryDTO> findHrAllTask();

    List<TaskAllListQueryDTO> findMentorAllTask(Long employeeSeq);

    List<GroupTaskAllQueryDTO> findGroupTaskTitle();
}
