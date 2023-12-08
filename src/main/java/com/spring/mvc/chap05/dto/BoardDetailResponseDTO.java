package com.spring.mvc.chap05.dto;

import com.spring.mvc.chap05.entity.Board;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter @ToString @EqualsAndHashCode
@RequiredArgsConstructor
public class BoardDetailResponseDTO {

    private final int boardNo; // 게시글 번호
    private final String title; // 제목
    private final String content; // 내용
    private final String date; // 작성일자시간
    private final int viewCount; // 조회수


    public BoardDetailResponseDTO(Board board) {
        this.boardNo = board.getBoardNo();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.date = makePrettierDateString(board.getRegDateTime());
        this.viewCount = board.getViewCount();
    }

    static String makePrettierDateString(LocalDateTime regDateTime) {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return pattern.format(regDateTime);
    }
}