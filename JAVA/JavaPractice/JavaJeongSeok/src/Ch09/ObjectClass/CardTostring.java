package Ch09.ObjectClass;

//toString()은 인스턴스에 대한 정보를 문자열로 제공할 목적으로 정의된 메서드이다.
//인스턴스의 정보를 제공한다는 의미는 대부분 참조변수에 저장된 값들을 문자열로 표현한다고 생각하면된다.
/**
 * toString의 기본정의
 *  public String toString() {
 *         return getClass().getName() + "@" + Integer.toHexString(hashCode());
 *     }
 *     즉 toString은 오버라이딩 하지 않으면 16진수의 해시코드를 얻게 된다는 뜻이다.
 */

class Card{
    String kind;
    int number;
    Card(){
        this("SPADE",1);
    }
    Card(String kind,int number){
        this.kind = kind;
        this.number = number;
    }
}
public class CardTostring {
    public static void main(String[] args) {
        Card c1 = new Card();
        Card c2 = new Card();
        
        System.out.println(c1.toString());
        System.out.println(c2.toString());

    }
}
