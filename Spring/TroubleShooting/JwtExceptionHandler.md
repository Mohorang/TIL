## 1️⃣문제상황

: 발급해준 jwt토큰이 만료시 이를 체크하기 위한 예외처리가 들어있지 않아서 사용하는 유저가 서비스 이용에 불편이 생겨 토큰이 만료되었을때의 처리를 추가해줄 필요가 있었다. 

## 2️⃣문제원인

시큐리티에서 발생하는 예외는 DispatcherServlet에도 전달되지 않기때문에 따로 예외핸들러를 설정해줘야했다. 또한 현재 코드에는 Exception을 던지기만 할 뿐 따로 처리과정이 들어있지 않아 있는것이 원인이였다. 이를 Filter단에서 예외처리를 해줘야한다.

## 3️⃣해결과정

**Filter**단에서 예외를 처리하는 **AuthenticationEntryPoint** 작성

```java
@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        String exception = (String) request.getAttribute("exception");

        if(exception.equals(ErrorCode.EXPIRED_TOKEN.getMessage())){
            setResponse(response,ErrorCode.EXPIRED_TOKEN);
        }
    }
    //한글 출력을 위해 getWriter() 사용
    private void setResponse(HttpServletResponse response, ErrorCode exceptionCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        JSONObject responseJson = new JSONObject();
        responseJson.put("message", exceptionCode.getMessage());
        responseJson.put("code", exceptionCode.getHttpStatus().value());

        response.getWriter().print(responseJson);
    }

}
```

```java
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtProvider provider;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 헤더에서 JWT 를 받아옵니다.
        String token = provider.resolveToken((HttpServletRequest) request);
        // 유효한 토큰인지 확인합니다.
        try {
            if (StringUtils.hasText(token) && provider.validateToken(token)) {
                // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.
                Authentication authentication = provider.getAuthentication(token);
                // SecurityContext 에 Authentication 객체를 저장합니다.
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        catch(ExpiredJwtException e){
            request.setAttribute("exception", ErrorCode.EXPIRED_TOKEN.getMessage());
            System.out.println("request.getAttribute(\"exception\") = " + request.getAttribute("exception"));
        }
        
        chain.doFilter(request, response);
    }


}
```

위의 코드에서 토큰을 provider의 validateToken에서 검사하여 문제가 있다면 예외가 발생하는데 이때발생하는 예외에 대하여 request 의 속성에 "exception" 값으로 넣어준다.
잘못된 토큰, 토큰 만료, 지원되지않는 토큰 등에 대한 처리를 해줍니다.

request에 "exception" 속성의 값을 넣어준 뒤 실제 AuthenticationEntryPoint에서 response에 결과를 담아 요청자에게 돌려줍니다.

```java
.and()
                .exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
```

작성한 CustomAuthenticationEntryPoint를 스프링 시큐리티의 설정에 등록해주면 됩니다.
