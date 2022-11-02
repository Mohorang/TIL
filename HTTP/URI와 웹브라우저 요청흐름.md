# URI란 무엇인가?

------

### URI(Uniform Resource Identify)

URI? URL? URN? 세가지의 차이는 무엇일까??

URI는 로케이터(Locator), 이름(name) 또는 둘다 추가로 분류될 수 있다.

![](C:\Users\dlwns\OneDrive\바탕 화면\인프런강의캡쳐\20221101_120942.png)

로케이터 : URI라는 가장 큰 개념 , 리소스를 식별한다. 자원이 어디에 있는지 식별하는 방법에는 크게 2가지가 존재하는데 흔하게 쓰이는 URL(Resource Locator)과 URN이 있다.



### URI 단어뜻

**U**niform : 리소스 식별하는 통일된 방식

**R**esource : 자원, URI로 식별할 수 있는 모~든 것(제한 없음) 우리가 구분할 수 있는 모든것

**I**dentifier : 다른항목과 구분하는데 필요한 정보

------

URL -> 리소스가 있는 위치를 지정

URN -> 리소스에 이름을 부여

위치는 변할 수 있지만 이름은 변하지 않는다.

URN의 이름만으로는 실제 리소스를 찾을 수 있는 방법이 보편화 되지 않음.

URI와 URL을 같은 의미로 이야기할 수 있다.

------



### URL 전체문법

• scheme://[userinfo@]host[:port][/path][?query][#fragment]
• https://www.google.com:443/search?q=hello&hl=ko
위와같은 URL이 있다고 하자.

우선 스키마 , 스키마에는 주로 프로토콜이 사용된다. 
프로토콜: 어떤 방식으로 자원에 접근할 것인가 하는 약속 규칙
• 예) http, https, ftp 등등
• http는 80 포트, https는 443 포트를 주로 사용, 포트는 생략 가능 https는 http에 강력한 보안이 적용된 방식
• https는 http에 보안 추가 (HTTP Secure)

------

### Host

도메인명이나 IP주소를 직접 입력할 수 있다.



Port는 생략가능하며 일반적으로 http는 80 https 는 443

------

### Path 

리소스의 경로(path), 계층적 구조

예)
• /home/file1.jpg
• /members
• /members/100, /items/iphone12

------

### Query

• key=value 형태
• ?로 시작, &로 추가 가능 ?keyA=valueA&keyB=valueB
• query parameter, query string 등으로 불림, 웹서버에 제공하는 파라미터, 문자 형태