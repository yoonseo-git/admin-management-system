package com.ys.admin_portal.controller;

import com.ys.admin_portal.domain.Course;
import com.ys.admin_portal.domain.Faq;
import com.ys.admin_portal.service.FaqService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/faqs")
public class FaqController {

    private final FaqService faqService;

    // 조회 (필터 포함)
    @GetMapping
    public String list(Model model,
                       @RequestParam(required = false, value = "course") Course course,
                       @RequestParam(required = false, value = "category") String category,
                       @RequestParam(required = false, value = "isPublished") Boolean isPublished,
                       HttpSession session) {

        session.removeAttribute("view");

        List<Faq> faqs = faqService.findByFilters(course, category, isPublished);
        model.addAttribute("faqs", faqs);
        model.addAttribute("courses", Course.values());
        model.addAttribute("selectedCourse", course);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("selectedPublished", isPublished);

        return "faqs/list";
    }

    // 등록 폼
    @GetMapping("/new")
    public String registForm(Model model) {
        model.addAttribute("courses", Course.values());
        return "faqs/form";
    }

    // 등록
    @PostMapping
    public String regist(Faq faq) {
        faqService.regist(faq);

        return "redirect:/faqs";
    }

    // 수정 폼
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id,
                           Model model) {
        Faq faq = faqService.findOne(id);
        model.addAttribute("faq", faq);
        model.addAttribute("courses", Course.values());

        return "faqs/edit";
    }

    // 수정
    @PostMapping("/{id}")
    public String edit(@PathVariable Long id,
                       @ModelAttribute Faq faq) { // @ModelAttribute는 생략 가능 (Spring이 자동 인식)
        faqService.edit(faq, id);

        return "redirect:/faqs";
    }

    // 삭제
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        faqService.delete(id);
        return "redirect:/faqs";
    }

    // 상세보기
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model, HttpSession session) {

        if (session.getAttribute("view") == null) {
            faqService.increase(id);
            session.setAttribute("view", "");
        }

        Faq faq = faqService.findOne(id);
        model.addAttribute("faq", faq);

        return "faqs/detail";
    }


}
