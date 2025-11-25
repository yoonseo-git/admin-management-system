package com.ys.admin_portal.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompanyInterview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NO")
    private Long id;

    @Column(nullable = false ,columnDefinition = "TEXT")
    private String title;

    @Column(nullable = false)
    private String company;

    @Lob
    @Column(nullable = false ,columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, length = 500)
    private String thumbNailUrl;

    @Column(nullable = false, updatable = false)
    private LocalDateTime regDate;

    private LocalDateTime updatedDate;

    @Column(nullable = false, updatable = false ,length = 50)
    private String regId;

    @Column(length = 50)
    private String updatedId;

    @Column(nullable = false)
    private Boolean isDeleted = false;

    @Column(nullable = false)
    private Boolean isDeploy = false;

    // 비즈니스 메서드
    public void delete() {
        this.isDeleted = true;
    }

    public void deploy() {
        this.isDeploy = true;
    }

    public void unDeploy() {
        this.isDeploy = false;
    }

    @PrePersist
    protected void onCreate() {

        if(this.isDeploy == null) {
            this.isDeploy = false;
        }

        if(this.isDeleted == null) {
            this.isDeleted = false;
        }

        this.regDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();

    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedDate = LocalDateTime.now();
    }

}
