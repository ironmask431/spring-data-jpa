package kevin.spring.springdatajpa.repository;

import kevin.spring.springdatajpa.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * spring data jpa 는 인터페이스로 repository 선언 후 extends JpaRepository 해주면 완료
 * 개발자는 인터페이스만 선언해놓으면 spring data jpa가 자동으로 구현체를 만든다.
 * @Repository 어노테이션을 생략 가능하다.
 */
public interface MemberRepository extends JpaRepository<Member, Long> {

}
