package com.spring.mvc.chap05.dto.request;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class AutoLoginDTO {

    private String sessionId;
    private LocalDateTime limitTime;
    private String account;
}
