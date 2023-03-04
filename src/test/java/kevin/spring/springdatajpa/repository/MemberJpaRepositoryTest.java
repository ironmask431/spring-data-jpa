package kevin.spring.springdatajpa.repository;

import kevin.spring.springdatajpa.entity.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberJpaRepositoryTest {

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    void save() {
        Member member = new Member("장규리", 21);
        memberJpaRepository.save(member);
        Member savedMember = memberJpaRepository.find(member.getId());
        Assertions.assertEquals(member.getId(), savedMember.getId());
        Assertions.assertEquals(member.getUsername(), savedMember.getUsername());
    }
}