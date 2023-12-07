package com.spring.mvc.chap05.service;

import com.spring.mvc.chap04.dto.ScoreRequestDTO;
import com.spring.mvc.chap04.dto.ScoreResponseDTO;
import com.spring.mvc.chap04.entity.Score;
import com.spring.mvc.chap04.repository.ScoreRepository;
import com.spring.mvc.chap05.dto.BoardResponseDTO;
import com.spring.mvc.chap05.dto.BoardWriteRequestDTO;
import com.spring.mvc.chap05.entity.Board;
import com.spring.mvc.chap05.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository repository;

    public List<BoardResponseDTO> getList() {
        return repository.findAll()
                .stream()
                .map(BoardResponseDTO::new)
                .collect(Collectors.toList());
    }

    public void insertBoard(BoardWriteRequestDTO board) {
        repository.save(new Board(board));
    }

    public boolean deleteScore(int bno) {
        repository.delete(bno);
        return true;
    }

    public Board  retrieve(int bno) {
        return repository.findOne(bno);
    }
}
