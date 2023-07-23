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
8. JPQL 사용방법, nativeQuery 사용방법
9. JPQL 사용하여 DTO클래스 반환 타입으로 조회하기
10. Entity, List<Entity>, Optional<Entity> 반환타입으로 조회가능
11. 조회 시 결과값이 없으면 List<Entity>타입은 Empty List로 반환됨. Entity 는 NULL, Optional 은 Empty Optional 리턴 
12. Page, pageable, PageRequest 을 이용하여 페이징처리
13. fetchJoin 과 @EntityGraph 개념
14. 벌크성 수정 쿼리
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
