package com.ys.admin_portal.service;

import com.ys.admin_portal.domain.Division;
import com.ys.admin_portal.domain.Review;
import com.ys.admin_portal.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;

    // 등록
    @Transactional
    public Long register(Review review) {
        return reviewRepository.save(review).getId();
    }

    // 전체 조회
    public List<Review> findAll() {
        return reviewRepository.findAllActive();
    }

    // 필터링 조회
    public List<Review> findByFilters(Division division, Boolean isDeploy) {
        return reviewRepository.findByFilters(division, isDeploy);
    }

    // 단건 조회
    public Review findOne(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() ->
                    new IllegalArgumentException("존재하지 않는 후기입니다. id: " + id));
    }

    // 수정
    @Transactional
    public void update(Long id, String content, String year, String month, Division division, String regId) {
        Review review = findOne(id);
        review.setContent(content);
        review.setYear(year);
        review.setMonth(month);
        review.setDivision(division);
        review.setRegId(regId);

        // Dirty Checking으로 자동 UPDATE
        /*
        * Dirty Checking이란?
        * JPA가 엔티티의 변경사항을 자동으로 감지해서 DB에 UPDATE 쿼리를 날려주는 기능
        * */
    }

    // 삭제 (soft delete)
    @Transactional
    public void delete(Long id) {
        Review review = findOne(id);
        review.delete();
    }

    // 배포 상태 토글
    @Transactional
    public void toggleDeploy(Long id) {
        Review review = findOne(id);
        if(review.getIsDeploy()) {
            review.undeploy();
        } else {
            review.deploy();
        }
    }



}
