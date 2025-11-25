package com.ys.admin_portal.repository;

import com.ys.admin_portal.domain.CompanyInterview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanyInterviewRepository extends JpaRepository<CompanyInterview, Long> {

    // 전체 조회 (삭제 안된 것)
    @Query("select ci from CompanyInterview ci where ci.isDeleted = false order by ci.regDate desc ")
    List<CompanyInterview> findAllActive();

    // 배포된 것만 조회
    @Query("select ci from CompanyInterview ci where ci.isDeploy = true and ci.isDeleted = false order by ci.regDate desc ")
    List<CompanyInterview> findDeployed();

    // 필터 조회
    @Query("select ci from CompanyInterview ci where" +
            "(:isDeploy is null or ci.isDeploy = :isDeploy) and " +
            "ci.isDeleted = false " +
            "order by ci.regDate desc "
    )
    List<CompanyInterview> findByFilters(@Param("isDeploy") Boolean isDeploy);

}
