### **JVM 메모리 구조**

자바가 운영체제에 종속되지 않는 이유는 **JVM(Java Virtual Machine)**덕분이다.

JVM은 자바 가상 머신으로 자바 컴파일러에 의해 생성된 바이트 코드를 운영체제가 이해할 수 있는 기계어로 해석하여 실행시켜주는 역할을 한다고 보면 된다.

JVM의 구성은 크게 4가지로 나뉘어진다.

- **Class Loader**
- **Execution Engine**
- **Garbage Collector**
- **Runtime Data Area** : Runtime Data Area는 또 5가지 영역으로 나뉘어진다.

![20221115_181505](https://user-images.githubusercontent.com/41957723/201882112-55848539-ca48-45ba-beb2-939983212151.png)


1. **Class Loader** : 컴파일러에 의해 생성된 바이트코드(.class)를 Runtime Data Area 에 적재하는 역할을 수행한다.

2. **Execution Engine** : Class Loader에 의해 메모리에 적재된 바이트 코드들을 기계어로 변경해 명령어 단위로 실행하는 역할을 한다.

3. **Grabage Collector** : 힙 영역에 적재된 객체들 중에 참조되지 않은 객체들을 탐색후 제거하는 역할을 담당한다. GC가 역할을 하는 시간은 정확히 언제인지 알 수 없다. GC가 수행되는 동안 GC를 수행하는 스레드가 아닌 모든 쓰레드가 일시 정지된다.

4. **Runtime Data Area** : JVM의 메모리 영역으로 자바 애플리케이션을 실행할 때 사용되는 데이터들을 적재하는 영역이다. 이 영역은 크게 

   - **Method Area**
   - **Heap Area**
   - **Stack Area** 
   - **PC register**
   - **Native Method Stack**

   으로 나눌 수 있다.

   1. **Method Area(메소드 영역)** : 주로 클래스 관련 (멤버변수 , 접근제어자, 상수 풀, 정적변수 등)이 생성되는 영역이다.
   2. **Heap Area(힙 영역)** : new 키워드에 의해 생성된 객체와 배열들이 생성되는 영역이다. 메소드 영역에 로드된 클래스만 생성이 가능하고 Garbage Collector가 참조되지 않는 메모리를 확인하고 제거하는 영역이다.
   3. **Stack Area** : 지역변수 , 파라미터 , 리턴값 연산에 사용되는 임시 값들이 생성되는 영역이다. int a = 10; 이라는 코드를 작성햇다면 스택영역에 a라는 이름의 10의 값을 담고있는 영역을 만들어준다. 
      다른예로 Person p = new Person(); 이라는 새로운 객체를 생성하면 스택영역에 p라는 영역이 생성되며 힙 영역에는 Person클래스의 인스턴스가 담겨져 있고 p에는 새로생성된 Person클래스가 생성된 힙 영역의 주소값을 담게된다.
   4. **PC Register(PC Register)** : 스레드가 생성될 때마다 생성되는 영역으로 Program Counter 즉 현재 스레드가 실행되는 부분의 주소와 명령을 저장하고 있는 영역이다. 이것을 이용해서 스레드를 돌아가면서 수행할 수 있게 된다.
