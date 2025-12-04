package com.ys.admin_portal.service;

import com.ys.admin_portal.domain.Banner;
import com.ys.admin_portal.domain.Course;
import com.ys.admin_portal.repository.BannerRepository;
import com.ys.admin_portal.util.FileUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class BannerServiceTest {

    @Mock
    private BannerRepository bannerRepository;

    @Mock
    private FileUtil fileUtil;

    @InjectMocks
    private BannerService bannerService;

    private Banner testBanner;

    @BeforeEach
    void setUp() {
        // 테스트용 배너 생성
        testBanner = Banner.builder()
                .id(1L)
                .course(Course.IT)
                .link("https://test.com")
                .pcImageUrl("/images/pc.jpg")
                .mobileImageUrl("/images/mobile.jpg")
                .createdBy("test")
                .isDeploy(true)
                .isDeleted(false)
                .build();
    }

    @Test
    @DisplayName("전체 배너 조회 성공")
    void findAll_Success() {
        // given
        List<Banner> banners = Arrays.asList(testBanner);
        given(bannerRepository.findAllActive()).willReturn(banners);

        // when
        List<Banner> result = bannerService.findAll();

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCourse()).isEqualTo(Course.IT);
        verify(bannerRepository, times(1)).findAllActive();
    }

    @Test
    @DisplayName("단일 배너 조회 성공")
    void findById_Success() {
        // given
        given(bannerRepository.findById(1L)).willReturn(Optional.of(testBanner));

        // when
        Banner result = bannerService.findById(1L);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getCourse()).isEqualTo(Course.IT);
        verify(bannerRepository, times(1)).findById(1L);
    }




}
