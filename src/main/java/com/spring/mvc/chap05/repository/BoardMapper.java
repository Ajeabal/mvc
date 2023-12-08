package com.spring.mvc.chap05.repository;

import com.spring.mvc.chap05.entity.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

    List<Board> findAll();

    boolean save(Board board);

    boolean delete(int bno);

    Board findOne(int bno);

    default void updateViewCount(int bno) {}
}
