package kevin.spring.springdatajpa.repository;

import kevin.spring.springdatajpa.dto.MemberDto;
import kevin.spring.springdatajpa.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * spring data jpa 는 인터페이스로 repository 선언 후 extends JpaRepository 해주면 완료
 * 개발자는 인터페이스만 선언해놓으면 spring data jpa가 자동으로 구현체를 만든다.
 *
 * @Repository 어노테이션을 생략 가능하다.
 */
public interface MemberRepository extends JpaRepository<Member, Long> {
    //spring data jpa 에서 제공하는 쿼리메소드기능
    //spring.io > spring data jpa 에서 쿼리메소드 가이드문서 활용하기.
    //https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation

    //메소드 이름으로 쿼리 생성
    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    List<Member> findByUsername(String username);

    // @Query, 리포지토리 메소드에 쿼리 정의하기
    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
    List<String> findUserName();

    //DTO 조회하기
    @Query("select new kevin.spring.springdatajpa.dto.MemberDto(m.id, m.username, t.name) from Member m left join m.team t")
    List<MemberDto> findMemberDto();

    //파라미터 바인딩
    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") List<String> names);

    //반환 타입 - 여러가지 반환타입으로 사용 할 수 있음.
    List<Member> findListByUsername(String username); //컬렉션
    Member findMemberByUsername(String username); //단건
    Optional<Member> findOptionalByUsername(String username); // 단건 Optional

    //spring data jpa 페이징
    Page<Member> findByAge(int age, Pageable pageable);

    //spring data jpa 페이징 - jpql을 사용
    //countQuery 를 명시하지 않을 시 기본 쿼리로 total count 조회.
    //countQuery 성능이 낮을 경우 별도로 countQuery 를 선언해서 사용이 가능하다.
    @Query(value = "select m from Member m left join m.team t where m.age = :age",
    countQuery = "select count(m.username) from Member m where m.age = :age")
    Page<Member> findByAgeJpql(@Param("age") int age, Pageable pageable);

    //update 쿼리를 사용시에는 @Modifying 어노테이션을 꼭 붙여줘야 실행됨.
    //clearAutomatically = true : 쿼리실행 후 영속성 컨텍스트를 clear처리 해준다.
    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);
}
