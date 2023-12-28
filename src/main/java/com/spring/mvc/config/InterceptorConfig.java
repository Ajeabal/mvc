package com.spring.mvc.config;

// 만든 인터셉터들을 스프링 컨텍스트에 등록하는 설정 파일

import com.spring.mvc.intercepter.AfterLoginInterceptor;
import com.spring.mvc.intercepter.AutoLoginInterceptor;
import com.spring.mvc.intercepter.BoardInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {
    private final AfterLoginInterceptor afterLoginInterceptor;
    private final BoardInterceptor boardInterceptor;
    private final AutoLoginInterceptor autoLoginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 로그인 이후 비회원 접근 차단 인터셉터
        registry.addInterceptor(afterLoginInterceptor)// 어떤 인터셉터를 등록할지
                .addPathPatterns("/members/sign-in", "/members/sign-up");//어떤 요청을 통해 인터셉터를 사용할지

        // 게시판 인터셉터
        registry.addInterceptor(boardInterceptor)
                .addPathPatterns("/board/*")
                .excludePathPatterns("/board/list", "/board/detail");

        // 자동로그인 인터셉터
        registry.addInterceptor(autoLoginInterceptor)
                .addPathPatterns("/**");
    }
}
