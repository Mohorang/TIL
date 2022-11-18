### **HttpServletResponse 사용법**

1. **HTTP응답 메시지 생성**
   - HTTP 응답 코드 지정
   - 헤더 생성
   - 바디 생성
2. **편의 기능 제공**
   - Content-Type, 쿠키, Redirect



**응답코드 생성**

```java
response.setStatus(HttpServletResponse.SC_OK);
```

헤더 설정

```java
response.setHeader("Content-Type","text/plain;charset=utf-8");
response.setHeader("Cache-Control","no-cahce,no-store,must-revalidate");
response.setHeader("Pragma","no-cache");
response.setHeader("my-header","hello");
```

컨텐츠 타입이나 인코딩 방법의 경우

**response**.**setContentType**이나 **response**.**setCharacterEncoding**등 사용가능

Content-Length의 경우 자동생성됨



HttpServletResponse 객체를 이용해서 쿠키자동생성이나 Redirect, 컨텐츠 타입 설정등 편리하게 가능함!