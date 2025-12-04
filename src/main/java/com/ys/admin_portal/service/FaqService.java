package com.ys.admin_portal.service;

import com.ys.admin_portal.domain.Course;
import com.ys.admin_portal.domain.Faq;
import com.ys.admin_portal.repository.FaqRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FaqService {

    private final FaqRepository faqRepository;

    // 조회
    public List<Faq> findAll() {
        return faqRepository.findAllActive();
    }

    // 단일 조회
    public Faq findOne(Long id) {
        return faqRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 FAQ가 없습니다. id: " + id));
    }

    // 필터 조회
    public List<Faq> findByFilters(Course course, String category, Boolean isPublished) {
        return faqRepository.findByFilters(course, category, isPublished);
    }

    // 등록
    @Transactional
    public Long regist(Faq faq) {
        return faqRepository.save(faq).getId();
    }

    // 수정
    @Transactional
    public void edit(Faq faq, Long id) {

        Faq one = findOne(id);
        one.setQuestion(faq.getQuestion());
        one.setAnswer(faq.getAnswer());
        one.setCourse(faq.getCourse());
        one.setCategory(faq.getCategory());
        one.setIsPublished(faq.getIsPublished());

        // Dirty Checking ..
    }

    // 삭제
    @Transactional
    public void delete(Long id) {
        Faq faq = findOne(id);
        faq.delete();

        // Dirty Checking ..
    }

    // 조회수 증가
    @Transactional
    public void increase(Long id) {
        findOne(id).increase();
    }

}
