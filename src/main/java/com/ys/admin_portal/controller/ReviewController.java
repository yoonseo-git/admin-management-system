package com.ys.admin_portal.controller;

import com.ys.admin_portal.domain.Division;
import com.ys.admin_portal.domain.Review;
import com.ys.admin_portal.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    public final ReviewService reviewService;

    // 목록 조회 (필터링 포함)
    @GetMapping
    public String list(@RequestParam(required = false) Division division,
                       @RequestParam(required = false) Boolean isDeploy,
                       Model model) {

        List<Review> reviews = reviewService.findByFilters(division, isDeploy);
        model.addAttribute("reviews", reviews);
        model.addAttribute("divisions", Division.values());
        model.addAttribute("selectedDivision", division);
        model.addAttribute("selectedDeploy", isDeploy);

        return "reviews/list";
    }

    // 등록 폼
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("divisions", Division.values());
        return "reviews/form";
    }

    // 등록
    @PostMapping("/new")
    public String create(@ModelAttribute Review review ) { // @ModelAttribute : HTML 폼 데이터를 자동으로 객체에 담아주는 어노테이션 (* HTML의 name 속성과 객체의 필드명이 같아야 함)
        reviewService.register(review);
        return "redirect:/reviews";
    }

    // 수정 폼
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Review review = reviewService.findOne(id);
        model.addAttribute("review", review);
        model.addAttribute("divisions", Division.values());
        return "reviews/edit";
    }

    // 수정
    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id, @ModelAttribute Review review) {

        reviewService.update(id, review.getContent(), review.getYear(),
                review.getMonth(), review.getDivision(), review.getRegId());

        return "redirect:/reviews";
    }

    // 삭제
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        reviewService.delete(id);
        return "redirect:/reviews";
    }

    // 배포 상태 토글
    @PostMapping("/{id}/toggle-deploy")
    public String toggleDeploy(@PathVariable Long id) {
        reviewService.toggleDeploy(id);
        return "redirect:/reviews";
    }



}
