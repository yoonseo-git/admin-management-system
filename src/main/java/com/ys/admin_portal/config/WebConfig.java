package com.ys.admin_portal.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer { // WebMvcConfigurer 인터페이스 - Spring MVC 설정을 커스터마이징하는 인터페이스, 정적 리소스, 인터셉터 등 설정 가능

    @Value("${file.upload.path}")
    private String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) { // 정적 리소스(이미지, CSS, JS 등) 경로 설정
        // /uploads/** 요청을 실제 폴더와 연결
        registry.addResourceHandler("/uploads/**") // <- 이 패턴의 요청이 오면
                .addResourceLocations("file:" + uploadPath); // file: 프로토콜 - 파일 시스템 경로, 여기서 파일을 찾아라
    }
}
