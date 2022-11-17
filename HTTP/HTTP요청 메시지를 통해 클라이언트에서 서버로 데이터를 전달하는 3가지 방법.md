### **HTTP요청 메시지를 통해 클라이언트에서 서버로 데이터를 전달하는 3가지 방법.**



1. **GET 쿼리 파라미터**
   - /url?username=hello&age=20
   - 메시지 바디 없이, URL의 쿼리 파라미터에 데이터를 포함해서 전달
   - 검색,필터,페이징 등에서 많이 사용되는 방식
2. **POST - HTML Form**
   - content-type = application/x-www-form-urlencoded
   - 메시지 바디에 쿼리 파라미터 형식으로 전달 , username=hello&age=20
   - 회원가입,상품주문 등의 HTML  Form에 사용
3. **HTTP message body에 데이터를 직접 담아서 사용**
   - HTTP API에서 주로 사용하는 방법, Json,XML,TEXT
   - 데이터 형식은 주로 Json을 가장 많이 사용
   - POST,PUT,PATCH