package spring.hi_hello_spring.wiki.command.application.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WikiUpdateRequestDTO {

    private String wikiModContent;
}
