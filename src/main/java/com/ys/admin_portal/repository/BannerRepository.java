package com.ys.admin_portal.repository;

import com.ys.admin_portal.domain.Banner;
import com.ys.admin_portal.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BannerRepository extends JpaRepository<Banner, Long> { // JpaRepository<엔티티 클래스, PK 타입>

    // 전체 조회(삭제 안 된 것)
    @Query("select b from Banner b where b.isDeleted = false order by b.createdAt desc")
    List<Banner> findAllActive();

    @Query("select b from Banner b where b.course = :course and b.isDeleted = false order by b.createdAt desc")
    List<Banner> findByCourse(@Param("course") Course course);

    @Query("select b from Banner b where b.isDeploy = true and b.isDeleted = false and b.course = :course order by b.createdAt desc")
    List<Banner> findDeployedByCourse(@Param("course") Course course);

    // 과정 + 배포상태 필터
    @Query("select b from Banner b where " +
            "(:course is null or b.course = :course) and " +
            "(:isDeploy is null or b.isDeploy = :isDeploy) and " +
            "(:keyword is null or :keyword = '' or b.createdBy = :keyword) and " +
            "b.isDeleted = false " +
            "order by b.createdAt desc ")
    List<Banner> findByFilters(@Param("course") Course course,
                               @Param("isDeploy") Boolean isDeploy,
                               @Param("keyword") String keyword);

}
