package com.ys.admin_portal.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Course {
    IT("IT과정"),
    GAME("게임제작과정"),
    MEDIA("영상제작과정"),
    BUSAN("부산캠퍼스");

    private final String description;
}
