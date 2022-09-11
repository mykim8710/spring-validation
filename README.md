# Spring - validation

## Project Spec
- 프로젝트 선택
    - Project: Gradle Project
    - Spring Boot: 2.7.3
    - Language: Java
    - Packaging: Jar
    - Java: 11
- Project Metadata
    - Group: com.example
    - Artifact: item-service
    - Name: spring-message
    - Package name: com.example.itemservice
- Dependencies: **Spring Web**, **Thymeleaf**, **Lombok**


## Spring - validation
- Validation - 검증 직접 처리
- Validation - BindingResult
  - FieldError, ObjectError
- 오류 코드와 메시지 처리
- Validation - Validator 분리
- Bean Validation
  - @annotation 적용 및 testCode
  - 에러메세지 : error.properties customize
  - Item 저장, 수정 각각 Validator적용
    - 방법1 : groups적용
    - 방법2 : 전송객체 분리(ItemSaveFormDto, ItemUpdateFormDto)