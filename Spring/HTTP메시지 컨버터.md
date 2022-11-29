### **HTTP메시지 컨버터**

뷰 템플릿으로 HTML을 생성해서 응답하는 것이 아니라, HTTP API처럼 JSON데이터를 메시지 바디에 직접 읽거나 쓰는 경우 메시지 컨버터를 사용하면 편리하다.

우선 스프링 MVC는 다음과 같은 경우에서 HTTP메시지 컨버터를 적용한다!

- HTTP 요청 : @RequestBody , HttpEntity(RequestEntity)  : RequestEntity는 HttpEntiity를 상속받음
- HTTP응답 : @ResponseBody , HttpEntity(ResponseEntity) : ResponseEntity도 동일

#### **org.springframework.http.converter.HttpMessageConverter**

```java
package org.springframework.http.converter;

public interface HttpMessageConverter<T> {
    
	boolean canRead(Class<?> clazz, @Nullable MediaType mediaType);
	boolean canWrite(Class<?> clazz, @Nullable MediaType mediaType);
	
    List<MediaType> getSupportedMediaTypes();
	
    T read(Class<? extends T> clazz, HttpInputMessage inputMessage)
		throws IOException, HttpMessageNotReadableException;
	void write(T t, @Nullable MediaType contentType, HttpOutputMessage
outputMessage) 
        throws IOException, HttpMessageNotWritableException;
}
```

HTTP메시지 컨버터는 요청,응답 둘 다에 사용되는 것을 알 수 있다.

- canRead(),canWrite() : 메시지 컨버터가 해당 클래스, 미디어 타입을 지원하는지 체크
- read().write() : 메시지 컨버터를 통해서 메시지를 읽고 쓰는 기능

### **스프링 부트 기본 메시지 컨버터**(일부생략)

- 0 = ByteArrayHttpMessageConverter
- 1 = StringHttpMessageConverter
- 2 = MappingJackson2HttpMessageConverter

스프링 부트는 다양한 메시지 컨버터를 제공하는데 대상 클래스 타입과 미디어 타입 둘 다 체크해서 사용 여부를 결정한다. 만약 만족하지 않다면 다음 메시지 컨버터로 우선순위가 넘어간다.

몇가지 주요한 메시지 컨버터들을 알아보자.

- ByteArrayHttpMessageConverter : **byte[]** 데이터를 처리한다.
  - 클래스 타입 : byte[] , 미디어 타입 : **/* *  (모든 타입을 지원한다는 뜻)
  - 요청 예 ) @RequestBody byte[] data
  - 응답 예 ) @ResponseBody return byte[] 쓰기 미디어 타입 application/octet-stream
- StringHttpMessageConverter : String문자로 데이터를 처리한다.
  - 클래스타입 : String , 미디어 타입 : **/* *  
  - 요청 예) @RequestBody String data
  - 응답 예 ) @ResponseBody return "ok" , 쓰기미디어타입 : text/plain
- MappingJackson2HttpMessageConverter : application/json 관련
  - 클래스 타입 : 객체 또는 HashMap, 미디어 타입 : application/json
  - 요청 예 ) @RequestBody HelloData data
  - 응답 예 ) @ResponseBody return helloData 쓰기 미디어 타입: application/json관련



### **HTTP 요청 데이터 읽기**

- HTTP 요청이 오고, 컨트롤러에서 @RequestBody , HttpEntity 파라미터를 사용한다.
- 메시지 컨버터가 메시지를 읽을 수 있는지 확인하기 위해 canRead() 를 호출한다.
  - 대상 클래스 타입을 지원하는가.
    - 예) @RequestBody 의 대상 클래스 ( byte[] , String , HelloData )
  - HTTP 요청의 Content-Type 미디어 타입을 지원하는가.
    - 예) text/plain , application/json , */*
- canRead() 조건을 만족하면 read() 를 호출해서 객체 생성하고, 반환한다.

### **HTTP 응답 데이터 생성**

- 컨트롤러에서 @ResponseBody , HttpEntity 로 값이 반환된다.
- 메시지 컨버터가 메시지를 쓸 수 있는지 확인하기 위해 canWrite() 를 호출한다.
  - 대상 클래스 타입을 지원하는가.
    - 예) return의 대상 클래스 ( byte[] , String , HelloData )
  - HTTP 요청의 Accept 미디어 타입을 지원하는가.(더 정확히는 @RequestMapping 의 produces )
    - 예) text/plain , application/json , */*
- canWrite() 조건을 만족하면 write() 를 호출해서 HTTP 응답 메시지 바디에 데이터를 생성한다.