package Ch09.ObjectClass;

import javax.swing.plaf.InsetsUIResource;

//toString이 해시코드가 아닌 원하는 정보를 출력하게끔 오버라이딩 하는 예제

/**
 * 출력결과
 * kind : SPADE, number : 1
 * kind : HEART, number : 10
 */
class Card2{
    String kind;
    int number;
    Card2(){
        this("SPADE",1);
    }
    Card2(String kind,int number){
        this.kind = kind;
        this.number = number;
    }
    //toString 메서드 오버라이딩
    @Override
    public String toString(){
        return "kind : " + kind + ", number : " + number;
    }
}
public class CardToString2 {
    public static void main(String[] args) {
        Card2 c1 = new Card2();
        Card2 c2 = new Card2("HEART",10);

        System.out.println(c1.toString());
        System.out.println(c2.toString());
    }

}
