package com.ys.admin_portal.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NO")
    private Long id;

    @Lob // 긴 텍스트 저장
    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "REVIEW_YEAR",length = 4)
    private String year;

    @Column(name = "REVIEW_MONTH",length = 2)
    private String month;

    @Enumerated(EnumType.STRING) // Enum을 문자열로 저장
    @Column(columnDefinition = "TEXT")
    private Division division;

    @Column(name = "REG_ID", length = 20)
    private String regId;

    @Column(name = "REG_DATE", length = 14)
    private LocalDateTime regDate;

    @Column(name = "IS_DELETED")
    private Boolean isDeleted = false;

    @Column(name = "IS_DEPLOY")
    private Boolean isDeploy = false;

    // 등록일시 자동 설정
    @PrePersist // 저장 전 자동 실행
    public void prePersist() {
        this.regDate = LocalDateTime.now();
    }

    // 비즈니스 메서드
    public void delete() {
        this.isDeleted = true;
    }
    // 배포 상태 토글
    public void deploy() {
        this.isDeploy = true;
    }
    // 배포 상태 토글
    public void undeploy() {
        this.isDeploy = false;
    }


}
