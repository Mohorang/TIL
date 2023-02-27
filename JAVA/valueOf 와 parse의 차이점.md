### **valueOf 와 parse의 차이점**

자바책을보며 공부하다가 String을 기본타입으로 변경하는 내용을 공부하는데 궁금한 사항이 생겨서 공부하다가 정리하게 되었다.

String을 기본타입 , 예를들어  int로 변경한다고 할 때 책에는 방법으로 **타입.valueof**()와 **타입.parseInt()**두가지가 있었는데 둘의 차이를 모르겟었다.

```java
public class StringEx {
    public static void main(String[] args) {
        String s = "1000";

        int a = Integer.parseInt(s);
        int b = Integer.valueOf(s);
        System.out.println("a = " + a);
        System.out.println("b = " + b);
    }
}
```

결과

```
a = 1000
b = 1000
```

결과는 같은값을 리턴해주고 있었다.

결론은 정말 간단한 내용인데 각 메서드를 따라가 보니 다음과 같았다.

#### **valueOf**

```java
public static Integer valueOf(String s) throws NumberFormatException {
    return Integer.valueOf(parseInt(s, 10));
}
```

#### **parseInt**

```java
public static int parseInt(String s) throws NumberFormatException {
    return parseInt(s,10);
}
```

valueOf 는 Integer타입을 리턴해주고 parseInt의 경우 기본형인 int를 리턴해주고 있었다.

코드를 쳐볼때 Intellij에서 변수 b에 valueOf를 사용햇을때 경고메시지가 뜬 걸 보고 눈치챗어야 하는데 결국 오토박싱을 해주는거였다.