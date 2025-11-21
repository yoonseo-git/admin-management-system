package com.ys.admin_portal.controller;

import com.ys.admin_portal.domain.Banner;
import com.ys.admin_portal.domain.Course;
import com.ys.admin_portal.dto.BannerUpdateDto;
import com.ys.admin_portal.service.BannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/banners")
@RequiredArgsConstructor
public class BannerController {

    private final BannerService bannerService;

    @Value("${file.upload.path}")
    private String uploadPath;

    // 목록 조회 (필터 포함)
    @GetMapping
    public String list(
            @RequestParam(value = "course", required = false) String courseStr,
            @RequestParam(value = "isDeploy", required = false) Boolean isDeploy,
            Model model
    ) {
        Course course = null;
        if (courseStr != null && !courseStr.isEmpty()) {
            course = Course.valueOf(courseStr);
        }

        List<Banner> banners = bannerService.findByFilters(course, isDeploy);

        model.addAttribute("banners", banners);
        model.addAttribute("selectedCourse", courseStr);
        model.addAttribute("selectedDeploy", isDeploy);

        return "banner/list";
    }

    // 등록 폼
    @GetMapping("/new")
    public String createForm() {
        return "banner/form";
    }

    // 등록 처리
    @PostMapping
    public String create(
            @RequestParam("course") String courseStr,
            @RequestParam("pcImage") MultipartFile pcImage,
            @RequestParam("mobileImage") MultipartFile mobileImage,
            @RequestParam(value = "video", required = false) MultipartFile video,
            @RequestParam(value = "link", required = false) String link,
            @RequestParam(value = "isDeploy", defaultValue = "false") boolean isDeploy
    ) throws IOException {

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String pcImageUrl = saveFile(pcImage);
        String mobileImageUrl = saveFile(mobileImage);

        String videoUrl = null;
        if (video != null && !video.isEmpty()) {
            videoUrl = saveFile(video);
        }

        Banner banner = Banner.builder()
                .course(Course.valueOf(courseStr))
                .pcImageUrl(pcImageUrl)
                .mobileImageUrl(mobileImageUrl)
                .videoUrl(videoUrl)
                .link(link)
                .isDeploy(isDeploy)
                .createdBy("admin")
                .build();

        bannerService.save(banner);

        return "redirect:/banners";
    }

    // 삭제
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        bannerService.delete(id);
        return "redirect:/banners";
    }

    // 수정 폼
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Banner banner = bannerService.findById(id);
        model.addAttribute("banner", banner);
        return "banner/edit";
    }

    // 수정 처리
    @PostMapping("/{id}")
    public String update(
            @PathVariable Long id,
            @RequestParam("course") String courseStr,
            @RequestParam(value = "pcImage", required = false) MultipartFile pcImage,
            @RequestParam(value = "mobileImage", required = false) MultipartFile mobileImage,
            @RequestParam(value = "videoUrl", required = false) String videoUrl,
            @RequestParam(value = "link", required = false) String link,
            @RequestParam(value = "isDeploy", defaultValue = "false") boolean isDeploy
    ) throws IOException {

        Banner existingBanner = bannerService.findById(id);

        String pcImageUrl = existingBanner.getPcImageUrl();
        String mobileImageUrl = existingBanner.getMobileImageUrl();

        if (pcImage != null && !pcImage.isEmpty()) {
            pcImageUrl = saveFile(pcImage);
        }

        if (mobileImage != null && !mobileImage.isEmpty()) {
            mobileImageUrl = saveFile(mobileImage);
        }

        BannerUpdateDto dto = BannerUpdateDto.builder()
                .course(Course.valueOf(courseStr))
                .pcImageUrl(pcImageUrl)
                .mobileImageUrl(mobileImageUrl)
                .videoUrl(videoUrl)
                .link(link)
                .isDeploy(isDeploy)
                .build();

        bannerService.update(id, dto);

        return "redirect:/banners";
    }

    // 파일 저장 메서드
    private String saveFile(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String savedFilename = UUID.randomUUID().toString() + "-"
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")) + extension;

        Path filePath = Paths.get(uploadPath + savedFilename);
        Files.write(filePath, file.getBytes());

        return savedFilename;
    }
}