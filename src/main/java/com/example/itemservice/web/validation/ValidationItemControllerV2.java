package com.example.itemservice.web.validation;

import com.example.itemservice.domain.item.Item;
import com.example.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/validation/v2/items")
@RequiredArgsConstructor
public class ValidationItemControllerV2 {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v2/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v2/addForm";
    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        log.info("objectName={}", bindingResult.getObjectName());
        log.info("target={}", bindingResult.getTarget());

        // item validation v1
//        if(!itemValidationV1(item, model).isEmpty()) {
//            log.error("error = {}", errors);
//            model.addAttribute("errors", errors);
//            return "validation/v2/addForm";
//        }

        // item validation v2
//        if(itemValidationV2(item, bindingResult).hasErrors()) {
//            log.error("error = {}", bindingResult);
//            return "validation/v2/addForm";
//        }

        // item validation v3
//        if (itemValidationV3(item, bindingResult).hasErrors()) {
//            log.error("error = {}", bindingResult);
//            return "validation/v2/addForm";
//        }

        // item validation v4
//        if (itemValidationV4(item, bindingResult).hasErrors()) {
//            log.error("error = {}", bindingResult);
//            return "validation/v2/addForm";
//        }

        // item validation v5
        if (itemValidationV5(item, bindingResult).hasErrors()) {
            log.error("error = {}", bindingResult);
            return "validation/v2/addForm";
        }

        // 성공 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }


    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item, BindingResult bindingResult) {
        // validation v1
//        if(!itemValidationV1(item, model).isEmpty()) {
//            log.error("error = {}", errors);
//            model.addAttribute("errors", errors);
//            return "validation/v2/editForm";
//        }

        // item validation v2
//        if (itemValidationV2(item, bindingResult).hasErrors()) {
//            log.error("error = {}", bindingResult);
//            return "validation/v2/editForm";
//        }

        // item validation v3
//        if (itemValidationV3(item, bindingResult).hasErrors()) {
//            log.error("error = {}", bindingResult);
//            return "validation/v2/editForm";
//        }

        // item validation v4
//        if (itemValidationV4(item, bindingResult).hasErrors()) {
//            log.error("error = {}", bindingResult);
//            return "validation/v2/editForm";
//        }

        // item validation v5
        if (itemValidationV5(item, bindingResult).hasErrors()) {
            log.error("error = {}", bindingResult);
            return "validation/v2/editForm";
        }

        itemRepository.update(itemId, item);
        return "redirect:/validation/v2/items/{itemId}";
    }

    @PostMapping("/{itemId}/delete")
    public String delete(@PathVariable Long itemId) {
        itemRepository.delete(itemId);
        return "redirect:/validation/v2/items";
    }

    private BindingResult itemValidationV5(Item item, BindingResult bindingResult) {
        if (!StringUtils.hasText(item.getItemName())) { // 상품명: 필수, 공백X
            // bindingResult.addError(new FieldError("item", "itemName", item.getItemName(), false, new String[]{"required.item.itemName"}, null, null));
            bindingResult.rejectValue("itemName", "required");
        }

        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) { // 가격: 1000원 이상, 1백만원 이하

            // bindingResult.addError(new FieldError("item", "price", item.getPrice(), false, new String[]{"range.item.price"}, new Object[]{1000, 1000000}, null));
            bindingResult.rejectValue("price", "range", new Object[]{1000, 1000000}, null);
        }

        if (item.getQuantity() == null || item.getQuantity() >= 9999) { // 수량: 최대 9999
            //bindingResult.addError(new FieldError("item", "quantity", item.getQuantity(), false, new String[]{"max.item.quantity"}, new Object[]{9999}, null));
            bindingResult.rejectValue("quantity", "max", new Object[]{9999}, null);
        }

        //특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                //bindingResult.addError(new ObjectError("item", new String[]{"totalPriceMin"}, new Object[]{10000, resultPrice}, null));
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

        return bindingResult;
    }

    private BindingResult itemValidationV4(Item item, BindingResult bindingResult) {
        if (!StringUtils.hasText(item.getItemName())) { // 상품명: 필수, 공백X
            bindingResult.addError(new FieldError("item", "itemName", item.getItemName(), false, new String[]{"required.item.itemName"}, null, null));
        }

        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) { // 가격: 1000원 이상, 1백만원 이하
            bindingResult.addError(new FieldError("item", "price", item.getPrice(), false, new String[]{"range.item.price"}, new Object[]{1000, 1000000}, null));
        }

        if (item.getQuantity() == null || item.getQuantity() >= 9999) { // 수량: 최대 9999
            bindingResult.addError(new FieldError("item", "quantity", item.getQuantity(), false, new String[]{"max.item.quantity"}, new Object[]{9999}, null));
        }

        //특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.addError(new ObjectError("item", new String[]{"totalPriceMin"}, new Object[]{10000, resultPrice}, null));
            }
        }

        return bindingResult;
    }

    private BindingResult itemValidationV3(Item item, BindingResult bindingResult) {
        if (!StringUtils.hasText(item.getItemName())) { // 상품명: 필수, 공백X
            bindingResult.addError(new FieldError("item", "itemName", item.getItemName(), false, null, null, "상품 이름은 필수 입니다."));
        }

        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) { // 가격: 1000원 이상, 1백만원 이하
            bindingResult.addError(new FieldError("item", "price", item.getPrice(), false, null, null, "가격은 1,000 ~ 1,000,000 까지 허용합니다."));
        }

        if (item.getQuantity() == null || item.getQuantity() >= 9999) { // 수량: 최대 9999
            bindingResult.addError(new FieldError("item", "quantity", item.getQuantity(), false, null, null, "수량은 최대 9,999 까지 허용합니다."));
        }

        //특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.addError(new ObjectError("item", null, null, "가격 x 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = " + resultPrice));
            }
        }

        return bindingResult;
    }

    private BindingResult itemValidationV2(Item item, BindingResult bindingResult) {
        // 검증로직
        if (!StringUtils.hasText(item.getItemName())) { // 상품명: 필수, 공백X
            bindingResult.addError(new FieldError("item", "itemName", "상품 이름은 필수 입니다."));
        }

        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) { // 가격: 1000원 이상, 1백만원 이하
            bindingResult.addError(new FieldError("item", "price", "가격은 1,000 ~ 1,000,000 까지 허용합니다."));
        }

        if (item.getQuantity() == null || item.getQuantity() >= 9999) { // 수량: 최대 9999
            bindingResult.addError(new FieldError("item", "quantity", "수량은 최대 9,999 까지 허용합니다."));
        }

        //특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.addError(new ObjectError("item", "가격 x 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = " + resultPrice));
            }
        }

        return bindingResult;
    }

    private Map<String, String> itemValidationV1(Item item, Model model) {
        // 검증 오류결과를 보관하는 Map
        Map<String, String> errors = new HashMap<>();

        // 검증로직
        if (!StringUtils.hasText(item.getItemName())) { // 상품명: 필수, 공백X
            errors.put("itemName", "상품 이름은 필수 입니다.");
        }

        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) { // 가격: 1000원 이상, 1백만원 이하
            errors.put("price", "가격은 1,000 ~ 1,000,000 까지 허용합니다.");
        }

        if (item.getQuantity() == null || item.getQuantity() >= 9999) { // 수량: 최대 9999
            errors.put("quantity", "수량은 최대 9,999 까지 허용합니다.");
        }

        //특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                errors.put("globalError", "가격 x 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = " + resultPrice);
            }
        }

        return errors;
    }

}

