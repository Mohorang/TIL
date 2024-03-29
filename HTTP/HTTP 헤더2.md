### **캐시 기본동작**

예를들어 어떤 이미지를 받았을때 캐시가 없다면 두번째 요청할 때 똑같은 데이터를 다시 요청해서 네트워크를 통한 다운로드가 이뤄져야한다. 이처럼 캐시가 없으면 계속해서 네트워크를 사용해야 하며 로딩속도가 느려지고 사용자가 사용하는 **인터넷 경험이 느려진다.** 
캐시를 적용하게 되면 첫번째 요청해서 넘겨받은 데이터가 캐시의 유효시간동안은 메모리에 남아있어 다음 요청때 로컬에 있는 캐시를 사용해서 **네트워크를 사용하지않고** 데이터를 불러올 수 있다.

------



### **캐시적용** 이점

- 캐시덕분에 캐시 가능한 시간동안 비싼 네트워크를 사용하지 않아도 된다.
- cahce-control이 응답 헤더에 담겨서 오는데 여기에 적힌 시간동안은 캐시가 유효하다는걸 알게 해준다.
- 비싼 네트워크 사용량을 줄일 수 있다.
- 캐시를 사용하면 메모리에서 바로 불러오기 떄문에 브라우저 로딩속도가 매우 빠르다.



### **캐시시간 초과**

------



- **캐시 시간(cache-control)**이 초과되면 서버를 통해 다시 캐시 조회하고 데이터를 받아서 기존 캐시결과를 지우고 덮어씌우는 즉 갱신되는 과정이 이뤄지게 된다. 
- 이때 다시 네트워크 다운로드가 발생한다.



캐시 유효시간이 초과해서 서버에 다시 요청하면 **두가지 상황**이 나타난다.

1. 서버에서 기존데이터를 변경한경우
2. 기존 데이터가 변경되지 않은 경우

#### **클라이언트에 있는 캐시데이터와 서버에 있는 캐시데이터가 안바뀌었다는것을 확인할 수 있는 방법이 필요하다!**

#### **검증헤더** 

1. 처음 데이터를 요청할 때 서버에서 헤더에 Last-Modified 라는 이름으로 최종수정일을 넘겨준다.
2. 캐시 데이터에 만료시간 이외에도 데이터 최종수정일을 기록한다.
3. 다음 요청때  if-modified-since라는 이름으로 request헤더에 최종수정일을 붙혀서 서버에 request하게된다.
4. 서버는 요청을 받으면 클라이언트로부터 받은 데이터 최종 수정일을 확인해서 일치하면 서버가 검증을한다.
5. 데이터가 이전에 내려준것과 바뀌지 않았다면 **304 Not Modified**라고 내보내고 caach-control과Last-Modified를 그대로 내려준다.
6. 중요한것은 Body가 없다!
7. 따라서 HTTP헤더만 내려주므로 네트워크 부하가 압도적으로 줄어들게 된다.

### **검증헤더와 조건부요청**

- 캐시 유효시간이 초과해도, 서버의 데이터가 갱신되지 않았다면
- 304 Not Modified + 헤더 메타 정보로만 응답해준다 (바디x)
- 클라이언트는 서버가 보낸 응답 헤더 정보로 캐시의 메타 정보를 갱신한다.
- 클라이언트는 캐시에 저장되어 있는 데이터를 재활용한다.
- 결과적으로 네트워크 다운로드가 발생하지만 용량이 적은 헤더정보만 다운로드한다.
- 따라서 매우 실용적인 해결책이라고 할 수 있다.

웹 브라우저들은 위 메커니즘을 따르고 있다.

------

### **Last-Modified, If-Modified-Since 단점**

- 1초 미만(0.x초) 단위로 캐시 조정이 불가능
- 날짜 기반의 로직 사용
- 데이터를 수정해서 날짜가 다르지만, 같은 데이터를 수정해서 데이터 결과가 똑같은 경우
- 서버에서 별도의 캐시 로직을 관리하고 싶은 경우
- 예) 스페이스나 주석처럼 크게 영향이 없는 변경에서 캐시를 유지하고 싶은 경우



### **ETag, If-None-Match**

- ETag(Entity Tag)
- 캐시용 데이터에 임의의 고유한 버전 이름을 달아둠
  - 예) ETag: "v1.0", ETag: "a2jiodwjekjl3"
- 데이터가 변경되면 이 이름을 바꾸어서 변경함(Hash를 다시 생성)
  - 예) ETag: "aaaaa" -> ETag: "bbbbb"
- 진짜 단순하게 ETag만 보내서 같으면 유지, 다르면 다시 받기!
![20221113_201406](https://user-images.githubusercontent.com/41957723/201519896-53572667-de5e-41ec-a356-9a73c6c4c529.png)

1. 첫 요청때 서버는 ETag라는 이름의 헤더를 클라이언트에 넘겨주면 클라이언트는 이를 캐시에 저장.
2. 캐시시간 초과 후 요청때에는 request헤더에 if-None-Match라는 이름으로 ETag의 내용을 넣어서 서버에 전달.
   ![20221113_201558](https://user-images.githubusercontent.com/41957723/201520380-72943d92-8cb1-4f99-8293-7f45f7b8cf87.png)
3. 일치시에는 기존 검증헤더 **304Not Modified**와 함께 **HTTP Header**만 전송 , 바디전송x


### **프록시 캐시**

예를들어 미국에 있는 원 서버(Origin)에 웹브라우저에서 요청을 한다면 0.5초가 걸린다고 하자, 한국에 프록시 캐시 서버를 넣어놓고 DNS요청이 왔을때 바로 미국에 원서버로 가는것이 아니라 프록시 캐시 서버를 거치도록 하는것이다. 이 때 한국의 서버가 **프록시 캐시 서버**이다.

#### **Private캐시 , Public 캐시**

- **private 캐시** : 웹브라우저 내에서 로컬에 저장되서 사용되는 전용 캐시.
- **public캐시** : 중간에서 공용으로 사용되는 캐시 (프록시 캐시)

#### Cache-Control : 캐시 지시어(directives) - 기타****

- **Cache-Control: public**
  - 응답이 public 캐시에 저장되어도 됨
- Cache-Control: private
  - 응답이 해당 사용자만을 위한 것임, private 캐시에 저장해야 함(기본값)
- Cache-Control: s-maxage
  - 프록시 캐시에만 적용되는 max-age
- Age: 60 (HTTP 헤더)
  - 오리진 서버에서 응답 후 프록시 캐시 내에 머문 시간(초)

------

### **캐시 무효화**

#### **Cache-Control : 확실한 캐시 무효화 응답**

캐시를 적용안해도 GET요청의 경우 임의로 캐싱을 해버릴 수 있다. 그래서 확실하게 캐시를 사용하지 않게 하기 위해서 적용시켜줘야하는 지시어들이 있다.

- **Cache-Control : no-cache, no-store, must-revalidate**
- **Pragma : no-chace , HTTP 1.0호환**



#### **Cache-Control : 캐시 지시어(directives)** - 확실한 캐시 무효화

- **Cache-Control : no-cahce**
  - 데이터는 캐시해도 되지만 , 항상 **원 서버에 검증**하고 사용
- **Cache-Control : no-store**
  - 데이터에 민감한 정보가 있으므로 저장하면 안됨(메모리에서만 사용하고 빠르게 삭제)
- **Cache-Control: must-revalidate**
  - 캐시 만료후 최초 조회시 원 서버에 검증해야함
  - 원 서버 접근 실패시 반드시 오류가 발생해야함 **504(Gateway Timeout)** no-cahce와 함께 must-revalidate를 사용하는 이유. 이거중요
  - must-revalidate는 캐시 유효 시간이라면 캐시를 사용함
- **Pragma: no-cahce**
  - HTTP 1.0하위 호환
