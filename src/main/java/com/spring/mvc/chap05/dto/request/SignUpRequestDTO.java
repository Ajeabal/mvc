package com.spring.mvc.chap05.dto.request;

import com.spring.mvc.chap05.entity.Member;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDTO {

    @NotBlank
    @Size(min = 4, max = 14)
    private String account;

    @NotBlank
    private String password;

    @NotBlank
    @Size(min = 2, max = 6)
    private String name;

    @NotBlank
    @Email
    private String email;

    // 프로필 사진 파일
    private MultipartFile profileImage;

    private Member.LoginMethod loginMethod;

    // Entity 변환 유틸 메서드
    public Member toEntity(PasswordEncoder encoder, String savePath) {
        return Member.builder()
                .account(account)
                .password(encoder.encode(password))
                .name(name)
                .email(email)
                .profileImage(savePath)
                .loginMethod(loginMethod)
                .build();
    }
}
