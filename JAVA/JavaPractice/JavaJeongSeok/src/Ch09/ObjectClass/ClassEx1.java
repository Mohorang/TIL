package Ch09.ObjectClass;

import java.nio.charset.Charset;

public class ClassEx1 {
    public static void main(String[] args) throws Exception {
        Card3 c= new Card3();

        Class c2 = c.getClass();
        System.out.println(c.toString());
        System.out.println(c2.toString());
        System.out.println(c2.getName());
        System.out.println(c2.toGenericString());
        System.out.println(c2.toString());
    }
}
final class Card3 {
    String kind;
    int num;
    Card3(){
        this("SPADE",1);
    }
    Card3(String kind, int num){
        this.kind = kind;
        this.num = num;
    }
    @Override
    public String toString(){
        return kind + " : " + num;
    }
}