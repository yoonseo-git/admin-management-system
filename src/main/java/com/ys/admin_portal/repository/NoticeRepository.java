package com.ys.admin_portal.repository;

import com.ys.admin_portal.domain.Course;
import com.ys.admin_portal.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
            "(:course is null or n.course = :course) and " +
            "(:isPublished is null or n.isPublished = :isPublished) and " +
            "(:isImportant is null or n.isImportant = :isImportant) and " +
            // keyword가 null이거나 빈 문자열이면 -> 전체조회 값이 있으면 -> 제목 또는 내용에서 검색
            "(:keyword is null or :keyword = '' or n.title like %:keyword% or n.content like %:keyword%) and " +
            "n.isDeleted = false " +
            "order by n.createdAt desc ")
    List<Notice> findByFilters(@Param("course") Course course,
                               @Param("isPublished") Boolean isPublished,
                               @Param("isImportant") Boolean isImportant,
                               @Param("keyword") String keyword);

    // 조회수 증가
    @Modifying
    @Query("UPDATE Notice n SET n.viewCount = n.viewCount + 1 WHERE n.id = :id")
    void incrementViewCount(@Param("id") Long id);

}
