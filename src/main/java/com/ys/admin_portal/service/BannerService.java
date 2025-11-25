package com.ys.admin_portal.service;

import com.ys.admin_portal.domain.Banner;
import com.ys.admin_portal.domain.Course;
import com.ys.admin_portal.repository.BannerRepository;
import com.ys.admin_portal.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true) // 모든 메서드가 기본적으로 readOnly = true -> 조회전용 최적화
public class BannerService {

    private final BannerRepository bannerRepository;
    private final FileUtil fileUtil;

    // 전체 조회
    public List<Banner> findAll() {
        return bannerRepository.findAllActive();
    }

    // 과정별 조회
    public List<Banner> findByCourse(Course course) {
        return bannerRepository.findByCourse(course);
    }

    // 단건 조회
    public Banner findById(Long id) {
        return bannerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("배너를 찾을 수 없습니다. id: " + id));
    }

    // 등록
    @Transactional
    public Long register(Banner banner, MultipartFile pcImage, MultipartFile mobileImage, MultipartFile video) throws IOException {

        // 파일 저장
        banner.setPcImageUrl(fileUtil.save(pcImage));
        banner.setMobileImageUrl(fileUtil.save(mobileImage));

        if (video != null && !video.isEmpty()) {
            banner.setVideoUrl(fileUtil.save(video));
        }

        banner.setCreatedBy("admin");

        return bannerRepository.save(banner).getId();
    }

    //수정
    @Transactional
    public void update(Long id, Banner updatedBanner,
                       MultipartFile pcImage, MultipartFile mobileImage) throws IOException {
        Banner banner = bannerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("배너를 찾을 수 없습니다. id=" + id));

        // 파일이 새로 업로드되면 교체
        if(pcImage != null && !pcImage.isEmpty()) {
            banner.setPcImageUrl(fileUtil.save(pcImage));
        }

        if(mobileImage != null && !mobileImage.isEmpty()) {
            banner.setMobileImageUrl(fileUtil.save(mobileImage));
        }

        // 나머지 필드 수정
        banner.setCourse(updatedBanner.getCourse());
        banner.setLink(updatedBanner.getLink());
        banner.setIsDeploy(updatedBanner.getIsDeploy());

        // Dirty Checking으로 자동 UPDATE
    }

    // 삭제 (논리 삭제)
    @Transactional
    public void delete(Long id) {
        /*
        * findById(id).orElseThrow(...)
          Optional<Banner> 반환 → 값이 있을 수도, 없을 수도 있음
          orElseThrow(): 값이 없으면 예외 발생
        * */
        Banner banner = bannerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("배너를 찾을 수 없습니다. id=" + id));

        banner.setIsDeleted(true);
        // JPA의 dirty checking으로 자동 UPDATE
        // @Transactional이 있어서 메서드 종료 시 자동 저장됨
    }

    // 필터링 조회
    public List<Banner> findByFilters(Course course, Boolean isDeploy) {
        return bannerRepository.findByFilters(course, isDeploy);
    }

}
