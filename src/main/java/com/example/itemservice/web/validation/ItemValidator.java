package com.example.itemservice.web.validation;

import com.example.itemservice.domain.item.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Slf4j
@Component
public class ItemValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Item.class.isAssignableFrom(clazz);
        // item == clazz
        // item == subItem
    }

    /**
     *
     * @param target : (Item) target
     * @param errors : bindingResult의 부모클래스
     */
    @Override
    public void validate(Object target, Errors errors) {
        Item item = (Item) target;

        if (!StringUtils.hasText(item.getItemName())) { // 상품명: 필수, 공백X
            errors.rejectValue("itemName", "required");
        }

        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) { // 가격: 1000원 이상, 1백만원 이하
            errors.rejectValue("price", "range", new Object[]{1000, 1000000}, null);
            // == ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult,"itemName", "required");
        }

        if (item.getQuantity() == null || item.getQuantity() >= 9999) { // 수량: 최대 9999
            errors.rejectValue("quantity", "max", new Object[]{9999}, null);
        }

        //특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                errors.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }


    }
}
