package com.ys.admin_portal.repository;

import com.ys.admin_portal.domain.Course;
import com.ys.admin_portal.domain.Faq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FaqRepository extends JpaRepository<Faq , Long> {

    // 전체 조회
    @Query("SELECT f FROM  Faq f WHERE f.isDeleted = false ORDER BY f.createdAt DESC ")
    List<Faq> findAllActive();

    // 필터링 조회
    @Query("SELECT f FROM  Faq f WHERE " +
            "(:course is null or :course = f.course) and " +
            "(:category is null or :category = '' or :category = f.category) and " +
            "(:isPublished is null or :isPublished = f.isPublished) and " +
            "f.isDeleted = false " +
            "order by f.createdAt desc"
    )
    List<Faq> findByFilters(@Param("course") Course course,
                            @Param("category") String category,
                            @Param("isPublished") Boolean isPublished);

}
