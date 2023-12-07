package com.spring.mvc.chap05.controller;

import com.spring.mvc.chap04.dto.ScoreResponseDTO;
import com.spring.mvc.chap04.entity.Score;
import com.spring.mvc.chap04.service.ScoreService;
import com.spring.mvc.chap05.dto.BoardDetailResponseDTO;
import com.spring.mvc.chap05.dto.BoardResponseDTO;
import com.spring.mvc.chap05.dto.BoardWriteRequestDTO;
import com.spring.mvc.chap05.entity.Board;
import com.spring.mvc.chap05.repository.BoardRepository;
import com.spring.mvc.chap05.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService service;

    // 1. 목록 조회 요청(/board/list : GET)
    @GetMapping("/list")
    public String list(Model model) {
        System.out.println("/board/list GET");

        List<BoardResponseDTO> boardList = service.getList();
        model.addAttribute("bList", boardList);
        return "chap05/list";
    }
    // 2. 글쓰기 화면 요청(/board/write : GET)
    @GetMapping("/write")
    public String write() {
        System.out.println("/board/write GET");
        return "chap05/write";
    }
    // 3. 글쓰기 등록 요청(/board/write: POST)
    @PostMapping("/write")
    public String write(BoardWriteRequestDTO borad) {
        service.insertBoard(borad);
        return "redirect:/board/list";
    }
    // 4. 글 삭제 요청(/board/delete: GET)
    @GetMapping("/delete")
    public String delete(int bno) {
        service.deleteScore(bno);
        return "redirect:/board/list";
    }
    // 5. 글 상세 보기 요청 (/board/detail : GET)
    @GetMapping("/detail")
    public String detail(int bno, Model model) {
        Board board = service.retrieve(bno);
        BoardDetailResponseDTO boardDetailResponseDTO = new BoardDetailResponseDTO(board);
        model.addAttribute("b", boardDetailResponseDTO);
        return "chap05/detail";
    }
}
