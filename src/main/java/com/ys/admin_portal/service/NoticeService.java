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
    public List<Notice> findByFilters(Course course, Boolean isPublished, Boolean isImportant, String keyword) {
        return noticeRepository.findByFilters(course, isPublished, isImportant, keyword);
    }

    // 단일 조회
    public Notice findOne(Long id) {
        return noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 공지사항이 없습니다. id : " + id));
    }

    // 등록
    @Transactional
    public Long regist(Notice notice) {
        return noticeRepository.save(notice).getId();
    }

    // 삭제
    @Transactional
    public void delete(Long id) {
        Notice notice = findOne(id);
        notice.delete();
    }

    // 수정
    @Transactional
    public void edit(Long id, Notice notice) {
        Notice editNotice = findOne(id);

        editNotice.setTitle(notice.getTitle());
        editNotice.setCourse(notice.getCourse());
        editNotice.setContent(notice.getContent());
        editNotice.setIsImportant(notice.getIsImportant());
        editNotice.setIsPublished(notice.getIsPublished());

    }

    // 조회 수 증가
    @Transactional
    public void increase(Long id) {
        noticeRepository.incrementViewCount(id);
    }

}
