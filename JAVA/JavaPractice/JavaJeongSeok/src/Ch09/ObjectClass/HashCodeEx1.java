package Ch09.ObjectClass;


/**
 * 실행결과
 * true
 * 96354
 * 96354
 * 1435804085
 * 1784662007
 */
public class HashCodeEx1 {
    public static void main(String[] args) {
        String str1 = new String("abc");
        String str2 = new String("abc");


        System.out.println(str1.equals(str2));

        //String 클래스는 문자열의 내용이 같으면 동일한 해시코드를 반환하도록 hashCode메서드가 오버라이딩 되어있다.
        //따라서 항상 같은 해시코드 값을 얻게된다.
        System.out.println(str1.hashCode());
        System.out.println(str2.hashCode());

        //System.identityHashCode 는 Object클래스의 hashCode와 같이 객체의 주소값으로 해시코드를
        //생성하기 때문에 모든 객체에 대해 항상 다른 해시코드 값을 반환할 것을 보장한다. 따라서 str1과 str2가 서로 다른 객체라는것을 알 수 있다.
        //하지만 이 결과도 new String()이 아닌 ""로 생성하면 결과는 같아진다. 이는 constant pool 과 관련이 있다.
        System.out.println(System.identityHashCode(str1));
        System.out.println(System.identityHashCode(str2));
    }



}
