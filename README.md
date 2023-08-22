# spring-data-jpa
강의명 : spring data jpa (김영한)

### 이 강의에서 배운 것
1. jpa Entity 에서 protected 생성자가 필요한 이유.
   - 무분별한 객체 생성방지(PUBLIC), 하지만 private로는 선언불가(JPA스펙상)
3. Spring data jpa 를 사용할 Repository 선언방법
   - public interface 인터페이스명 extends JpaRepository<Entity클래스명, Long>
5. spring data jpa repository가 제공하는 기본 메소드드들.
   - findAll, findById, save 등.
7. 쿼리메소드 참고   
   - https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
8. jpql, 네이티브 쿼리 선언 방법
9. JPQL 사용하여 DTO클래스 반환 타입으로 조회하기
10. Entity, List<Entity>, Optional<Entity> 반환타입으로 조회가능
11. 조회 시 결과값이 없으면 List<Entity>타입은 Empty List로 반환됨. Entity 는 NULL, Optional 은 Empty Optional 리턴 
12. Page, pageable, PageRequest 을 이용하여 페이징처리
13. @PageableDefault, application.yml 설정을 통해 페이징 디폴트 설정 가능
14. N + 1 문제, fetchJoin 과 @EntityGraph 개념
    - N + 1 문제. 연관된 entity조회 시 해당 entity 들을 조회하는 쿼리가 각각 추가로 실행되는 문제
    - fetchJoin 사용 시 Lazy로 연관된 entity도 최초 1회 쿼리에서 같이 조회할 수 있다.  
    - fetchJoin 방법 : JPQL 쿼리사용 "from Member m left join fetch m.team"
    - @EntityGraph 를 사용하면 JPQL을 사용하지 않고 fetchJoin 을 쓸수 있음.
      ```java
      //fetchJoin 을 jpql을 쓰지않은 메소드 쿼리에서도 사용가능하다.
      @EntityGraph(attributePaths = {"team"})
      List<Member> findEntityGraphByUsername(@Param("username") String username);
      ``` 
    - fetchType = Lazy로 되어잇는 연관관계의 객체를 자주 사용하는 경우
    - 그냥 조회 시 추가 쿼리가 자주 발생하므로 이런 경우에는 fetchJoin으로 조회하면 쿼리 실행 개수를 줄일 수 있음.
15. 벌크성 수정 쿼리에 대해.
16. jpa Hint & Lock (자세한 내용은 패스 있다는 것만 알고 넘어가자)
17. Projection - 엔티티에서 원하는 필드만 interface(getter), dto 으로 만들어서 반환타입으로 지정하여 조회할 수 있다. 
    - Projection을 활용하면 Entity로 조회 -> dto로 변환 이런작업을 하지않아도 됨.(바로 DTO 타입으로 조회)
    - 별도로 만든 dto 클래스 타입으로 repository 쿼리에서 특정 컬럼값만 받을 수 있다.
    - dto의 필드명과 조회 엔티티 필드명이 같아야 조회됨.
    - * 주의 : 기본생성자가 있을 경우 엔티티타입이 dto로 변환이 안됨...! 기본생성자 빼기
    - nativeQuery로 조회 시 에는 반환타입을 interface로 만들어서 사용함. DTO로는 네이티브쿼리로는 조회가 안된다.
18. 네이티브 쿼리 사용방법.  
19. 네이티브쿼리로 페이징 처리를 하고 싶을때 projection, inteface를 사용함.
    - 단, 네이티브 쿼리이기 때문에 카운트 쿼리를 항상 별도로 명시해줘야 한다
20. @EntityListeners, @MappedSuperclass 를 선언한 베이스엔티티를 상속받아서 엔티티의   
등록자, 수정자, 등록일자, 수정일자를 자동 insert, update 할 수 있다.
