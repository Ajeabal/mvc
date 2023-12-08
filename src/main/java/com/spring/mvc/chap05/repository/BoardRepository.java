package com.spring.mvc.chap05.repository;

import com.spring.mvc.chap04.entity.Score;
import com.spring.mvc.chap05.dto.BoardWriteRequestDTO;
import com.spring.mvc.chap05.entity.Board;

import java.util.List;


public interface BoardRepository {

    // 목록 조회
    List<Board> findAll();

    // 상세 조회
    Board findOne(int boardNo);

    // 게시물 등록
    boolean save(Board board);

    // 게시물 삭제
    boolean deleteByNo(int boardNo);

    // 조회수 상승
    default void updateViewCount(int boardNo) {}

}