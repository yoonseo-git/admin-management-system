package com.ys.admin_portal.controller;

import com.ys.admin_portal.domain.CompanyInterview;
import com.ys.admin_portal.service.CompanyInterviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/companyInterviews")
@RequiredArgsConstructor
public class CompanyInterviewController {

    private final CompanyInterviewService companyInterviewService;

    // 목록 조회 (필터링 포함)
    @GetMapping
    public String list(
            @RequestParam(required = false) Boolean isDeploy,
            Model model) {
        List<CompanyInterview> interviews = companyInterviewService.findByFilters(isDeploy);
        model.addAttribute("interviews", interviews);
        model.addAttribute("selectedDeploy", isDeploy);

        return "/interviews/list";

    }

    // 등록 폼
    @GetMapping("/new")
    public String createForm() {
        return "/interviews/form";
    }

    // 등록
    @PostMapping("/new")
    public String create(
            @ModelAttribute CompanyInterview companyInterview,
            @RequestParam("thumbnailFile") MultipartFile thumbnailFile) throws IOException {

        companyInterviewService.register(companyInterview, thumbnailFile);

        return "redirect:/companyInterviews";
    }

    // 수정 폼
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        CompanyInterview interview = companyInterviewService.findOne(id);
        model.addAttribute("interview", interview);

        return "/interviews/edit";
    }

    // 수정
    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id,
                       @ModelAttribute CompanyInterview companyInterview,
                       @RequestParam(value = "thumbnailFile", required = false) MultipartFile thumbnailFile) throws IOException {
        companyInterviewService.update(id, companyInterview, thumbnailFile);

        return "redirect:/companyInterviews";
    }

    // 삭제
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        companyInterviewService.delete(id);

        return "redirect:/companyInterviews";
    }

    // 배포 토글
    @PostMapping("/{id}/toggle-deploy")
    public String toggleDeploy(@PathVariable Long id) {
        companyInterviewService.toggleDeploy(id);
        return "redirect:/companyInterviews";
    }

}
