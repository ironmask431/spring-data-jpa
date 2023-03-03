package kevin.spring.springdatajpa.repository;

import kevin.spring.springdatajpa.entity.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberJpaRepositoryTest {

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    @Transactional
    void save() {
        Member member = new Member("김나나");
        Member savedMember = memberJpaRepository.save(member);
        Assertions.assertEquals(member.getId(), savedMember.getId());
        Assertions.assertEquals(member.getUsername(), savedMember.getUsername());
    }

    @Test
    void find() {

    }
}