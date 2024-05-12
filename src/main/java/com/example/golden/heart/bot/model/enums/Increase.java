package com.example.golden.heart.bot.model.enums;

import lombok.Getter;

@Getter
public enum Increase {
    SHORT("14 дней"),
    LONG("30 дней");

    private final String title;

    Increase(String title) {
        this.title = title;
    }
}
