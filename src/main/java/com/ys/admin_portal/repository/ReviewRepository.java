package com.ys.admin_portal.repository;

import com.ys.admin_portal.domain.Division;
import com.ys.admin_portal.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 전체 조회 (삭제 안된 것)
    @Query("select r from Review r where r.isDeleted = false order by r.regDate desc")
    List<Review> findAllActive();

    // 과정별 조회
    @Query("select r from Review r where r.isDeleted = false and r.division = :division order by r.regDate desc")
    List<Review> findByDivision(@Param("division") Division division);

    // 배포된 것만 조회
    @Query("select r from Review r where r.isDeleted = false and r.isDeploy = true order by r.regDate desc")
    List<Review> findDeployed();

    // 과정 + 배포상태 필터
    @Query("select r from Review r where" +
            "(:division is null or r.division = :division) and " +
            "(:isDeploy is null or r.isDeploy = :isDeploy) and " +
            "r.isDeleted = false " +
            "order by r.regDate desc"
    )
    List<Review> findByFilters(@Param("division") Division division,
                               @Param("isDeploy") Boolean isDeploy
                               );


}
