package com.spring.mvc.chap05.repository;

import com.spring.mvc.chap04.entity.Score;
import com.spring.mvc.chap05.dto.BoardWriteRequestDTO;
import com.spring.mvc.chap05.entity.Board;

import java.util.List;


public interface BoardRepository {

    // 게시글 정보 전체 목록 조회
    List<Board> findAll();

    boolean save(Board board);


    boolean delete(int bno);

    Board findOne(int bno);

    default void updateViewCount(int bno) {}
}
