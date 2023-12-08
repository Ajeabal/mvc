package com.spring.mvc.chap05.service;

import com.spring.mvc.chap05.dto.BoardResponseDTO;
import com.spring.mvc.chap05.dto.BoardWriteRequestDTO;
import com.spring.mvc.chap05.entity.Board;
import com.spring.mvc.chap05.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
//@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository repository;

    public BoardService(BoardRepository repository) {
        this.repository = repository;
    }

    public List<BoardResponseDTO> getList() {
        return repository.findAll()
                .stream()
                .map(BoardResponseDTO::new)
                .collect(Collectors.toList());
    }

    public void insertBoard(BoardWriteRequestDTO board) {
        repository.save(new Board(board));
    }

    public void deleteScore(int bno) {
        repository.delete(bno);
    }

    public Board  retrieve(int bno) {
        repository.updateViewCount(bno);
        return repository.findOne(bno);
    }
}
