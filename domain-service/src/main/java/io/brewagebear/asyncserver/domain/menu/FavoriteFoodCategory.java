package io.brewagebear.asyncserver.domain.menu;

import lombok.Getter;

@Getter
public enum FavoriteFoodCategory {
    KOREAN_FOOD("한식"),
    CHINESE_FOOD("중식"),
    WESTERN_FOOD("양식");

    private final String title;

    FavoriteFoodCategory(String title) {
        this.title = title;
    }
}
