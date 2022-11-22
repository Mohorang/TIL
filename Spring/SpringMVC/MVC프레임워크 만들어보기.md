## **MVC프레임 워크 직접 만들어보기**

처음 MVC프레임 워크를 접햇을때 왜 이런 구조로 설계되었는지 이해를 못하는데, 프론트 컨트롤러의 도입부터 직접 구현해봄으로 써 개발자로서의 역량을 키우고 MVC에 대한 이해를 높히는 것을 목표로 한다.



### **프론트 컨트롤러 도입 - v1**

![20221122_115831](C:\Users\dlwns\OneDrive\바탕 화면\인프런강의캡쳐\20221122_115831.png)

클라이언트로부터 요청이 들어오면 프론트 컨트롤러에서 URL매핑 정보를 통해 컨트롤러를 조회, 컨트롤러를 호출 후 컨트롤러는 JSP  forward를 실시.

서블릿과 비슷한 모양의 컨트롤러 인터페이스를 도입한다. 각 컨트롤러들은 이 인터페이스를 구현하면된다. 프론트 컨트롤러는 이 인터페이스를 호출해서 구현과 관계없이 로직의 일관성을 가져갈 수 있다. **<---- 좀더 이해할 수 있게 알아보기**(다형성 관련)



### **FrontControllerV1**

```java
@WebServlet(name = "frontControllerServletV1",urlPatterns = "/front-controller/v1/*")
public class FrontControllerServletV1 extends HttpServlet {
    //매핑정보 만들기
    private Map<String, ControllerV1> controllerMap = new HashMap<>();

    public FrontControllerServletV1() {
        controllerMap.put("/front-controller/v1/members/new-form", new MemberFormControllerV1());
        controllerMap.put("/front-controller/v1/members/save", new MemberSaveControllerV1());
        controllerMap.put("/front-controller/v1/members", new MemberListControllerV1());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV1.service");
        String requestURI = request.getRequestURI();

        ControllerV1 controller = controllerMap.get(requestURI);
        //페이지가 존재하지 않을때 404리턴
        if(controller ==null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        //문제가 없을 때
        controller.process(request, response);
    }
}
```

------



### **View 분리 - V2**

V1에서의 문제점 : 예제로 만든 모든 컨트롤러에서 뷰로 이동하는 부분에 중복이 있고 , 깔끔하지가 않았다.

```java
String viewPath = "/WEB-INF/views/new-form.jsp";
RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
dispatcher.forward(request, response);
```

이 부분을 깔끔하게 분리하기 위한 별도의 뷰를 처리하는 객체를 만들어보자.

V1에서의 컨트롤러가 직접 포워딩하는 구조에서 MyView라는 객체를 반환해주도록 만들어본다.

![20221122_130548](C:\Users\dlwns\OneDrive\바탕 화면\인프런강의캡쳐\20221122_130548.png)

### **MyView클래스 생성하기**

```java
public class MyView {
    private String viewPath;

    public MyView(String viewPath) {
        this.viewPath = viewPath;
    }

    public void render(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request,response);
    }
}
```

### **V1과의 차이점**

- 기존 컨트롤러들의 코드는 그대로 유지한 채로 ControllerV2의 인터페이스를 void에서 MyView객체를 리턴하도록 변경.

- 기존V1에서의 컨트롤러 코드에서 바로 포워딩을 진행하는것이 아닌 프론트컨트롤러에 MyView객체를 리턴하도록 변경하였다.
- ControllerV2의 process를 implements한 컨트롤러들의 리턴으로 MyView를 리턴받으면 MyView 클래스의 render를 호출해서 기존 컨트롤러들이 담당하던 포워딩 동작을 프론트컨트롤러 내에서 일관되게 실행.

### **순서정리**

1. 웹 클라이언트에서 HTTP를 요청
2. 프론트 컨트롤러에서 URL 매핑 정보를 컨트롤러에서 조회
3. 컨트롤러를 호출하고 컨트롤러는 MyView를 반환
4. 반환된 MyView객체로 render메소드를 실행
5. V1과 동일하게 포워딩 실시.

프론트 컨트롤러의 도입으로 MyView객체의 render()를 일관되게 처리할 수 있다. 각각의 컨트롤러들은 MyView만 생성해서 반환하기만 하면 된다.

------

### **Model추가 - V3**

- **서블릿 종속성 제거**

- **뷰 경로 중복 제거** : 가장 큰 이점은 향후 뷰의 폴더 위치가 함께 이동해도 **프론트 컨트롤러에서만 고치면** 되는점이 가장 큰 이점으로 보인다. (**ViewResolver**의 추가)

![20221122_145331](C:\Users\dlwns\OneDrive\바탕 화면\인프런강의캡쳐\20221122_145331.png)

#### **V3에서 새로 생긴 ModelView**

```java
public class ModelView {
    private String viewName;
    private Map<String , Object> model = new HashMap<>();

    public ModelView(String viewName) {
        this.viewName = viewName;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public void setModel(Map<String, Object> model) {
        this.model = model;
    }
}
```

ModelView는 논리경로(공통된 경로 + .jsp 확장자명)와 MyView의 render에서 setAttribute에 사용하기 위한 model객체까지 담겨있는 클래스이다.

새롭게 ModelView가 사용되었기 때문에 FrontController또한 변경되었다.

### **FrontControllerV3**

```java
@WebServlet(name = "frontControllerServletV3",urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {
    //매핑정보 만들기
    private Map<String, ControllerV3> controllerMap = new HashMap<>();

    public FrontControllerServletV3() {
        controllerMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        controllerMap.put("/front-controller/v3/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        ControllerV3 controller = controllerMap.get(requestURI);
        //페이지가 존재하지 않을때 404리턴
        if(controller == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        //v2
//        MyView view = controller.process(request, response);
//        view.render(request,response);

        //paramMap
        Map<String, String> paramMap = createParamMap(request);
        ModelView mv = controller.process(paramMap);

        String viewName = mv.getViewName();
        MyView view = viewResolver(viewName);

        view.render(mv.getModel(),request,response);
    }

    private static MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    private static Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String,String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
}
```

Map<String,String> paramMap에 클라이언트로부터의 요청에 들어있는 파라미터들을 전부 받아와서 저장하고 ModelView 객체를 반환받는다, 그 후 viewResolver를 이용해 논리경로와 공통경로를 포함한 확장자명까지 함께 저장하여 후에 render하기 위한 최종경로를 만들어내고 최종적으로 **view.render(mv.getModel(),request,response)**를 호출함으로써 mv.getModel에는 process를 호출함으로써 생긴 **Model**데이터가 담겨있고 이를 render에서 활용하여 뷰를 호출할때 데이터를 request에 셋팅하여 같이 넘겨주게 된다.

### **새롭게 추가된 MyView의 메서드**

```java
public void render(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    //jsp에서 request의 파라미터들을 이용할 수 있게끔 model의 데이터를 셋팅하는함수
    modelToRequestAttribute(model, request);
    RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
    dispatcher.forward(request,response);
}
```

------

### **단순하고 실용적인 컨트롤러 -v4**

프론트컨트롤러가 도입된 v1, 컨트롤러에서 바로 포워딩 하던 방식에서 뷰를 분리한 v2, 서블릿 종속성을 제거한 v3까지 잘 설계된 컨트롤러라고 할 수 있다. 그렇지만 실제 인터페이스를 구현하는 개발자 입장에서는 항상 ModelView객체를 생성하고 반환해야 하는 부분이 아직은 번거롭다고 할 수 있다. 좋은 프레임워크는 아키텍쳐도 물론 중요하지만, 그와 더불어 **실제 개발하는 개발자가 단순하고 편리하게 사용**할 수 있어야 하는점이 중요하다고 한다.

![20221122_171553](C:\Users\dlwns\OneDrive\바탕 화면\인프런강의캡쳐\20221122_171553.png)

**V3와 다른점은 Controller가 ModelView가 아닌 ViewName을 반환한다는 점이다.**

### **FrontControllerV4**

```java
@WebServlet(name = "frontControllerServletV4",urlPatterns = "/front-controller/v4/*")
public class FrontControllerServletV4 extends HttpServlet {
    //매핑정보 만들기
    private Map<String, ControllerV4> controllerMap = new HashMap<>();

    public FrontControllerServletV4() {
        controllerMap.put("/front-controller/v4/members/new-form", new MemberFormControllerV4());
        controllerMap.put("/front-controller/v4/members/save", new MemberSaveControllerV4());
        controllerMap.put("/front-controller/v4/members", new MemberListControllerV4());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        ControllerV4 controller = controllerMap.get(requestURI);
        //페이지가 존재하지 않을때 404리턴
        if(controller == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        
        //paramMap
        Map<String, String> paramMap = createParamMap(request);
        //v4에서 추가
        Map<String,Object> model = new HashMap<>();
        String viewName = controller.process(paramMap,model);

        MyView view = viewResolver(viewName);
        view.render(model,request,response);
    }

    private static MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    private static Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String,String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
}
```

v3에서 달라진 가장 큰 차이점은 v3에서는 인터페이스를 상속받은 컨트롤러들이 직접 **ModelView클래스에서** model을 생성해서 **ModelView를** 리턴해줫다면 v4에서는 **FrontController에서** Model객체를 넘겨줌으로서 컨트롤러는 String으로된 **ViewName**만 리턴해주면 되게끔 간편하게 변경되어 개발자가 다루기 편해졋다는 점이다.
