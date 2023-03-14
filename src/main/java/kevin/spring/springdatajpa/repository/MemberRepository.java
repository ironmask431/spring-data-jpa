package kevin.spring.springdatajpa.repository;

import kevin.spring.springdatajpa.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * spring data jpa 는 인터페이스로 repository 선언 후 extends JpaRepository 해주면 완료
 * 개발자는 인터페이스만 선언해놓으면 spring data jpa가 자동으로 구현체를 만든다.
 * @Repository 어노테이션을 생략 가능하다.
 */
public interface MemberRepository extends JpaRepository<Member, Long> {
    //spring data jpa 에서 제공하는 쿼리메소드기능
    //spring.io > spring data jpa 에서 쿼리메소드 가이드문서 활용하기.
    //https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);
}
