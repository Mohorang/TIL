HTTP 요청 메시지가 파라미터나 HTML form에 담겨서 오는것이 아닌 Body에 담겨져 왔을때는 어떻게 해야할까??

**HTTP message body**에 데이터를 직접 담아서 요청하는 경우

- HTTP API에서 주로 사용, **JSON**,**XML**, **TEXT등이** 존재한다.
- 데이터 형식은 주로 Json이 사용된다.
- 메서드에는 **POST,PUT,PATCH**

요청 파라미터와는 다르게 HTTP메시지 바디를 통해 데이터가 직접 넘어오는 경우에는 **@RequestParam**, @ModelAttribute와 같은 어노태이션은 사용할 수 없다. **단 HTML Form 형식으로 전달되는 경우 요청 파라미터로 인정된다.**



- 가장 단순한 텍스트 메시지를 HTTP메시지 바디에 담아서 전송하고 읽어본다.
- http 메시지 바디의 데이터를 InputStream을 사용해서 직접 읽을 수 있다.

```java
@PostMapping("/request-body-string-v1")
public void requestBodyString(HttpServletRequest request, HttpServletResponse response) throws IOException {
    ServletInputStream inputStream = request.getInputStream();
    //바이트코드를 문자로 바꿀건지 , 항상 지정해줄 필요가 있음 ex)UTF_8
    String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

    log.info("messageBody = {}",messageBody);
    response.getWriter().write("ok");
}
```



#### **Input,Output스트림 Reader**

```java
/**
* InputStream(Reader): HTTP 요청 메시지 바디의 내용을 직접 조회
* OutputStream(Writer): HTTP 응답 메시지의 바디에 직접 결과 출력
*/
@PostMapping("/request-body-string-v2")
public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException {
    //바이트코드를 문자로 바꿀건지 , 항상 지정해줄 필요가 있음 ex)UTF_8
    String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

    log.info("messageBody = {}",messageBody);
    responseWriter.write("ok");
}
```

스프링 MVC는 다음 파라미터를 지원해준다.

- InputStream(Reader) : HTTP요청 메시지 바디의 내용을 직접 조회
- OutputStream(Writer): HTTP응답 메시지의 바디에 직접 결과 출력



#### **HttpEntity를 사용한 메시지 바디 조회**

```java
//Http 메시지 컨버터
//Http메시지를 그대로 스펙화?
//HTTP header,body정보를 편리하게 조회
/**
* HttpEntity: HTTP header, body 정보를 편리하게 조회
* - 메시지 바디 정보를 직접 조회(@RequestParam X, @ModelAttribute X)
* - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
*
* 응답에서도 HttpEntity 사용 가능
* - 메시지 바디 정보 직접 반환(view 조회X)
* - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
*/
@PostMapping("/request-body-string-v3")
public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) throws IOException {

    log.info("messageBody = {}",httpEntity.getBody());
    return new HttpEntity<>("ok");
}
```

스프링 MVC는 **다음 파라미터**를 지원한다.

- **HttpEntity**: HTTP header,body정보를 편리하게 조회 가능
  - 메시지 바디 정보를 직접 조회
  - 요청 파라미터를 조회하는 기능과는 관계x (ex: @RequestParam, @ModelAttribute)
- **HttpEntity는** 응답에서도 사용 가능
  - 메시지 바디 정보 직접 반환
  - 헤더 정보 포함 가능
  - view 조회x

HttpEntity를 상속받은 객체들

- **RequestEntity**
  - HttpMethod,url정보가 추가, 요청에서 사용
- **ResponseEntity**
  - HTTP상태 코드 설정이 가능해진다. 응답에서 사용

스프링 MVC내부에서는 HTTP메시지 바디를 읽어서 문자나 객체로 변환해서 전달해주는데 이떄 HTTP 메시지 컨버터(**HttpMessageConverter**)라는 기능을 사용한다. 나중에 공부해볼 것.



#### **@RequestBody사용** 

```java
/**
* @RequestBody
* - 메시지 바디 정보를 직접 조회(@RequestParam X, @ModelAttribute X)
* - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
*
* @ResponseBody
* - 메시지 바디 정보 직접 반환(view 조회X)
* - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
*/
@ResponseBody
@PostMapping("/request-body-string-v4")
public String requestBodyStringV4(@RequestBody String messageBody) throws IOException {

    log.info("messageBody = {}",messageBody);
    return "ok";
}
```

@**RequestBody를** 사용하여 메시지 바디에 담겨있는 내용을 손쉽게 받아올 수 있다. 단 @**RequestParam이나** @**ModelAttribute와** 같은 파라미터의 내용을 가져오는 어노태이션과는 전혀 관계가 없는것을 기억해두자!