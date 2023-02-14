package Interface.ex1;

public class FighterTest {
    public static void main(String[] args) {
        Unit f = new Fighter();
        Unit u = new Unit();

        f.Move(1,2);
        f.Attack();
        u.Move(1,2);
        u.Attack();
    }
}

class Fighter extends Unit{
    @Override
    public void Move(int x, int y) {
        System.out.println("Fighter class Move");
    }
    @Override
    public void Attack() {
        System.out.println("Fighter class Attack");
    }
}

class Unit implements Fightable{

    public void Move(int x, int y) {
        System.out.println("Unit class Move");
    }
    public void Attack(){
        System.out.println("Unit class Attack");
    }

    int currentHp;
    int x;
    int y;
}
//인터페이스의 확장방법 , Fightable 은 Movable과 Attackable 을 확장하였다.
//아무것도 작성하지 않았지만 Movable 의 Move와 Attackable의 Attack을 메서드로 가지고있다.
interface Fightable extends Movable,Attackable {
}
interface Movable{
    void Move(int x, int y);
}
interface Attackable{
    void Attack();
}
