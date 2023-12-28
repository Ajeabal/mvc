package com.spring.mvc.chap05.controller;

import com.spring.mvc.chap05.common.PageMaker;
import com.spring.mvc.chap05.common.Search;
import com.spring.mvc.chap05.dto.response.BoardResponseDTO;
import com.spring.mvc.chap05.dto.request.BoardWriteRequestDTO;
import com.spring.mvc.chap05.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
@Slf4j
public class BoardController {
    private final BoardService boardService;

    // 1. 목록 조회 요청 (/board/list : GET)
    @GetMapping("/list")
    public String list(@ModelAttribute("s") Search page, Model model) {
        log.info("/board/list : GET!");
        List<BoardResponseDTO> dtoList = boardService.getList(page);
        PageMaker maker = new PageMaker(page, boardService.getTotalCount(page));
        log.debug("{}", page);
        model.addAttribute("bList", dtoList);
        model.addAttribute("maker", maker);
//        model.addAttribute("s", page);
        return "chap05/list";
    }


    // 2. 글쓰기 화면요청 (/board/write : GET)
    @GetMapping("/write")
    public String write() {
        log.info("/board/write : GET!");
        return "chap05/write";
    }


    // 3. 글쓰기 등록요청 (/board/write : POST)
    @PostMapping("/write")
    public String write(BoardWriteRequestDTO dto, HttpSession session) {
        log.info("/board/write : POST! - " + dto);
        boardService.register(session,dto);
        return "redirect:/board/list";
    }


    // 4. 글 삭제 요청 (/board/delete : GET)
    @GetMapping("/delete")
    public String delete(@RequestParam("bno") int boardNo) {
        log.info("/board/delete : GET");
        boardService.delete(boardNo);
        return "redirect:/board/list";
    }

    // 5. 글 상세보기 요청 (/board/detail : GET)
    @GetMapping("/detail")
    public String detail(int bno, @ModelAttribute("s") Search search, Model model) {
        log.info("/board/detail : GET");
        model.addAttribute("b", boardService.getDetail(bno));
        return "chap05/detail";
    }
}
