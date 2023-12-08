package com.spring.mvc.chap05.common;

import lombok.*;

@Getter
@Setter
@ToString
public class Page {

    private int pageNo; // 클라이언트가 요청한 페이지 번호
    private int amount; // 클라이언트가 요청한 보여질 게시물 수

    public Page() {
        this.pageNo = 1;
        this.amount = 6;

    }
    /*
        만약에 한 페이지에 게시물을 10개씩 뿌린다고 가정하면
        1페이지 -> LIMIT 0, 10
        2페이지 -> LIMIT 10, 10
        3페이지 -> LIMIT 20, 10

        만약에 한 페이지에 게시물을 6개씩 뿌린다고 가정하면
        1페이지 -> LIMIT 0, 6
        2페이지 -> LIMIT 6, 6
        3페이지 -> LIMIT 12, 6

        만약에 한 페이지에 게시물을 n개씩 뿌린다고 가정하면
        1페이지 -> LIMIT 0, n
        2페이지 -> LIMIT 10, n
        3페이지 -> LIMIT 20, n

        N페이지 -> (N-1)*N
    */

    public int getPageStart() {
        return (pageNo - 1) * amount;
    }
}
