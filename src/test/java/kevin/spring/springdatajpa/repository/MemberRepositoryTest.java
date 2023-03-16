package kevin.spring.springdatajpa.repository;

import kevin.spring.springdatajpa.dto.MemberDto;
import kevin.spring.springdatajpa.entity.Member;
import kevin.spring.springdatajpa.entity.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

    @Autowired
    TeamRepository teamRepository;

    Member member1;
    Member member2;
    Member member3;

    Team teamA;
    Team teamB;

    @BeforeEach
    void init(){
        teamA = new Team("teamA");
        teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);

        member1 = new Member("장규리", 10, teamA);
        member2 = new Member("장규리", 20, teamA);
        member3 = new Member("송하영", 21, teamB);
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);


    }

    @Test
    void testMember() {
        Member savedMember = memberRepository.findById(member1.getId()).orElseThrow(()-> new IllegalArgumentException());
        Assertions.assertEquals(member1.getId(), savedMember.getId());
        Assertions.assertEquals(member1.getUsername(), savedMember.getUsername());
    }

    @Test
    void basicCRUD(){
        //단건 조회 검증
        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();
        assertEquals(findMember1, member1);
        assertEquals(findMember2, member2);

        //리스트 조회 검증
        List<Member> all = memberRepository.findAll();
        assertEquals(all.size(), 3);

        //카운트 검증
        long count = memberRepository.count();
        assertEquals(count, 3);

        //삭제 검증
        memberRepository.delete(member1);
        memberRepository.delete(member2);
        count = memberRepository.count();
        assertEquals(count, 0);
    }

    @Test
    void findByUsernameAndAgeGreaterThen(){
        List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("장규리", 15);

        assertEquals(result.get(0).getUsername(), "장규리");
        assertEquals(result.get(0).getAge(), 20);
        assertEquals(result.size(), 1);
    }

    @Test
    void findUser(){
        List<Member> result = memberRepository.findUser("장규리", 10);
        assertEquals(result.get(0), member1);
    }

    @Test
    void findMemberDto(){
        List<MemberDto> memberDtos = memberRepository.findMemberDto();
        for(MemberDto memberDto : memberDtos){
            System.out.println("leesh - dto = "+memberDto);
        }
    }

}