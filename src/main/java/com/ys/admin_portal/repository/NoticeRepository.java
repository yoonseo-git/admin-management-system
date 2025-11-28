package com.ys.admin_portal.repository;

import com.ys.admin_portal.domain.Course;
import com.ys.admin_portal.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    // 전체 조회
    @Query("select n from Notice n where n.isDeleted = false order by n.createdAt desc ")
    List<Notice> findAllActive();

    // 필터 조회
    @Query("select n from Notice n where " +
            "(:course = null or n.course = :course) and " +
            "(:isPublished = null or n.isPublished = :isPublished) and " +
            "(:isImportant = null or n.isImportant = :isImportant) and " +
            "n.isDeleted = false " +
            "order by n.createdAt desc ")
    List<Notice> findByFilters(@Param("course") Course course,
                               @Param("isPublished") Boolean isPublished,
                               @Param("isImportant") Boolean isImportant);
}
