package Interface.ex2;

public class RepariableTest {
    public static void main(String[] args) {
        Tank t = new Tank();
        DropShip d = new DropShip();
        Marine marine = new Marine();
        SCV scv = new SCV();

        scv.repair(d);
        scv.repair(t);
        //scv.repair(marine); 컴파일 에러 발생
    }
}
interface Repariable{

}
class Unit{
    int hitPoint;
    final int MAX_HP;
    Unit(int MAX_HP) {
        this.MAX_HP = MAX_HP;
    }
}

class GroundUnit extends Unit{
    GroundUnit(int hp){
        super(hp);
    }
}
class AirUnit extends Unit{
    AirUnit(int hp){
        super(hp);
    }
}

class Tank extends GroundUnit implements Repariable{
    Tank(){
        super(150);
        hitPoint = MAX_HP;
    }

    @Override
    public String toString() {
        return "Tank";
    }
}
class DropShip extends AirUnit implements Repariable{
    DropShip(){
        super(125);
        hitPoint = MAX_HP;
    }

    @Override
    public String toString(){
        return "DropShip";
    }
}
class Marine extends GroundUnit{
    Marine(){
        super(40);
        hitPoint = MAX_HP;
    }
}
class SCV extends GroundUnit implements Repariable{
    SCV(){
        super(60);
        hitPoint = MAX_HP;
    }
    void repair(Repariable r){
        if(r instanceof Unit){
            Unit u = (Unit) r;
            while(u.hitPoint != u.MAX_HP){
                u.hitPoint++;
            }
            System.out.println(u.toString() + " 의 수리가 끝낫습니다.");
        }
    }
}
