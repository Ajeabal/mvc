package com.spring.mvc.intercepter;

import com.spring.mvc.chap05.repository.BoardMapper;
import com.spring.mvc.util.LoginUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.spring.mvc.util.LoginUtils.*;

/*
    - 인터셉터: 컨트롤러의 요청이 들아가기 전, 후로
        공통적으로 처리할 코드 또는 검사할 것들을 정의하는 클래스
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class BoardInterceptor implements HandlerInterceptor {

    private final BoardMapper boardMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 로그인 X -> 글쓰기, 수정, 삭제 요청을 막음
        HttpSession session = request.getSession();
        if (!isLogin(session)) {
            log.info("This request({}) is denied", request.getRequestURI());
            response.sendRedirect("/members/sign-in");
            return false;
        }

        // 삭제 요청이 들어올 때 서버에서 작성자와 관리자 확인
        // 삭제 요청 확인
        String requestURI = request.getRequestURI();
        if (requestURI.contains("delete")) {
            // 글번호 ? => 쿼리스트링
            String parameter = request.getParameter("bno");
            // 로그인한 계정명과 게시물의 계정명의 일치를 체크
            // 로그인한 계정명은 세션에서
            // 게시물 계정명은 DB에서

            String targetAccount = boardMapper.findOne(Integer.parseInt(parameter)).getAccount();
            // 관리자 =>삭제 통과
            if (isAdmin(session)) return true;
            // 만약에 내가 쓴 글이 아니면 => 접근권한이 없다는 안내페이지로 이동
            if (!isMine(session, targetAccount)) {
                response.sendRedirect("/access-deny");
                return false;
            }
        }
        return true;
    }
}
