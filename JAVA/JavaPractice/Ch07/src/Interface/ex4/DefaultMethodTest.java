package Interface.ex4;

public class DefaultMethodTest {
    public static void main(String[] args) {
        Child c = new Child();
        c.method1();
        c.method2();
        c.method3();
    }
}

class Child extends Parent implements MyInterface1,MyInterface2{
    //디폴트 메서드 이름이 중복되는경우 오버라이딩이 필요
    public void method1(){
        System.out.println("method1 in child class");
    }
}

class Parent {
    //디폴트 메서드의 경우 상위 클래스의 메서드가 우선
    public void method2(){
        System.out.println("method2 in Parent");
    }
}
interface MyInterface1{
    default void method1(){
        System.out.println("method1 in myinterface1");
    }
    default void method2(){
        System.out.println("method2 in myinterface1");
    }

}
interface MyInterface2{
    //중복이 아니라면 Child클래스의 인스턴스 생성을 통해 디폴트 메서드 사용가능.
    default void method3(){
        System.out.println("method1 in myinterface2");
    }
    default void method2(){
        System.out.println("method2 in myinterface2");
    }
}
