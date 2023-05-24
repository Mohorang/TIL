package JAVA;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

//hashMap과 LinkedHashMap의 차이
//용도는 똑같지만 그 순서를 기억하고싶을때 사용
public class Collections {
    public static void main(String[] args) {
        HashMap<String , Integer> hashMap = new HashMap<>();

        hashMap.put("첫번째",1);
        hashMap.put("두번째",2);
        hashMap.put("세번째",3);

        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("첫번째",1);
        map.put("두번째",2);
        map.put("세번째",3);

        for (String s : hashMap.keySet()) {
            System.out.println("hashMap.get(s) = " + hashMap.get(s));
        }
        for (String s : map.keySet()) {
            System.out.println("map.get(s) = " + map.get(s));
        }
    }
}
