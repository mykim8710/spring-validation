package com.example.itemservice.domain.item;

import lombok.Getter;
import lombok.ToString;

/**
 * FAST: 빠른 배송
 * NORMAL: 일반 배송
 * SLOW: 느린 배송
 **/
@Getter
@ToString
public class DeliveryCode {
    private String code;
    private String displayName;

    public DeliveryCode(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }
}
