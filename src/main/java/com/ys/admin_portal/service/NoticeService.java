package com.ys.admin_portal.service;

import com.ys.admin_portal.domain.Course;
import com.ys.admin_portal.domain.Notice;
import com.ys.admin_portal.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {

    private final NoticeRepository noticeRepository;

    // 전체 조회 (필터링 미포함)
    public List<Notice> findAllActive() {
        return noticeRepository.findAllActive();
    }

    // 필터 조회
    public List<Notice> findByFilters(Course course, Boolean isPublished, Boolean isImportant) {
        return noticeRepository.findByFilters(course, isPublished, isImportant);
    }

}
