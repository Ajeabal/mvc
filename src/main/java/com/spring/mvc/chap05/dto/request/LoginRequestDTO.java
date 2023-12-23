package com.spring.mvc.chap05.dto.request;

import lombok.*;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {

    private String account;
    private String password;
    private boolean autoLogin; // 자동로그인 체크 여부
}
