### **@ModelAttribute의 또다른 사용예**

@ModelAttribute에는 두가지 사용방법이 있는데 그 중 하나는 @ModelAttribute에 Name파라미터를 채워주면 채워준 Name으로 addAttribute동작을 실행해준다.

```java
@PostMapping("/add")
public String addItemV2(@ModelAttribute("item") Item item){

    itemRepository.save(item);
    //생략가능
    //model.addAttribute("item",item);

    return "basic/item";
}
```

위 코드의 model.addAttribute("item",item);는 생략가능

#### **@ModelAttribute - 요청 파라미터 처리**

- @ModelAttribute는 객체를 생성하고 요청 파라미터의 값을 프로퍼티 접근법(setXxx)으로 입력해준다.

#### **@ModelAttribute - Model 추가**

- @ModelAttribute에는 중요한 한가지 기능이 더 있는데 바로 모델(Model)에 @ModelAttribute로 지정한 객체를 자동으로 넣어주는 기능이다.  위의 V2코드에서 addAttribute를 주석처리해도 잘 동작하는것을 확인햇엇다. 

모델에 데이터를 담을 때에는 이름이 필요하다. 이름은 @ModelAttribute에 지정한 name(value)속성을 사용한다. 만약 다음과 같이 @ModelAttribute의 이름을 다르게 지정한다면 다른 이름으로 모델에 포함된다. 즉 ModelAttribue의 name값이 모델에 저장된다고 보면 된다.



### **@ModelAttribute - name value생략**

```java
    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item, Model model){

        //Item -> item으로 즉 첫글자만 소문자로 바꿔서 자동으로 modelAttribute에 add해준다.
        itemRepository.save(item);

        return "basic/item";
    }
```

ModelAttribute의 Name (Value)를 생략 시 클래스명에서 첫글자만 소문자로 변경해서 등록된다.



### **@ModelAttribute 전체 생략**

```java
//ModelAttribute의 생략
    @PostMapping("/add")
    public String addItemV4(Item item){

        itemRepository.save(item);

        return "basic/item";
    }
```

쿼리 파라미터 값을 객체에 넣어줄 때 처럼 @ModelAttribute로 Model에 데이터를 넣어주는 작업도 동일하게 @ModelAttribute어노태이션의 생략이 가능하다.