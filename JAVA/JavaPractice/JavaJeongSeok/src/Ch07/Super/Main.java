package Ch07.Super;

class Point{
    int x;
    int y;

    Point(){
        this(1,2);
    }
    Point(int x , int y){
        this.x = x;
        this.y = y;
    }

}
class Point3D extends Point{
    int z;

    Point3D(int z){
        //super(); 클래스가 자동 호출됨.
        this.x = 2;
        this.y = 3;
        this.z = z;
    }
}
public class Main {
    public static void main(String[] args) {
        Point3D p3 = new Point3D(3);
        System.out.println("p3.x = " + p3.x + " p3.y = " + p3.y + " p3.z = " + p3.z);
    }
}
