package com.spring.mvc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 로컬에 저장된 이미지를 웹브라우저에서 불러올 수 있도록 URL을 만드는 설정
@Configuration
public class LocalResourceConfig implements WebMvcConfigurer {
    @Value("${file.upload.root-path}")
    private String rootPath;

    /*
        하드 디스크에 저장된 rootPath 아래의 파일은
        http URL에서 /local/파일 경로명으로 접근 가능하게 한다.
     */

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/local/**")
                .addResourceLocations("file:/" + rootPath);
    }
}
