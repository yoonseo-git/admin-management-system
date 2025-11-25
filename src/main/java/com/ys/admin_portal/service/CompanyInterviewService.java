package com.ys.admin_portal.service;

import com.ys.admin_portal.domain.CompanyInterview;
import com.ys.admin_portal.repository.CompanyInterviewRepository;
import com.ys.admin_portal.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyInterviewService {

    private final CompanyInterviewRepository companyInterviewRepository;
    private final FileUtil fileUtil;

    // 필터링 조회
    public List<CompanyInterview> findByFilters(Boolean isDeploy) {
        return companyInterviewRepository.findByFilters(isDeploy);
    }

    // 단건 조회
    public CompanyInterview findOne(Long id) {
       return companyInterviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("인터뷰를 찾을 수 없습니다. id : " + id));
    }

    // 등록
    @Transactional
    public Long register(
            @ModelAttribute CompanyInterview companyInterview,
            @RequestParam("thumbnailFile") MultipartFile thumbnailFile
            ) throws IOException {

        // 파일 저장
        companyInterview.setThumbNailUrl(fileUtil.save(thumbnailFile));

        return companyInterviewRepository.save(companyInterview).getId();
    }

    // 삭제
    @Transactional
    public void delete(Long id) {
        CompanyInterview interview = findOne(id);

        interview.delete();
    }

    // 배포 토글
    @Transactional
    public void toggleDeploy(Long id) {
        CompanyInterview interview = findOne(id);

        if(interview.getIsDeploy()) {
            interview.unDeploy();
        } else {
            interview.deploy();
        }

    }

}
