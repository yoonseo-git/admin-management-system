package com.ys.admin_portal.config;

import com.ys.admin_portal.repository.BannerRepository;
import com.ys.admin_portal.repository.ReviewRepository;
import com.ys.admin_portal.service.BannerService;
import com.ys.admin_portal.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // 이 클래스는 설정 파일 , Spring이 시작할 때 읽음
@RequiredArgsConstructor
public class SpringConfig {

    private final BannerRepository bannerRepository;
    private final ReviewRepository reviewRepository;

    @Bean // bannerService라는 이름의 빈 생성, Spring 컨테이너에 등록됨
    public BannerService bannerService() {
        return new BannerService(bannerRepository);
    }

    @Bean
    public ReviewService reviewService() {
        return new ReviewService(reviewRepository);
    }

}
