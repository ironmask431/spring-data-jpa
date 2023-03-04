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
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Test
    void save() {
        Member member = new Member("장규리",21);
        memberRepository.save(member);
        Member savedMember = memberRepository.findById(member.getId()).orElseThrow(()-> new IllegalArgumentException());
        Assertions.assertEquals(member.getId(), savedMember.getId());
        Assertions.assertEquals(member.getUsername(), savedMember.getUsername());
    }
}