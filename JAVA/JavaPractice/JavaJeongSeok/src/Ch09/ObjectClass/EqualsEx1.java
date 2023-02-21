package Ch09.ObjectClass;

public class EqualsEx1 {
    public static void main(String[] args) {
        Value v1 = new Value(10);
        Value v2 = new Value(10);

        //object 클래스의 equals 메서드
        //객체 자신과 객체 obj가 같은 객체인지 알려준다. 같으면 true
        //equals 메서드는 두 대상의 참조변수값을 기준으로 비교한다.
        if(v1.equals(v2)){
            System.out.println("v1과 v2는 같습니다.");
        }
        else
            System.out.println("v1과 v2는 다릅니다.");

        //v2 에 v1의 참조변수값을 대입
        v2 = v1;

        if(v1.equals(v2)){
            System.out.println("v1과 v2는 같습니다.");
        }
        else
            System.out.println("v1과 v2는 다릅니다.");
    }
}
class Value{
    int value;
    public Value(int value){
        this.value = value;
    }
}