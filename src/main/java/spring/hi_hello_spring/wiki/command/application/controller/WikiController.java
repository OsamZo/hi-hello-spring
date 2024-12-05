package spring.hi_hello_spring.wiki.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;
import spring.hi_hello_spring.wiki.command.application.dto.WikiCreateRequestDTO;
import spring.hi_hello_spring.wiki.command.application.dto.WikiUpdateRequestDTO;
import spring.hi_hello_spring.wiki.command.application.service.WikiService;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/wiki")
@RequiredArgsConstructor
@Tag(name = "Wiki API", description = "위키 API")
public class WikiController {

    private final WikiService wikiService;

    @PostMapping
    @Operation(summary = "위키 등록", description = "위키 등록 로직입니다.")
    public ApiResponse<?> createWiki(
            @RequestBody WikiCreateRequestDTO wikiCreateRequestDTO
    )
    {
        wikiService.createWiki(wikiCreateRequestDTO);
        return ResponseUtil.successResponse("위키가 성공적으로 등록되었습니다.").getBody();
    }

    @PutMapping("/{wikiSeq}")
    @Operation(summary = "위키 수정", description = "위키 수정 로직입니다.")
    public ApiResponse<?> updateWiki(
            @PathVariable Long wikiSeq,
            @RequestParam Long employeeSeq,
            @RequestBody WikiUpdateRequestDTO wikiUpdateRequestDTO
    ) throws IOException {
        wikiService.updateWiki(wikiSeq, employeeSeq, wikiUpdateRequestDTO);
        return ResponseUtil.successResponse("위키가 성공적으로 수정되었습니다.").getBody();
    }
}