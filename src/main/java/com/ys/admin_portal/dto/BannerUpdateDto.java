package com.ys.admin_portal.dto;

import com.ys.admin_portal.domain.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BannerUpdateDto {
    private Course course;
    private String pcImageUrl;
    private String mobileImageUrl;
    private String videoUrl;
    private String link;
    private boolean isDeploy;
}
