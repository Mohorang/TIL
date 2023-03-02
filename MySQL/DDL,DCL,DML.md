처음에 SQL을 공부할 때 SELECT부터 시작해서 실습해보면서 따라햇었는데 DB에는 DML,DDL,DCL로 나뉘어진다는 것도 모르고 사용하고 있엇다.

간단하게 DML , DDL,DCL의 뜻에 대해 정리하려고 한다.

#### **DML(Data Manipulation Language)**

1. 데이터 조작어
2. **SELECT** : 정말 자주 사용하는 명령어, 데이터 베이스에 들어 있는 데이터를 조회하거나 검색하기 위한 명령어
3. **INSERT** : 데이터의 삽입
4. **UPDATE** : 데이터 수정
5. **DELETE** : 데이터의 삭제

#### **DDL(Data Definition Language)**

1. 데이터 정의어, 테이블과 같은 데이터 구조를 정의하는데 사용되는 명령어이다.
2. CREATE : 테이블을 생성하는데 사용
3. ALTER : 테이블의 수정을 위해 사용
4. DROP : 테이블의 삭제
5. RENAME : 테이블의 이름변경

#### DCL(Data Control Language)

1. 논리적인 작업의 단위를 묶어서 DML에 의해 조작된 결과를 작업단위(트랜잭션) 별로 제어하는 명령어를 뜻한다.
2. COMMIT,ROLLBACK,SAVEPOINT