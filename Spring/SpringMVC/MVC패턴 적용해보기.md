### **MVC패턴 적용해보기**

------

- **모델 :**  뷰에 출력할 데이터를 담아두는 역할, 뷰가 필요한 데이터를 모두 모델에 담아서 전달해주는 덕분에 뷰는 비즈니스 로직이나 데이터 접근을 몰라도 되고, 화면을 렌더링하는 일에만 집중할 수 있게 해준다.
- **뷰** :  모델에 담겨있는 데이터를 사용해서 화면을 그리는 역할
- **컨트롤러 :** HTTP요청을 받아서 파라미터를 검증하고, 비즈니스 로직을 실행하는 역할과 비즈니스 로직의 결과로 생성된 데이터를 모델에 담아 뷰가 사용할 수 있도록 한다.



**Servlet**

```java
@WebServlet(name = "MvcMemberFormServlet",urlPatterns = "/servlet-mvc/members/new-form")
public class MvcMemberFormServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String viewPath = "/WEB-INF/view/new-form.jsp";
        //디스패쳐의 역할 컨트롤러에서 뷰로 넘어갈 때 사용
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        //실제로 넘겨주는 메소드
        dispatcher.forward(request,response);
    }
}
```

1. **dispatcher.forward()** 의 중요한점은 서버 내부에서 다시 호출이 발생한다는 점이다. 즉 웹 브라우저에 응답이 갔다가 다시 돌아오는 리다이렉트랑은 다르다.
2. WEB-INF디렉토리 안의 jsp파일은 외부에서의 호출로는 불려지지 않는다. 항상 컨트롤러를 거쳐서 포워드 하지 않으면 호출이 불가능하다 , 이것은 WAS서버의 룰이라고 한다.



#### **Redirect vs forward**

리다이렉트는 실제 클라이언트에 응답이 나갔따가 클라이언트가 redirect로 요청하는 것이고 forward의 경우 서버 내부에서 일어나는 호출이기 떄문에 클라이언트가 인지하지 못한다는 차이점이 있다.

#### **상대경로**

```html
<form action="save" method="post">
    username: <input type="text" name="username" />
    age: <input type="text" name="age" />
    <button type="submit">전송</button>
</form>
```

/로 시작하지 않는 상대경로를 사용하면 현재 url + @의 형태로 동작한다.

기존 강의에서 서블릿에 대해 배우고 후에 jsp안에 뷰를 표현하는 html코드와 렌더링할 데이터에 관한 자바코드까지 같이 들어있다보니 변경을 위한 라이프 사이클이 같이 존재하게 되는문제점과 유지보수의 어려움이 문제점으로 나온다. 그래서 등장한것이 MVC패턴, 위에서 간단하게 회원등록과 조회 관련 코드를 작성해보았는데 서블릿은 들어온 url에 대해 오롯이 jsp에게 전달해주는 컨트롤러 역할만 담당하고 jsp는 모델을 통해 데이터를 조회해서 화면을 뿌리는 역할만 담당하게 한다. 그러나 이런 방식에도 결국 문제점이 존재하는데 그 문제점으로는..



### **MVC패턴의 한계**

MVC패턴 덕분에 컨트롤러의 역할과 뷰를 렌더링 하는 역할을 구분하는 것이 가능해졋다. 특히 뷰는 화면을 그리는 역할에만 충실한 덕분에 코드가 깔끔하고 직관적이다. 단순히 모델에서 필요한 데이터만 꺼내고 화면을 만들면 된다.
그렇지만 컨트롤러의 코드를 보면 중복이 많다.

#### **포워드 중복**

view로 이동하는 코드가 항상 중복되었다. 메서드로 공통화시켜도 괜찮지만 결국 메서드도 항상 직접 호출해줘야한다.

```java
RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
dispatcher.forward(request, response);
```

#### **viewPath의 중복**

같은 패키지 내의 jsp들의 viewPath를 작성할 떄 prefix가 겹치는 문제도 있다.

#### **공통처리가 어렵다.**

기능이 복잡해질 수록 컨트롤러에서 공통으로 처리해야하는 부분이 점점 더 많아질 것이다. 단순히 공통 기능을 메서드로 뽑으면 될 것 같지만, 결과적으로 해당 메서드를 항상 호출해야하기 떄문에 결국 호출하지 않게 되면 문제가 될 것이다. 따라서 호출되기 전에 공통기능을 처리해줄 필요가 있다.

이런 문제를 해결하기 위해서 컨트롤러 호출전에 공통 기능들을 처리해 줄 소위 **수문장 **이 필요한데 **프론트 컨트롤러(Front Controller)**패턴 도입을 하여 이런 문제를 해결해 줄 수 있다.**(입구를 하나로!)**스프링 MVC의 핵심도 이 프론트 컨트롤러에 있다.
