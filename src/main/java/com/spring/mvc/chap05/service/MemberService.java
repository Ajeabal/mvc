package com.spring.mvc.chap05.service;

import com.spring.mvc.chap05.dto.request.AutoLoginDTO;
import com.spring.mvc.chap05.dto.request.LoginRequestDTO;
import com.spring.mvc.chap05.dto.request.SignUpRequestDTO;
import com.spring.mvc.chap05.dto.response.LoginUserResponseDTO;
import com.spring.mvc.chap05.entity.Member;
import com.spring.mvc.chap05.repository.MemberMapper;
import com.spring.mvc.util.LoginUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.time.LocalDateTime;

import static com.spring.mvc.chap05.service.LoginResult.*;
import static com.spring.mvc.util.LoginUtils.AUTO_LOGIN_COOKIE;
import static com.spring.mvc.util.LoginUtils.getCurrentLoginMemberAccount;

@Slf4j
@Service
@RequiredArgsConstructor // AllArg안하는 이유는 Required는 final만 해주고 Allarg는 전부 다여서
public class MemberService {

    private final MemberMapper memberMapper;
    private final PasswordEncoder encoder;

    // 회원가입 처리
    public boolean join(SignUpRequestDTO dto, String savePath) {
        // 클라이언트가 보낸 회원가입 데이터를
        // 패스워드 인코딩하여 엔터티로 변환 전달해야한다.
        return memberMapper.save(dto.toEntity(encoder, savePath));
    }

    // 로그인 처리
    public LoginResult authenticate(
            LoginRequestDTO dto,
            HttpSession session,
            HttpServletResponse response
            ) {
        Member foundMember = getMember(dto.getAccount());

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
        // 자동 로그인 처리
        if (dto.isAutoLogin()) {
            // 1. 자동 로그인 쿠키 생성 - 쿠키 안에 절대 중복되지 않는 값을 저장.(브라우저 세션 아이디)
            Cookie autoLoginCookie = new Cookie(AUTO_LOGIN_COOKIE, session.getId());
            // 2. 쿠키 설정 - 사용경로, 수명
            int limitTime = 7776000; // 자동 로그인 유지 시간
            autoLoginCookie.setPath("/");
            autoLoginCookie.setMaxAge(limitTime);

            // 3. 쿠키를 클라이언트에 전송
            response.addCookie(autoLoginCookie);

            // 4. DB에도 쿠키에 관련된 값들(랜덤 세션키, 만료시간)을 갱신
            memberMapper.saveAutoLogin(
                    AutoLoginDTO.builder()
                            .sessionId(session.getId())
                            .limitTime(LocalDateTime.now().plusDays(90))
                            .account(dto.getAccount())
                            .build()
            );

        }

        log.info("{}님 로그인 완료", foundMember.getAccount());
        return SUCCESS;
    }

    private Member getMember(String account) {
        return memberMapper.findMember(account);
    }

    // 아이디, 이메일 중복검사 서비스
    public boolean checkDuplicateValue(String type, String keyword) {
        return memberMapper.isDuplicate(type, keyword);
    }

    // 세션을 이용해 일반 로그인 유지하기
    public void maintainLoginState(HttpSession session, String accout) {
        // 세션은 서버에서만 유일하게 보관되는 데이터로서
        // 로그인 유지 등 상태유지가 필요할 때 사용되는 개념이다.
        // 세션은 쿠키와 달리 모든 데이터를 저장할 수 있다.
        // 세션의 수명은 설정한 수명시간에 영향을 받고 브라우저의 수명과 함께한다.

        // 현재 로그인한 사람의 모든 정보 조회
        Member member = getMember(accout);

        LoginUserResponseDTO dto = LoginUserResponseDTO.builder()
                .account(member.getAccount())
                .email(member.getEmail())
                .nickName(member.getName())
                .auth(member.getAuth().name())
                .profile(member.getProfileImage())
                .build();
        log.debug("login: {}", dto);
        // 조회한 정보를 세션에 저장
        session.setAttribute(LoginUtils.LOGIN_KEY, dto);

        // 세션도 수명을 설정해야 한다.
        session.setMaxInactiveInterval(60 * 60); // 1시간이 지나면 로그인이 풀림.
    }

    public void autoLoginClear(HttpServletRequest request, HttpServletResponse response) {
        // 1. 자동로그인 쿠키를 가져온다
        Cookie c = WebUtils.getCookie(request, AUTO_LOGIN_COOKIE);
        // 2. 쿠키삭제 방법은 수명을 0으로 설정하여 클라이언트에게 재전송
        if (c != null) {
            c.setMaxAge(0);
            c.setPath("/");
            response.addCookie(c);
            // 3.DB에서도 세션아이디와 만료시간을 제거한다.
            memberMapper.saveAutoLogin(
                    AutoLoginDTO.builder()
                            .sessionId("none")
                            .limitTime(LocalDateTime.now())
                            .account(getCurrentLoginMemberAccount(request.getSession()))
                            .build()
            );
        }
    }
}
