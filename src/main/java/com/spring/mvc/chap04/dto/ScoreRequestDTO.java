package com.spring.mvc.chap04.dto;

import lombok.*;

@Setter @Getter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ScoreRequestDTO {
    private String name;
    private int kor, eng, math;
}
