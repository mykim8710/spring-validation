package com.example.itemservice.config.message;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;

@Configuration
public class I18nConfig implements WebMvcConfigurer {

    /**
     * AcceptHeaderLocaleResolver : Http 헤더의 Accept-Language의 값을 사용한다. (기본값)
     * CookieLocaleResolver : 쿠키의 값을 저장하여 사용한다., 값이 없을 경우에는 기본값(현재 동작하는 컴퓨터 환경의 Locale)을 사용하도록 설정.
     * SessionLocaleResolver : 세션에 값을 저장하여 사용한다.
     * FixedLocaleResolver : 요청과 관계없이 default locale 사용한다
     */

    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver resolver = new CookieLocaleResolver();
        resolver.setDefaultLocale(Locale.getDefault());
        resolver.setCookieName("lang");
        return resolver;
    }

    /**
     * Locale 값이 변경되면 인터셉터가 동작한다.
     * url의 query parameter에 지정한 값이 들어올 때 동작한다.
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        return interceptor;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(10 * 60); // 리로드 시간
        return messageSource;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
