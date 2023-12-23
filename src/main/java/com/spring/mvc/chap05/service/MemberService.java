package com.spring.mvc.chap05.service;

import com.spring.mvc.chap05.dto.request.LoginRequestDTO;
import com.spring.mvc.chap05.dto.request.SignUpRequestDTO;
import com.spring.mvc.chap05.entity.Member;
import com.spring.mvc.chap05.repository.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.spring.mvc.chap05.service.LoginResult.*;

@Slf4j
@Service
@RequiredArgsConstructor // AllArg안하는 이유는 Required는 final만 해주고 Allarg는 전부 다여서
public class MemberService {

    private final MemberMapper memberMapper;
    private final PasswordEncoder encoder;

    // 회원가입 처리
    public boolean join(SignUpRequestDTO dto) {
        // 클라이언트가 보낸 회원가입 데이터를
        // 패스워드 인코딩하여 엔터티로 변환 전달해야한다.
        return memberMapper.save(dto.toEntity(encoder));
    }

    // 로그인 처리
    public LoginResult authenticate(LoginRequestDTO dto) {
        Member foundMember = memberMapper.findMember(dto.getAccount());

        if (foundMember == null) { // 회원강비을 하지 않은 상태
            log.info("회원가입을 해야합니다.");
            return NO_ACC;
        }

        // 비밀번호 일치 검사
        String inputPassword = dto.getPassword(); // 사용자가 입력한 패스워드
        String dbPassword = foundMember.getPassword(); // 실제 패스워드 (암호화 되어 있음)
        if (!encoder.matches(inputPassword, dbPassword)) {
            log.info("비밀번호가 일치하지 않음");
            return NO_PW;
        }
        log.info("{}님 로그인 완료", foundMember.getAccount());
        return SUCCESS;
    }

    // 아이디, 이메일 중복검사 서비스
    public boolean checkDuplicateValue(String type, String keyword) {
        return memberMapper.isDuplicate(type, keyword);
    }
}
