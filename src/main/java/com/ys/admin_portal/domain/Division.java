package com.ys.admin_portal.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter //모든 필드의 getter 자동 생성
@RequiredArgsConstructor // final 필드를 받는 생성자 자동 생성
public enum Division {

    JAVA("자바"),
    SECURITY("보안"),
    GAME("게임"),
    VIDEO("영상");

    private final String koreanName;
}
