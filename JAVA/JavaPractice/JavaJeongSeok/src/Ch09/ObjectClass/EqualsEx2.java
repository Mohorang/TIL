package Ch09.ObjectClass;

class Person{
    long id;
    //equals를 오버라이딩 하여 참조변수값이 아닌 객체가 가지고 있는 value값을 비교하게 끔 변경
    public boolean equals(Object obj){
        if (obj instanceof Person) {
            //obj가 Object타입이므로 id값을 참조하기 위해서는 person타입으로의 형변환이 필요하다.
            return id == ((Person)obj).id;
        }
        else{
            return false;
        }
    }
    Person(long id){
        this.id = id;
    }
}

public class EqualsEx2 {
    public static void main(String[] args) {
        Person p1 = new Person(8011081111222L);
        Person p2 = new Person(8011081111222L);

        if(p1 == p2){
            System.out.println("p1과 p2는 같은 사람입니다.");
        }
        else System.out.println("p1과 p2는 다른 사람입니다.");

        if(p1.equals(p2)){
            System.out.println("p1과 p2는 같은 사람입니다.");
        }
        else System.out.println("p1과 p2는 다른 사람입니다.");
    }
}
