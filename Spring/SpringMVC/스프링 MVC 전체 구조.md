### **스프링 MVC 전체 구조**

지금까지는 스프링MVC가 발전해왔던 발자취를 따라가서 v1부터 v5까지 작성해보았다. 지금까지 작성한 구조와 실제 Spring MVC구조를 비교해보자. 

#### **지금까지 만든 스프링 MVC구조**

![20221124_134646](https://user-images.githubusercontent.com/41957723/216572520-8941a1ce-9250-42ef-94fb-063a08eb6a84.png)


#### **실제 Spring MVC구조**

![20221124_134719](https://user-images.githubusercontent.com/41957723/216572569-60ba6b69-3a27-44bb-9eb1-8a164f0b364c.png)

#### 직접 만든 프레임워크 스프링 MVC 비교

- FrontController DispatcherServlet

- handlerMappingMap HandlerMapping

- MyHandlerAdapter HandlerAdapter

- ModelView ModelAndView

- viewResolver ViewResolver

- MyView View

  

### **DispatcherServlet구조 살펴보기**



### **Spring MVC동작 순서**

1. **핸들러** **조회** : 핸들러 매핑을 통해 요청 URL에 매핑된 핸들러(컨트롤러)를 조회한다.
2. **핸들러 어댑터 조회** : 핸들러를 실행할 수 있는 어댑터를 조회한다.
3. **핸들러 어댑터 실행** : 핸들러 어댑터를 실행한다.
4. **핸들러 실행** : 핸들러 어댑터가 실제 핸들러를 실행시켜준다.
5. **ModelAndView반환** : 핸들러 어댑터는 핸들러가 반환하는 정보를 ModelAndView로 변환해서 반환해준다.
6. **viewResolver호출** : 뷰 리졸버를 찾고 실행한다.
7. **View의** **반환** : 뷰 리졸버는 뷰의 논리이름을 물리 이름으로 바꾸고 렌더링 역할을 담당하는 뷰 객체를 반환한다. JSP의 경우 내부에 forward()로직이 있다.
8. **뷰 렌더링** : 뷰를 통해서 뷰를 렌더링해서 고객에게 Html이 전달된다.

우리가 해야할 일은 DispatcherServlet의 코드변경을 할 필요는 없고 확장가능하게 만들어준 인터페이스들을 사용해서 구현하고 DispatcherServlet에 등록해주기만 하면 된다!

