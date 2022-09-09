package com.example.itemservice.domain.item;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum ItemType {
    BOOK("도서"),
    FOOD("음식"),
    ETC("기타"),
    ;

    private final String description;

    ItemType(String description) {
        this.description = description;
    }
}
