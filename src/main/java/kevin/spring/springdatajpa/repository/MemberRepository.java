package kevin.spring.springdatajpa.repository;

import kevin.spring.springdatajpa.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * spring data jpa 는 인터페이스로 repository 선언 후 extends JpaRepository 해주면 완료
 */
public interface MemberRepository extends JpaRepository<Member, Long> {

}
