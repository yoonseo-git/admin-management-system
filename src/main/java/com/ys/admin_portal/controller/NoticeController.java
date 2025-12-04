package com.ys.admin_portal.controller;

import com.ys.admin_portal.domain.Course;
import com.ys.admin_portal.domain.Notice;
import com.ys.admin_portal.service.NoticeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/notices")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    // 조회 (필터링 포함)
    @GetMapping
    public String list(Model model,
                       HttpSession session,
                       @RequestParam(value = "course", required = false) Course course,
                       @RequestParam(value = "isPublished", required = false) Boolean isPublished,
                       @RequestParam(value = "isImportant", required = false) Boolean isImportant,
                       @RequestParam(value = "keyword", required = false) String keyword
    ) {

        // 조회 기록 삭제(조회 세션 초기화)
        session.removeAttribute("view");

        List<Notice> allActiveNotice = noticeService.findByFilters(course, isPublished, isImportant, keyword);
        model.addAttribute("notices", allActiveNotice);
        model.addAttribute("courses", Course.values());
        model.addAttribute("selectedCourse", course);
        model.addAttribute("selectedPublished", isPublished);
        model.addAttribute("selectedImportant", isImportant);
        model.addAttribute("selectedKeyword", keyword);

        return "notice/list";
    }

    // 등록폼
    @GetMapping("/new")
    public String createForm(Model model) {

        model.addAttribute("course", Course.values());

        return "/notice/form";
    }

    // 등록
    @PostMapping("/new")
    public String regist(@ModelAttribute Notice notice) {
        noticeService.regist(notice);

        return "redirect:/notices";
    }

    // 공지사항 상세보기
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id,
                         Model model,
                         HttpSession session) {

        // 조회 기록 없으면 조회수 증가
        if (session.getAttribute("view") == null) {
            noticeService.increase(id);
            session.setAttribute("view", ""); // 빈 문자열 저장
        }

        Notice notice = noticeService.findOne(id);
        model.addAttribute("notice", notice);
        return "notice/detail";
    }

    // 삭제
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        noticeService.delete(id);

        return "redirect:/notices";
    }

    // 수정폼
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Notice notice = noticeService.findOne(id);
        model.addAttribute("notice", notice);
        model.addAttribute("course", Course.values());

        return "notice/edit";
    }

    // 수정
    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Notice notice) {
        noticeService.edit(id, notice);
        return "redirect:/notices/" + id;
    }


}
