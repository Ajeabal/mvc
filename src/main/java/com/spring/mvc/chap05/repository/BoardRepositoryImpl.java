package com.spring.mvc.chap05.repository;

import com.spring.mvc.chap04.entity.Grade;
import com.spring.mvc.chap04.entity.Score;
import com.spring.mvc.chap05.dto.BoardWriteRequestDTO;
import com.spring.mvc.chap05.entity.Board;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

//@Repository
public class BoardRepositoryImpl implements BoardRepository {

    private static final Map<Integer, Board> boardMap;

    // 게시글 순서
    private static int bN;



    static {
        boardMap = new HashMap<>();
        Board b1 = new Board(++bN,"testTitle1","testContent1");
        Board b2 = new Board(++bN,"testTitle2","testContent2");
        Board b3 = new Board(++bN,"testTitle3","testContent3");

        boardMap.put(b1.getBoardNo(), b1);
        boardMap.put(b2.getBoardNo(), b2);
        boardMap.put(b3.getBoardNo(), b3);

    }

    @Override
    public List<Board> findAll() {

        return boardMap.values()
                .stream()
                .sorted(Comparator.comparing(Board::getBoardNo).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public boolean save(Board board) {
        board.setBoardNo(++bN);
        boardMap.put(board.getBoardNo(), board);
        if (boardMap.containsKey(board.getBoardNo())) {
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(int bno) {
        if (!boardMap.containsKey(bno)) {
            return false;
        }
        boardMap.remove(bno);
        return true;
    }

    @Override
    public Board findOne(int bno) {
        Board board = boardMap.get(bno);
        return board;
    }
}
