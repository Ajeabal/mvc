package com.spring.mvc.chap05.dto;

import com.spring.mvc.chap04.entity.Grade;
import com.spring.mvc.chap04.entity.Score;
import com.spring.mvc.chap05.entity.Board;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter @ToString @EqualsAndHashCode
@RequiredArgsConstructor
public class BoardResponseDTO {

    private final int boardNo; // 게시글 번호
    private final String shortTitle; // 제목
    private final String shortContent; // 내용
    private final String date; // 작성일자시간
    private final int viewCount; // 조회수


    public BoardResponseDTO(Board board) {
        this.boardNo = board.getBoardNo();
        this.shortTitle = makeShortTitle(board.getTitle());
        this.shortContent = makeShortContent(board.getContent());
        this.date = makePrettierDateString(board.getRegDateTime());
        this.viewCount = board.getViewCount();
    }

    static String makePrettierDateString(LocalDateTime regDateTime) {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return pattern.format(regDateTime);
    }

    private String makeShortContent(String originContent) {
        return sliceString(originContent, 30);
    }


    private String makeShortTitle(String originTitle) {
        return sliceString(originTitle, 7);
    }

    /**
     * @param targetString : 줄이고 싶은 원본 문자열
     * @param wishLength   : 짜르고 싶은 글자 수
     * @return : wishLength보다 targetString이 길면
     * wishLength만큼 짤라서 뒤에 ... 붙여서 리턴
     */
    private static String sliceString(String targetString, int wishLength) {
        return (targetString.length() > wishLength)
                ? targetString.substring(0, wishLength) + "..."
                : targetString
                ;
    }
}
