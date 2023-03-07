package kevin.spring.springdatajpa.repository;

import kevin.spring.springdatajpa.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberJpaRepositoryTest {

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    void testMember() {
        Member member = new Member("장규리", 21);
        memberJpaRepository.save(member);
        Member savedMember = memberJpaRepository.find(member.getId());
        assertEquals(member.getId(), savedMember.getId());
        assertEquals(member.getUsername(), savedMember.getUsername());
    }

    @Test
    void basicCRUD(){
        Member member1 = new Member("장규리", 21);
        Member member2 = new Member("송하영", 21);
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        //단건 조회 검증
        Member findMember1 = memberJpaRepository.findById(member1.getId()).get();
        Member findMember2 = memberJpaRepository.findById(member2.getId()).get();
        assertEquals(findMember1, member1);
        assertEquals(findMember2, member2);

        //리스트 조회 검증
        List<Member> all = memberJpaRepository.findAll();
        assertEquals(all.size(), 2);

        //카운트 검증
        long count = memberJpaRepository.count();
        assertEquals(count, 2);

        //삭제 검증
        memberJpaRepository.delete(member1);
        memberJpaRepository.delete(member2);
        count = memberJpaRepository.count();
        assertEquals(count, 0);
    }
}