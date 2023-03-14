package kevin.spring.springdatajpa.repository;

import kevin.spring.springdatajpa.entity.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Test
    void testMember() {
        Member member = new Member("장규리",21);
        memberRepository.save(member);
        Member savedMember = memberRepository.findById(member.getId()).orElseThrow(()-> new IllegalArgumentException());
        Assertions.assertEquals(member.getId(), savedMember.getId());
        Assertions.assertEquals(member.getUsername(), savedMember.getUsername());
    }

    @Test
    void basicCRUD(){
        Member member1 = new Member("장규리", 21);
        Member member2 = new Member("송하영", 21);
        memberRepository.save(member1);
        memberRepository.save(member2);

        //단건 조회 검증
        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();
        assertEquals(findMember1, member1);
        assertEquals(findMember2, member2);

        //리스트 조회 검증
        List<Member> all = memberRepository.findAll();
        assertEquals(all.size(), 2);

        //카운트 검증
        long count = memberRepository.count();
        assertEquals(count, 2);

        //삭제 검증
        memberRepository.delete(member1);
        memberRepository.delete(member2);
        count = memberRepository.count();
        assertEquals(count, 0);
    }

    @Test
    void findByUsernameAndAgeGreaterThen(){
        Member member1 = new Member("장규리", 10);
        Member member2 = new Member("장규리", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("장규리", 15);

        assertEquals(result.get(0).getUsername(), "장규리");
        assertEquals(result.get(0).getAge(), 20);
        assertEquals(result.size(), 1);
    }
}