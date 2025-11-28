package com.ys.admin_portal.config;

import com.ys.admin_portal.repository.BannerRepository;
import com.ys.admin_portal.repository.CompanyInterviewRepository;
import com.ys.admin_portal.repository.NoticeRepository;
import com.ys.admin_portal.repository.ReviewRepository;
import com.ys.admin_portal.service.BannerService;
import com.ys.admin_portal.service.CompanyInterviewService;
import com.ys.admin_portal.service.NoticeService;
import com.ys.admin_portal.service.ReviewService;
import com.ys.admin_portal.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // 이 클래스는 설정 파일 , Spring이 시작할 때 읽음
@RequiredArgsConstructor
public class SpringConfig {

    private final BannerRepository bannerRepository;
    private final ReviewRepository reviewRepository;
    private final CompanyInterviewRepository companyInterviewRepository;
    private final NoticeRepository noticeRepository;

    @Bean
    public FileUtil fileUtil() {
        return new FileUtil();
    }

    @Bean // bannerService라는 이름의 빈 생성, Spring 컨테이너에 등록됨
    public BannerService bannerService() {
        return new BannerService(bannerRepository, fileUtil());
    }

    @Bean
    public ReviewService reviewService() {
        return new ReviewService(reviewRepository);
    }

    @Bean
    public CompanyInterviewService companyInterviewService() {
        return new CompanyInterviewService(companyInterviewRepository, fileUtil());
    }

    @Bean
    public NoticeService noticeService() {
        return new NoticeService(noticeRepository);
    }



}
