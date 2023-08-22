package kevin.spring.springdatajpa.repository;

import kevin.spring.springdatajpa.dto.MemberDto;
import kevin.spring.springdatajpa.dto.UsernameOnly;
import kevin.spring.springdatajpa.dto.UsernameOnlyDto;
import kevin.spring.springdatajpa.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
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

    //fetchJoin - member에 연관된 team 객체정보도 한번의 쿼리 실행으로 다 조회한다.
    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();

    //위 fetchJoin 과 똑같은 기능
    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member m")
    List<Member> findMemberFetchJoin2();

    //fetchJoin 을 jpql을 쓰지않은 메소드 쿼리에서도 사용가능하다.
    @EntityGraph(attributePaths = {"team"})
    List<Member> findEntityGraphByUsername(@Param("username") String username);

    //Lock - 자세한 내용에 대한 추가정리 필요
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Member> findLockByUsername(@Param("username") String username);


    //Projection
    //별도로 만든 인터페이스 타입으로 특정 컬럼값만 받을 수 있다. (네이티브 쿼리에서도 유용하게 사용)
    //find와 ~By 사이에는 아무단어나 넣어도됨.

    //interface 타입으로 받기
    List<UsernameOnly> findInterfaceByUsername(@Param("username") String username);

    //Dto 클래스타입으로 받기
    List<UsernameOnlyDto> findDtoByUsername(@Param("username") String username);

    //동적타입으로 받기도 가능
    <T> List<T> findTypeByUsername(@Param("username") String username, Class<T> type);

    //네이티브 쿼리
    @Query(value = "select * from member where username = ?", nativeQuery = true)
    Member findByNativeQuery(String username);


    //네이티브 쿼리 - 프로젝션, Pageable 사용가능 (단, 네이티브 쿼리이기 때문에 카운트 쿼리를 별도로 추가해줘야함.)
    //프로젝션(인터페이스)를 사용해서 네이티브 쿼리 에서 조회한 컬럼들을 받을 수 있다.
    @Query(value = "select m.member_id as id, m.username as username, t.name as teamName " +
        "from member m left join team t "
        , countQuery = "select count(*) from member"
        , nativeQuery = true)
    Page<MemberProjection> findByNativeProjection(Pageable pageable);

}
