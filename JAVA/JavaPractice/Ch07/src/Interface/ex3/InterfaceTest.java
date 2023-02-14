package Interface.ex3;

public class InterfaceTest {
    public static void main(String[] args) {
        A a = new A();
        a.methodA();
    }
}
class A{
    void methodA(){
        I i = InstanceManager.getInstance();
        i.methodB();
        System.out.println(i.toString());
    }
}
interface I {
    void methodB();
}
class B implements  I{
    @Override
    public void methodB() {

    }

    public String toString(){ return "class B";
    }
}
class InstanceManager{
    public static I getInstance(){
        return new B();
    }
}