package com.spring.mvc.intercepter;

import com.spring.mvc.util.LoginUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.spring.mvc.util.LoginUtils.*;

@Slf4j
@Configuration
public class AfterLoginInterceptor implements HandlerInterceptor {
    // 로그인 한 이후 비회원만 접근 할 수 있는 페이지 접근 차단.
    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws  Exception {
        log.info("After Login Interceptor Execute.");
        HttpSession session = request.getSession();

        if (isLogin(session)){
            response.sendRedirect("/");
            return false;
        }
        return true;
    }

}
