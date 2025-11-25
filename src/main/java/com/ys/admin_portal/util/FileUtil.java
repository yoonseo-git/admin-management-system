package com.ys.admin_portal.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class FileUtil {

    @Value("${file.upload.path}")
    private String uploadPath;

    public String save(MultipartFile file) throws IOException {

        // 디렉토리 생성
        File uploadDir = new File(uploadPath);
        if(!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // 파일명 생성
        String originalFilename = file.getOriginalFilename();
        String extention = originalFilename.substring(originalFilename.lastIndexOf("."));
        String savedFilename = UUID.randomUUID().toString() + "-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")) + extention;

        // 파일 저장
        Path filePath = Paths.get(uploadPath + savedFilename);
        Files.write(filePath, file.getBytes());

        return savedFilename;
    }

}
