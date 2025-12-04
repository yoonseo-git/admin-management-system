package com.ys.admin_portal.domain;


import com.ys.admin_portal.dto.BannerUpdateDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity // 이 클래스는 데이터베이스 테이블과 매핑 , JPA가 관리하는 객체
@Table(name = "banners") // DB테이블 이름을 "banners"로 지정 (생략하면 클래스이름(Banner)을 테이블명으로 사용
@Getter @Setter // 모든 필드의 getter, setter 자동 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA가 객체 생성할 때 필요, 외부에서 new Banner()막음(직접 생성 방지)
@AllArgsConstructor
@Builder
public class Banner {

    @Id // 이 필드가 Primary Key(PK)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동으로 값 생성, IDENTITY : DB의 AUTO_INCREMENT 사용
    private Long id;

    @Enumerated(EnumType.STRING) // Enum을 문자열로 저장
    @Column(nullable = false, length = 20)
    private Course course;

    @Column(nullable = false, length = 500)
    private String pcImageUrl;

    @Column(nullable = false, length = 500)
    private String mobileImageUrl;

    @Column(length = 500)
    private String videoUrl;

    @Column(length = 1000)
    private String link;

    @Column(nullable = false)
    private Boolean isDeploy = false;

    @Column(nullable = false)
    private Boolean isDeleted = false;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Column(nullable = false, updatable = false, length = 50)
    private String createdBy;

    @Column(length = 50)
    private String updatedBy;



    @PrePersist // DB에 INSERT 되기 직전에 자동 실행, 생성 시각 자동 입력
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate // DB에 UPDATE 되기 직전에 자동 실행, 수정 시각 자동 갱신
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // 비지니스 로직

    // 배포상태 토글
    public void deploy() {
        this.isDeploy = true;
    }

    public void undeploy() {
        this.isDeploy = false;
    }

    // soft delete를 위한 setter
    public void delete() {
        this.isDeleted = true;
    }

}
