package Ch09.ObjectClass;



/**
 * 출력
 * x=3, y=5
 * x=3, y=5
 */
class Point implements Cloneable{ //clone메서드를 사용하기 위해서는 복제를 허용하는 cloneable인터페이스 구현해야한다.
    int x,y;
    Point(int x,int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString(){
        return "x=" + x + ", y=" + y;
    }

    public void setX(int x) {
        this.x = x;
    }

    //보통 clone메서드는 작업에 실패해서 원래 상태로 되돌리거나 변경후의 값을 참고하는데 사용된다.
    //protected --> public 으로 접근제어자 변경해야 상속관계가 없는 다른 클래스에서 접근가능
    @Override
    public Object clone() {
        Object obj = null;


        try{
            obj = super.clone(); //clone메서드는 반드시 예외처리를 해주어야한다.
        }catch (CloneNotSupportedException e){
            return null;
        }
        return obj;
    }
}
public class CloneEx1 {
    public static void main(String[] args) {
        Point original = new Point(3,5);
        Point copy = (Point)original.clone();
        System.out.println(original);
        System.out.println(copy);
    }
}
