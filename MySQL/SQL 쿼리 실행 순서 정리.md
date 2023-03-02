### SQL 쿼리 실행 순서 정리

### SELECT 쿼리 실행 순서

SQL 쿼리문을 실행하는데 순서가 존재한다. 알고 있는데 자꾸 깜빡해서 정리를 한다.

 

SELECT 쿼리문은 FROM, WHERE, GROUP BY, HAVING, SELECT, ORDER BY 총 6단계를 거친다.

 



![img](https://blog.kakaocdn.net/dn/bLjhgu/btrBMhFvXvp/vefRgyJA0OTaxEwrCcfZJk/img.png)



아래는 각 단계의 동작을 정리한 것이다.

 

#### FROM 절 (+ Join)

**쿼리의 첫번째 실행 순서는 FROM절이다**. FROM 절에서는 조회하는 **테이블 전체**를 가져온다. INDEX를 사용하지 않는다는 가정에서 WHERE절이나 SELECT절에서 일부 행이나 열을 제거하여 출력한다고 해도 가장 처음에 테이블의 모든 데이터를 가져온다.

#### WHERE 절

WHERE 절에서는 FROM절에서 읽어온 테이블에서 **조건에 맞는 결과만 갖도록** 데이터를 필터링한다. 

#### GROUP BY

GROUP BY 절에서는 **선택한 칼럼으로 그룹핑한다.** 

#### HAVING 절

HAVING 절은 **그룹핑 후에 각 그룹에 사용되는 조건 절**이다. HAVING 절의 조건을 WHERE 절에도 사용할 수 있는 경우라면 WHERE절에 사용하는 것이 바람직하다. **HAVING 절은 각 그룹에 조건을 걸기 때문에 퍼포먼스가 떨어지게 된다.**

 

예를 들어 MONEY > 10000은 모든 레코드에 MONEY가 10000이 넘어야 한다는 조건이다. 이는 각 그룹에 따로 거는 것보다는 WHERE절로 한번에 거는 것이 좋다. (현재는 내부적으로 Optimize 해준다.)

 

반면, AVG(MONEY) > 10000처럼 집계함수의 경우에는 각 그룹별로 평균을 구하는 것과 전체 평균을 구하는 것은 다르게 동작하기 때문에 HAVING절을 사용해야 한다.

#### SELECT 절

SELECT 절은 여러 조건들을 처리한 후 남은 데이터에서 **어떤 열을 출력해줄지 선택한다.**

#### ORDER BY 절

어떤 열까지 출력할지 정했다면 행의 순서를 어떻게 보여줄지 **정렬**해주는 절이 ORDER BY이다.

### LIMIT 절

LIMIT 절은 결과 중 몇개의 행을 보여줄 지 선택한다.

 

### 실행순서가 중요한 이유

**쿼리의 실행 순서를 아는 것은 중요하다.** 실행순서를 모르면 쿼리를 제대로 작성하기 어렵다. 예를 들어 보자.

 

**OrderBy 절에서 Alias 사용**

```
SELECT CONCAT(first_name, last_name) AS full_name
FROM user
ORDER BY full_name;
```

ORDER BY 절은 SELECT 절보다 뒤에 실행되기 때문에 SELECT 절의 결과를 사용할 수 있다.

 

**Where 절에서 Alias 사용**

```
SELECT CONCAT(first_name, last_name) AS full_name
FROM user
WHERE full_name = 'VioletBeach';
```

반면 Where 절에서는 SELECT 절보다 뒤에 실행된다. WHERE 절은 FROM 절의 결과를 가지고 제한을 거는 역할이기 때문에 해당 쿼리는 에러가 발생한다.

 

WHERE 절에서 Alias를 사용하려다가 원치 않는 결과를 받는다거나, ORDER BY 절에서 SELECT 절에서 사용된 함수를 또 호출해서 자원이 낭비되는 이슈를 막으려면 실행 순서에 대한 이해가 필요하다.

 

------

### Reference

- https://myjamong.tistory.com/172
- https://docs.microsoft.com/ko-kr/sql/ssms/visual-db-tools/use-having-and-where-clauses-in-the-same-query-visual-database-tools?view=sql-server-ver15

