package spring.hi_hello_spring.notify.query.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReadNotifyDTO {

    private Long notiSeq;
    private Long employeeSeq;   // 수신자
    private String notiContent;
    private String notiUrl;
    private boolean alarmReadStatus;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regDate;
}
