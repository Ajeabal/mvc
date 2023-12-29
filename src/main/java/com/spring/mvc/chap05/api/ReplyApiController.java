package com.spring.mvc.chap05.api;

import com.spring.mvc.chap05.common.Page;
import com.spring.mvc.chap05.dto.request.ReplyModifyRequestDTO;
import com.spring.mvc.chap05.dto.request.ReplyPostRequestDTO;
import com.spring.mvc.chap05.dto.response.ReplyDetailResponseDTO;
import com.spring.mvc.chap05.dto.response.ReplyListResponseDTO;
import com.spring.mvc.chap05.service.ReplyService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

/*
    REST API URL 설계 원칙
     - CRUD는 URL에 명시하는게 아니라 HTTP method로만 표현해야 한다.
     => /replies/write  (X)
     => /replies : POST (O)

     전체조회
     => /replies/all    (X)
     => /replies : GET  (O)

     단일 조회
     => /replies/17 : GET

     => /replies/delete?replyNo=3 (X)
     => /replies/3 : DELETE (O)
 */


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/replies")
public class ReplyApiController {

    private final ReplyService replyService;

    // 댓글 목록 조회 요청
    // URL : /api/v1/replies/글 번호/page/페이지 번호
    @GetMapping("/{boardNo}/page/{pageNo}")
    public ResponseEntity<?> list(@PathVariable long boardNo, @PathVariable int pageNo) {
        Page page = new Page();
        page.setPageNo(pageNo);
        page.setAmount(5);
        ReplyListResponseDTO replies = replyService.getList(boardNo, page);
        return ResponseEntity.ok().body(replies);
    }

    // 댓글 등록 요청 처리
    @PostMapping
    public ResponseEntity<?> create(@Validated @RequestBody ReplyPostRequestDTO dto,
                                    BindingResult result,
                                    HttpSession session) {
        if (result.hasErrors()) {
            return ResponseEntity
                    .badRequest()
                    .body(result.toString());
        }

        log.debug("{}", dto);
        try {
            ReplyListResponseDTO responseDTO = replyService.register(dto, session);
            return ResponseEntity.ok().body(responseDTO);
        } catch (SQLException e) {
            log.warn("500 status code response cause by: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    // 댓글 삭제 요청 처리
    @DeleteMapping("/{replyNo}")
    public ResponseEntity<?> remove(@PathVariable Long replyNo) {
        if (replyNo == null) {
            return ResponseEntity
                    .badRequest()
                    .body("댓글 번호를 보내주세요.");
        }

        log.info("/api/v1/replies/{} : DELETE", replyNo);

        try {
            ReplyListResponseDTO responseDTO = replyService.delete(replyNo);
            return ResponseEntity
                    .ok()
                    .body(responseDTO);
        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body(e.getMessage());
        }
    }

    // 댓글 수정 요청 처리
    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH})
    public ResponseEntity<?> update(@Validated @RequestBody ReplyModifyRequestDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity
                    .badRequest()
                    .body(result.toString());
        }
        log.info("/api/v1/replies PUT/PATCH");
        log.debug("Parameter: {}", dto);

        try {
            ReplyListResponseDTO responseDTO = replyService.modify(dto);
            return ResponseEntity.ok().body(responseDTO);

        } catch (Exception e) {
            log.warn("Internal Server Error. Caused by: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
