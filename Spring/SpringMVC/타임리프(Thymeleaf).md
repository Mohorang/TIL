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

------

### **속성값**

------

## **반복**

타임리프에서 반복은 **`th:each`** 를 사용한다. 추가로 반복에서 사용할 수 있는 여러 상태 값을 지원한다.

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
  <meta charset="UTF-8">
  <title>Title</title>
</head>
<body>
<h1>기본 테이블</h1>
<table border="1">
  <tr>
    <th>username</th>
    <th>age</th>
  </tr>
  <tr th:each="user : ${users}">
    <td th:text="${user.username}">username</td>
    <td th:text="${user.age}">0</td>
  </tr>
</table>
<h1>반복 상태 유지</h1>
<table border="1">
  <tr>
    <th>count</th>
    <th>username</th>
    <th>age</th>
    <th>etc</th>
  </tr>
  <tr th:each="user, userStat : ${users}">
    <td th:text="${userStat.count}">username</td>
    <td th:text="${user.username}">username</td>
    <td th:text="${user.age}">0</td>
    <td>
      index = <span th:text="${userStat.index}"></span>
      count = <span th:text="${userStat.count}"></span>
      size = <span th:text="${userStat.size}"></span>
      even? = <span th:text="${userStat.even}"></span>
      odd? = <span th:text="${userStat.odd}"></span>
      first? = <span th:text="${userStat.first}"></span>
      last? = <span th:text="${userStat.last}"></span>
      current = <span th:text="${userStat.current}"></span>
    </td>
  </tr>
</table>
</body>
</html>
```

#### **반복 기능**

**`<tr th:each="user : ${users}">`**

- 반복시 오른쪽 컬렉션( ${users} )의 값을 하나씩 꺼내서 왼쪽 변수( user )에 담아서 태그를 반복실행합니다.
- th:each 는 List 뿐만 아니라 배열, java.util.Iterable , java.util.Enumeration 을 구현한 모든객체를 반복에 사용할 수 있습니다. Map 도 사용할 수 있는데 이 경우 변수에 담기는 값은 Map.Entry입니다.

#### **반복 상태 유지**

**`<tr th:each="user, userStat : ${users}">`**

- 반복의 두번째 파라미터를 설정해서 반복의 상태를 확인 할 수 있습니다.두번째 파라미터는 생략 가능한데, 생략하면 지정한 변수명( user ) + Stat 가 됩니다.여기서는 user + Stat = userStat 이므로 생략 가능합니다.

#### **반복 상태 유지 기능**

- **index** : 0부터 시작하는 값
- **count** : 1부터 시작하는 값
- **size** : 전체 사이즈
- **even** , **odd** : 홀수, 짝수 여부( boolean )
- **first** , **last** :처음, 마지막 여부( boolean )
- **current** : 현재 객체

------

### **주석**

------

### **블록**

- 블록은 일반적으로 `th:each `로 해결하기 어려운 반복문을 처리하기 위해 사용된다. th:blcok은 유일하게 HTML태그가 아닌 타임리프의 자체태그이다.

### **사용예**

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
  <meta charset="UTF-8">
  <title>Title</title>
</head>
<body>
<th:block th:each="user : ${users}">
  <div>
    사용자 이름1 <span th:text="${user.username}"></span>
    사용자 나이1 <span th:text="${user.age}"></span>
  </div>
  <div>
    요약 <span th:text="${user.username} + ' / ' + ${user.age}"></span>
  </div>
</th:block>
</body>
</html>
```

타임리프의 특성상 HTML 태그안에 속성으로 기능을 정의해서 사용하는데, 위 예처럼 이렇게 사용하기 애매한 경우에 사용하면 된다. **`th:block`** 은 렌더링시 제거된다.

------

### **자바스크립트 인라인**

------

### **템플릿 조각**

웹 페이지를 개발할 때는 공통 영역이 많다. 예를들어 상당 영역이나 하단 영역, 좌측 카테고리 등등 여러페이지에서 함께 사용하는 특정 영역들이 존재하게 된다. 이런 부분을 코드를 복사해서 사용한다면 변경시 여러페이지를 다 수정해야 하므로 상당히 비효율 적이다. 이런문제를 해결하기 위해 타임리프에서는 템플릿 조각과 레이아웃 기능을 지원한다.

#### **footer.html**

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<body>

<footer th:fragment="copy">
    푸터 자리 입니다.
</footer>

<footer th:fragment="copyParam (param1, param2)">
    <p>파라미터 자리 입니다.</p>
    <p th:text="${param1}"></p>
    <p th:text="${param2}"></p>
</footer>

</body>
</html>
```

공통적으로 사용될 코드가 들어있는 footer.html파일 , `th:fragment` 태그를 사용해서 공통적으로 사용할 코드 조각을 만들 수 있다.

#### **frgamentMain.html**

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
  <meta charset="UTF-8">
  <title>Title</title>
</head>
<body>
<h1>부분 포함</h1>
<h2>부분 포함 insert</h2>
<div th:insert="~{template/fragment/footer :: copy}"></div>

<h2>부분 포함 replace</h2>
<div th:replace="~{template/fragment/footer :: copy}"></div>

<h2>부분 포함 단순 표현식</h2>
<div th:replace="template/fragment/footer :: copy"></div>

<h1>파라미터 사용</h1>
<div th:replace="~{template/fragment/footer :: copyParam ('데이터1', '데이터2')}"></div>

</body>
</html>
```

조각을 사용하기 위해서는 `th:inser` 나 `th:replace` 태그 중 하나를 사용하고 `~{...}` 로 감싸주어야 한다. 

`th:insert` : 기존 태그의 사이에 코드조각을 삽입한다.

`th:replace` : 기존 태그를 삭제하고 코드 조각이 대체한다. `~{...}`  는 원칙적으로는 사용해야 하지만 생략 가능하다.

파라미터를 전달해서 동적으로 조각을 렌더링 하는것도 가능하다.

```html
<h1>파라미터 사용</h1>
<footer>
<p>파라미터 자리 입니다.</p>
<p>데이터1</p>
<p>데이터2</p>
</footer>
```

------

### **템플릿 레이아웃**

이전에는 일부 코드 조각을 가지고와서 사용햇다면, 이번에는 코드 조각을 레이아웃에 넘겨서 사용하는 방법에 대해서 공부해보자.

**레이아웃? :** 

이전 코드에서는 한 태그만을 카피해서 사용해갔다면 템플릿 레이아웃을 이용하면 헤더같이 공통내용이 들어가있는 부분을 그대로 가져다 쓰는것이 가능해진다. 그 안에서 베이스 코드를 가져다 쓸 레이아웃에서 본인만의 내용을 넣고 싶다면 파라미터에 담아서 넘겨주는것 또한 가능하다.

#### **헤더를 가져다가 쓸 HTML**

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head th:replace="template/layout/base :: common_header(~{::title},~{::link})">
  <title>메인 타이틀</title>
  <link rel="stylesheet" th:href="@{/css/bootstrap.min.css}">
  <link rel="stylesheet" th:href="@{/themes/smoothness/jquery-ui.css}">
</head>
<body>
메인 컨텐츠
</body>
</html>
```

**베이스 HTML**

```html
<html xmlns:th="http://www.thymeleaf.org">
<head th:fragment="common_header(title,links)">
  <title th:replace="${title}">레이아웃 타이틀</title>

  <!-- 공통 -->
  <link rel="stylesheet" type="text/css" media="all" th:href="@{/css/awesomeapp.css}">
  <link rel="shortcut icon" th:href="@{/images/favicon.ico}">
  <script type="text/javascript" th:src="@{/sh/scripts/codebase.js}"></script>

  <!-- 추가 -->
  <th:block th:replace="${links}" />
</head>
```

#### **결과**

```html
<!DOCTYPE html>
<html>
<head>
<title>메인 타이틀</title>
<!-- 공통 -->
<link rel="stylesheet" type="text/css" media="all" href="/css/awesomeapp.css">
<link rel="shortcut icon" href="/images/favicon.ico">
<script type="text/javascript" src="/sh/scripts/codebase.js"></script>
<!-- 추가 -->
<link rel="stylesheet" href="/css/bootstrap.min.css">
<link rel="stylesheet" href="/themes/smoothness/jquery-ui.css">
</head>
<body>
메인 컨텐츠
</body>
</html>
```

#### **레이아웃은 왜 쓸까???**

- 비슷한 배치에 특정부분만 내용이 다른 홈페이지가 여러개 만들어야 하는 상황이면 템플릿 레이아웃을 사용하는게 편하다.

이 템플릿 레이아웃은 이전의 `<head>` 태그 정도가 아니라 html전체에 적용할 수도 있다.

#### **템플릿 레이아웃을 사용할 HTML파일**

```html
<!DOCTYPE html>
<html th:fragment="layout (title, content)" xmlns:th="http://
www.thymeleaf.org">
<head>
  <title th:replace="${title}">레이아웃 타이틀</title>
</head>
<body>
<h1>레이아웃 H1</h1>
<div th:replace="${content}">
  <p>레이아웃 컨텐츠</p>
</div>
<footer>
  레이아웃 푸터
</footer>
</body>
</html>
```

#### **카피할 뼈대가 되는 HTML**

```html
<!DOCTYPE html>
<html th:replace="~{template/layoutExtend/layoutFile :: layout(~{::title},~{::section})}"
      xmlns:th="http://www.thymeleaf.org">
<head>
  <title>메인 페이지 타이틀</title>
</head>
<body>
<section>
  <p>메인 페이지 컨텐츠</p>
  <div>메인 페이지 포함 내용</div>
</section>
</body>
</html>
```
