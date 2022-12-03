### **타임리프(Thymeleaf)**

------



#### **타임리프 특징**

- 서버 사이드 HTML 렌더링 (SSR)
- 네츄럴 템플릿
- 스프링 통합 지원



**서버사이드 렌더링** : 타임리프는 백엔드 서버에서 HTML을 동적으로 렌더링하는 용도로 사용된다.

**네츄럴 템플릿**

- 타임리프는 순수 HTML을 최대한 유지하는 특징이 있다. 
- 타임리프로 작성한 파일은 HTML을 유지하기 때문에 웹 브라우저에서 파일을 직업 열어도 내용을 확인할 수 있고, 서버를 통해 뷰 템플릿을 거치면 동적으로 변경된 결과를 확인할 수 있다.
- JSP를 포함한 다른 뷰 템플릿들은 해당 파일을 열면 예를 들어 JSP 파일 자체를 그대로 웹 브라우저에서 열어보면 JSP소스코드와 HTML이 뒤죽박죽 섞여서 웹 브라우저에서 정상적인 HTML결과를 확인할 수 없다. 오직 서버를 통해서 JSP가 렌더링 되고HTML응답 결과를 받아야 화면을 확인할 수 있다.
- 반면에 타임리프로 작성된 파일은 해당 파일을 그대로 웹 브라우저에서 열어도 정상적인 HTML결과를 확인할 수 있다. 물론이 경우 동적으로 결과가 렌더링 되지는 않는다. 하지만 HTML마크업 결과가 어떻게 되는지 파일만 열어도 확인 할 수 있다. 이렇게 **순수 HTML을 그대로 유지하면서 뷰 템플릿도 사용할 수 있는 타임리프의 특징을 네츄럴 템플릿** 이라고 한다.

------



### **타임리프의 기본기능**

#### **텍스트 - text,utext**

타임리프는 기본적으로 HTML태그의 속성에 기능을 정의해서 동작한다.

HTML의 콘텐츠에서 데이터를 출력할 때 **th:text**를 사용하면 된다.

```html
<li>th:text 사용 <span th:text="${데이터의 name}"> </span></li>
```

위 방법은 HTML의 태그 속성 안에서 데이터를 출력할 떄 사용하는 방법이고 HTML의 콘텐츠 영역 안에서 데이터를 출력하고 싶을 때에는 다음과 같이 **[[...]]**를 사용하면 된다.

```html
<li>컨텐츠 안에서 직접 출력하기 = [[${data}]]</li>
```



### **Escape**

HTML 문서는 < , > 같은 특수 문자를 기반으로 정의된다. 따라서 뷰 템플릿으로 HTML 화면을 생성할
때는 출력하는 데이터에 이러한 특수 문자가 있는 것을 주의해서 사용해야 한다.

```java
model.addAttribute("data","Hello <b>Spring!</b>");
```

컨트롤러에서 addAttribute에서 글씨를 진하게 하기위해 data 에 태그를 추가햇는데 실제 웹 브라우저에서는 글자가 진해지는게 아닌  (&lt)같은 이상한 문자로 변경된 것을 볼 수 있었다.

#### **HTML 엔티티**

웹 브라우저는 < 를 HTML 테그의 시작으로 인식한다. 따라서 < 를 테그의 시작이 아니라 문자로 표현할 수있는 방법이 필요한데, 이것을 HTML 엔티티라 한다. 그리고 이렇게 HTML에서 사용하는 특수 문자를 HTML 엔티티로 변경하는 것을 이스케이프(escape)라 한다. 그리고 타임리프가 제공하는 th:text , [[...]] 는 기본적으로 이스케이스(escape)를 제공한다.

이스케이프 기능을 사용하지 않으려면 Unescape를 사용해야 한다. 다음 두가지 방식이 존재한다.

- th:text --> th:utext
- [[...]] ---> [(...)]

실제 개발에서는 escape를 사용하지 않아 HTML렌더링이 정상적으로 이뤄지지않는 문제가 자주 발생한다고 한다. 그래서 타임리프도 기본적으로 이스케이프를 제공한다고 한다. 필요할 때 이외에는 unescape를 사용하지 말 것!



------

### **변수 - SpringEL**

타임리프에서 변수를 사용할 떄는 변수 표현식을 사용한다.

### **변수 표현식 --> ${...}**

위 변수 표현식에는 SpringEL이라는 스프링이 제공하는 표현식을 사용할 수 있다.

### **SpringEL 다양한 표현식 사용**

**Object**

- user.username : user의 username을 프로퍼티 접근 user.getUsername()
- user['username'] : 위와 같음 user.getUsername()
- user.getUsername() : user의 getUsername() 을 직접 호출

**List**

- users[0].username : List에서 첫 번째 회원을 찾고 username 프로퍼티 접근
- list.get(0).getUsername()
- users[0]['username'] : 위와 같음
- users[0].getUsername() : List에서 첫 번째 회원을 찾고 메서드 직접 호출

**Map**

- userMap['userA'].username : Map에서 userA를 찾고, username 프로퍼티 접근
- map.get("userA").getUsername()
- userMap['userA']['username'] : 위와 같음
- userMap['userA'].getUsername() : Map에서 userA를 찾고 메서드 직접 호출

**th:with**를 사용하면 지역 변수를 선언해서 사용할 수 있다. 지역 변수는 선언한 태그 안에서만 사용할 수 있다.



------

### **기본 객체들**

타임리프는 기본 객체들을 제공한다.

- **${#request}**
- **${#response}**
- **${#session}**
- **${#servletContext}**
- **${#locale}**

그런데 #request 는 HttpServletRequest 객체가 그대로 제공되기 때문에 데이터를 조회하려면
request.getParameter("data") 처럼 불편하게 접근해야 한다.

이런 점을 해결하기 위해 편의 객체도 제공한다.

- HTTP 요청 파라미터 접근: param
  - 예) ${param.paramData}
- HTTP 세션 접근: session
  - 예) ${session.sessionData}
- 스프링 빈 접근: @
  - 예) ${@helloBean.hello('Spring!')}



------

###  URL링크

타임리프에서 URL링크를 생성할 때에는 @{...}문법을 사용하면 된다.



#### **단순한 URL**

- **@{/hello} /hello**
  - 쿼리 파라미터
- **@{/hello(param1=${param1}, param2=${param2})}**
  - /hello?param1=data1&param2=data2
  - **() 에 있는 부분은 쿼리 파라미터로 처리된다.**

#### **경로 변수**

- @{/hello/{param1}/{param2}(param1=${param1}, param2=${param2})}
  - /hello/data1/data2
  - **URL 경로상에 변수가 있으면 () 부분은 경로 변수로 처리된다.**

#### **경로 변수 + 쿼리 파라미터**

- @{/hello/{param1}(param1=${param1}, param2=${param2})}
  - 결과 : /hello/data1?param2=data2
  - **경로 변수와 쿼리 파라미터를 함께 사용할 수 있다.**

상대경로, 절대경로, 프로토콜 기준을 표현할 수 도 있다.
/hello : 절대 경로
hello : 상대 경로

------

### **리터럴**

리터럴이란(Literals) : 소스 코드상에서 고정된 값을 말하는 용어이다. 예를들어 "Hello"는 문자리터럴이고 10,20은 숫자 리터럴이다.

타임리프에서 문자 리터럴은 항상 `''`작은 따옴표로 감싸야 한다.
ex)`<span th:text=" 'hello' ">`

그런데 항상 문자를 작은따옴표로 감싸는것은 귀찮은 일이므로 공백없이 쭉 이어져나간다면 하나의 의미있는 토큰으로 인지해서 다음과 같ㅇ이 작은 따옴표를 생략할 수 있다.
룰 : `A-Z , a-z , 0-9 , [] , . , - , _`

ex)`<span th:text="hello">`

#### **오류**

`<span th:text="hello world!"></span>`

문자 리터럴은 원칙상 공백없이 이어지는게 아니라면 작은따옴표로 감싸줘야하기 떄문에 위는 오류이다.
