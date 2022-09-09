package com.example.itemservice.message;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class MessageSourceTest {
    @Autowired
    MessageSource ms;

    @Test
    @DisplayName("메세지 - message Test")
    void helloMessageTest() {
        //Locale.setDefault(Locale.KOREA);    // 시스템 default가 us로 되어있음
        String result = ms.getMessage("hello", null, null);
        assertThat(result).isEqualTo("안녕");
    }

    @Test
    @DisplayName("메세지 - message code가 없을때 Exception 발생 Test")
    void notFoundMessageCodeTest() {
        //Locale.setDefault(Locale.KOREA);    // 시스템 default가 us로 되어있음
        assertThatThrownBy(() -> ms.getMessage("no_code", null, null)).isInstanceOf(NoSuchMessageException.class);
    }

    @Test
    @DisplayName("메세지 - message code가 없을때 기본메세지 설정 Test")
    void notFoundMessageCodeDefaultMessageTest() {
        //Locale.setDefault(Locale.KOREA);    // 시스템 default가 us로 되어있음
        String result = ms.getMessage("no_code", null, "기본 메시지", null);
        assertThat(result).isEqualTo("기본 메시지");
    }

    @Test
    @DisplayName("메세지 - 매개변수 Test")
    void argumentMessageTest() {
        //Locale.setDefault(Locale.KOREA);    // 시스템 default가 us로 되어있음
        String result = ms.getMessage("hello.name", new Object[]{"Spring"}, null);
        assertThat(result).isEqualTo("안녕 Spring");
    }

    @Test
    @DisplayName("국제화 - default language Test")
    void defaultLangTest() {
        //Locale.setDefault(Locale.KOREA);    // 시스템 default가 us로 되어있음
        assertThat(ms.getMessage("hello", null, null)).isEqualTo("안녕");
        assertThat(ms.getMessage("hello", null, Locale.KOREA)).isEqualTo("안녕");
    }

    @Test
    @DisplayName("국제화 - US language Test")
    void enLangTest() {
        //Locale.setDefault(Locale.KOREA);    // 시스템 default가 us로 되어있음
        assertThat(ms.getMessage("hello", null, Locale.ENGLISH)).isEqualTo("hello");
    }
}
