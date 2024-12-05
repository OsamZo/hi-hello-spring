package spring.hi_hello_spring.onboarding.query.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class TemplateAllQueryDTO {

    private Long templateSeq;
    private String templateTitle;
    private int templateProcedure;
}
