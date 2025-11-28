package com.ys.admin_portal.controller;

import com.ys.admin_portal.domain.Banner;
import com.ys.admin_portal.domain.Course;
import com.ys.admin_portal.service.BannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/banners")
@RequiredArgsConstructor
public class BannerController {

    private final BannerService bannerService;

    // 목록 조회 (필터 포함)
    @GetMapping
    public String list(
            @RequestParam(value = "course", required = false) Course course,
            @RequestParam(value = "isDeploy", required = false) Boolean isDeploy,
            Model model
    ) {

        List<Banner> banners = bannerService.findByFilters(course, isDeploy);

        model.addAttribute("banners", banners);
        model.addAttribute("selectedCourse", course);
        model.addAttribute("selectedDeploy", isDeploy);

        return "banner/list";
    }

    // 등록 폼
    @GetMapping("/new")
    public String createForm(Model model) {

        model.addAttribute("courses", Course.values());

        return "banner/form";
    }


    // 등록 처리
    @PostMapping
    public String create(
            @ModelAttribute Banner banner,
            @RequestParam("pcImage") MultipartFile pcImage,
            @RequestParam("mobileImage") MultipartFile mobileImage,
            @RequestParam(value = "video", required = false) MultipartFile video
    ) throws IOException {

        bannerService.register(banner, pcImage, mobileImage, video);
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
        model.addAttribute("courses", Course.values());
        return "banner/edit";
    }


    // 수정 처리
    @PostMapping("/{id}")
    public String update(
            @PathVariable Long id,
            @ModelAttribute Banner banner,
            @RequestParam(value = "pcImage", required = false) MultipartFile pcImage,
            @RequestParam(value = "mobileImage", required = false) MultipartFile mobileImage
    ) throws IOException {

        bannerService.update(id, banner, pcImage, mobileImage);
        return "redirect:/banners";
    }

    // 배포 상태 토글
    @PostMapping("/{id}/toggle-deploy")
    public String toggleDeploy(@PathVariable Long id) {
        bannerService.toggleDeploy(id);

        return "redirect:/banners";
    }

}