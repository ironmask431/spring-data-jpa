# spring-data-jpa
강의명 : spring data jpa (김영한)

### 이 강의에서 배운 것
1. jpa Entity 에서 protected 생성자가 필요한 이유.
   - 무분별한 객체 생성방지(PUBLIC), 하지만 private로는 선언불가(JPA스펙상)
3. Spring data jpa 를 사용할 repository 선언방법
   - public interface 인터페이스명 extends JpaRepository<Entity클래스명, Long>
5. spring data jpa repository가 제공하는 기본 메소드드들.
   - findAll, findById, save 등.
7. 쿼리메소드 참고   
   - https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
9. JPQL 사용하여 DTO클래스 반환 타입으로 조회하기
10. Entity, List<Entity>, Optional<Entity> 반환타입으로 조회가능
11. 조회 시 결과값이 없으면 List<Entity>타입은 Empty List로 반환됨. Entity 는 NULL, Optional 은 Empty Optional 리턴 
12. Page, pageable, PageRequest 을 이용하여 페이징처리
13. N + 1 문제, fetchJoin 과 @EntityGraph 개념
    - N + 1 문제. 연관된 entity조회 시 해당 entity들을 조회하는 쿼리가 각각 추가로 실행되는 문제
    - fetchJoin 사용 시 Lazy로 연관된 entity도 처음 조회 쿼리에서 같이 조회한다.  
    - fetchJoin 방법 : JPQL 쿼리사용 "from Member m left join fetch m.team"
    - @EntityGraph 를 사용하면 JPQL을 사용하지 않고 fetchJoin 을 쓸수 있음.
      ```java
      //fetchJoin 을 jpql을 쓰지않은 메소드 쿼리에서도 사용가능하다.
      @EntityGraph(attributePaths = {"team"})
      List<Member> findEntityGraphByUsername(@Param("username") String username);
      ``` 
    - fetchType = Lazy로 되어잇는 연관관계의 객체를 자주 사용하는 경우
    - 그냥 조회 시 추가 쿼리가 자주 발생하므로 이런 경우에는 fetchJoin으로 조회하면 쿼리 실행 개수를 줄일 수 있음.
15. 벌크성 수정 쿼리
```
jpa에서는 기본적으로 조회한엔티티의 값을 변경 하면 더티체킹으로 업데이트가 자동실행되나,
별도로 여러row의 데이터를 일괄 수정 시에는 엔티티를 모두 조회해서 값을 수정하는 것보다
update 쿼리를 직접 작성해주는게 더 효율적일 때도 있다.
그러나 벌크성 수정 쿼리는 위험성이 있으므로 안쓰는것이 좋다.
엔티티 조회 후 벌크성 update 실행 시 영속성 컨텍스트내에서는 update 이전 데이터이나,
실제 데이터는 update 이후 데이터이므로, 오차가 발생함.
그래서 벌크성 수정쿼리 후에는 영속성컨텐스트를 다 날려버려야 한다. (벌크성 수정쿼리를
사용할 시에는 다른 서비스로직과 트랙잰션을 같이 쓰지않도록 하는걸 추천)
EntityManager 를 사용해서 em.flush(); em.clear(); 하는 방법
repository 메소드에서 (@Modifying(clearAutomatically = true) 사용하는 방법이있음.
```
16. jpa Hint & Lock (자세한 내용은 패스 있다는 것만 알고 넘어가자)
17. Projection - jpa쿼리 반환타입을 원하는 속성만 interface, dto 으로 만들어서 받을 수 있다.
    - Projection을 활용하면 Entity로 조회 -> dto로 변환 이런작업을 하지않아도 됨.
    - 별도로 만든 dto 클래스 타입으로 repository 쿼리에서 특정 컬럼값만 받을 수 있다.
    - 필드명과 조회 엔티티 필드명이 같아야 조회됨.
    - * 주의 : 기본생성자가 있을 경우 엔티티타입이 dto로 변환이 안됨...! 기본생성자 빼기
    - 주로 nativeQuery로 데이터 조회시 반환타입을 interface로 만들어서 활용함.
19. 네이티브쿼리로 페이징 처리를 하고 싶을때 projection, inteface를 사용함.
    - 단, 네이티브 쿼리이기 때문에 카운트 쿼리를 항상 별도로 명시해줘야 한다.
