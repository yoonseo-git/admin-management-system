package com.ys.admin_portal.controller;

import com.ys.admin_portal.domain.Course;
import com.ys.admin_portal.domain.Notice;
import com.ys.admin_portal.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/notices")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    // 조회 (필터링 포함)
    @GetMapping
    public String list(Model model,
                       @RequestParam(value = "course", required = false) Course course,
                       @RequestParam(value = "isPublished", required = false) Boolean isPublished,
                       @RequestParam(value = "isImportant", required = false) Boolean isImportant
    ) {

        List<Notice> allActiveNotice = noticeService.findByFilters(course, isPublished, isImportant);
        model.addAttribute("notices", allActiveNotice);
        model.addAttribute("courses", Course.values());
        model.addAttribute("selectedCourse", course);
        model.addAttribute("selectedPublished", isPublished);
        model.addAttribute("selectedImportant", isImportant);

        return "notice/list";
    }


}
