package com.ys.admin_portal.service;

import com.ys.admin_portal.domain.Banner;
import com.ys.admin_portal.domain.Course;
import com.ys.admin_portal.dto.BannerUpdateDto;
import com.ys.admin_portal.repository.BannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true) // 모든 메서드가 기본적으로 readOnly = true -> 조회전용 최적화
public class BannerService {

    private final BannerRepository bannerRepository;

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
    public Long save(Banner banner) {
        Banner saved = bannerRepository.save(banner);
        return saved.getId();
    }

    //수정
    @Transactional
    public void update(Long id, BannerUpdateDto dto) {
        Banner banner = bannerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("배너를 찾을 수 없습니다. id=" + id));

        banner.update(dto);
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
