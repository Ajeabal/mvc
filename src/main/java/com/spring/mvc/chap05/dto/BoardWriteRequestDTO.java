package com.spring.mvc.chap05.dto;

import lombok.*;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BoardWriteRequestDTO {
    private String title; // 제목
    private String content; // 내용
}