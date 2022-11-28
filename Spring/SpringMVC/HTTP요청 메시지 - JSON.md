### **HTTP요청 메시지 - JSON**

이번에는 HTTP API에서 주로 사용하는 JSON데이터 형식을 조회해보는 방법을 공부해보자

#### **직접 request를 받아와 변환하여 얻는 방법**

```java
@PostMapping("/request-body-json-v1")
public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
    ServletInputStream inputStream = request.getInputStream();
    //메시지 바디에 담긴 내용을 문자로 변환
    String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
    log.info("messageBody= {}",messageBody);
    //변환된 문자를 객체로 변환 objectMapper라는 jackson라이브러리 사용
    HelloData helloData = objectMapper.readValue(messageBody,HelloData.class);
    log.info("username = {} , age = {}",helloData.getUsername(),helloData.getAge());

    response.getWriter().write("ok");
}
```

- HttpServletRequest를 사용해서 직접 Http메시지 바디에서 데이터를 읽어와서 문자로 변환한다. 
- 문자로 된 Json데이터를 Jackson라이브러리인 objectMapper를 사용해서 자바 객체로 변환시킨다.

#### **@RequestBody사용한 문자변환**

```java
@ResponseBody
@PostMapping("/request-body-json-v2")
public String requestBodyJsonV2(@RequestBody String messageBody) throws IOException {

    log.info("messageBody= {}",messageBody);
    HelloData helloData = objectMapper.readValue(messageBody,HelloData.class);
    log.info("username = {} , age = {}",helloData.getUsername(),helloData.getAge());

    return "ok";
}
```

@**RequestBody를** 사용해서 작성해야 하는 코드를 단축, messageBody에는 이미 문자로 변환되어 저장되어 있기 떄문에 이전 단계에서 사용한 objectMapper.readValue를 사용하여 객체로 변환시킨다.



#### **@RequestBody를 사용한 객체로 변환**

```java
@ResponseBody
@PostMapping("/request-body-json-v3")
public String requestBodyJsonV3(@RequestBody HelloData helloData) throws IOException {

    log.info("username = {} , age = {}",helloData.getUsername(),helloData.getAge());
    return "ok";
}
```

- @**RequestBody를** 사용하면 직접만든객체에 바로 저장해줄 수 있다.
- 이전단계에서 햇던 작업들을 추가적으로 생략가능(**objectMapper사용** 필요 x)
- **HttpEntity**,@**RequestBody를** 사용하면 Http메시지 컨버터가 HTTP메시지 바디의 내용을 우리가 원하는 문자나 객체 등으로 변환해준다. HTTP메시지 컨버터는 문자뿐 아니라 JSON도 객체로 변환시켜주는데 V2에서 햇던 작업을 대신해준다.
- 중요한점은 Json데이터를 처리할 때에는 @**RequestBody를** 생략할 수 없다. 애노테이션이 생략되면 스프링은 String,int,Integer타입에는 **@RequestParam**을 , 나머지의 경우@ModelAttribute 어노태이션을 적용시키는데 객체를 사용할 경우 @ModelAttribute 가 적용되는데 이 경우 **@ModelAttribute** 는 요청 파라미터를 처리하는 어노태이션이기 떄문에 오류가 발생하게 된다.



#### **HttpEntity도 사용가능하다**

```java
//HttpEntity를 사용해도 가능
@ResponseBody
@PostMapping("/request-body-json-v4")
public String requestBodyJsonV4(HttpEntity<HelloData> data) throws IOException {

    HelloData helloData = data.getBody();
    log.info("username = {} , age = {}",helloData.getUsername(),helloData.getAge());

    return "ok";
}
```



#### @ResponseBody

```java
@ResponseBody
@PostMapping("/request-body-json-v5")
public HelloData requestBodyJsonV5(@RequestBody HelloData helloData) throws IOException {
    log.info("username = {} , age = {}",helloData.getUsername(),helloData.getAge());
    return helloData;
}
```

**@ResponseBody**를 사용하여 응답의 경우에도 반환하고 싶은 객체를 HTTP메시지 바디에 넣어줄 수 있다.

- @RequestBody 요청
  - JSON 요청 :arrow_right:HTTP 메시지 :arrow_right:컨버터 객체
- @ResponseBody 응답
  - 객체 HTTP :arrow_right:메시지 컨버터 :arrow_right:JSON 응답