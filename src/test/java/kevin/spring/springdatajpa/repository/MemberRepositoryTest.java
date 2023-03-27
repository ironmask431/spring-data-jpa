package kevin.spring.springdatajpa.repository;

import kevin.spring.springdatajpa.dto.MemberDto;
import kevin.spring.springdatajpa.entity.Member;
import kevin.spring.springdatajpa.entity.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

    @Test
    void findBynames(){
        List<Member> members = memberRepository.findByNames(Arrays.asList("장규리", "송하영"));
        for(Member member : members){
            System.out.println("leesh - member = "+member);
        }
    }

    @Test
    void returnType() throws Exception {
        //컬렉션타입으로 조회 시 값이 없으면 spirng data jpa 에서 빈 empty 컬렉션을 반환해준다.
        List<Member> memberList = memberRepository.findListByUsername("장규리");
        //엔티티 단독 조회 시 result = 0 이면 null 로 리턴됨.
        //엔티티 단독 조회 시 result2개 이상이면 exception 발생
        Member member = memberRepository.findMemberByUsername("장규리");
        //Optional<엔티티>타입으로 조회 시 result = 0 이면 비어있는 Optional 리턴.
        //Optional<엔티티>타입으로 조회 시 result2개 이상이면 exception 발생
        Optional<Member> optional = memberRepository.findOptionalByUsername("송하영");

        System.out.println(memberList.get(0));
        System.out.println(memberList.size());
        System.out.println(member);
        System.out.println(optional.orElseThrow(() -> new Exception()));

//        여러 반환타입으로 정상 조회됨
//        Member(id=5, username=송하영, age=21)
//        Member(id=5, username=송하영, age=21)
//        Member(id=5, username=송하영, age=21)

        //컬렉션타입으로 조회 시 값이 없으면 spirng data jpa 에서 빈 empty 컬렉션을 반환해준다.
        List<Member> nullList = memberRepository.findListByUsername("ㄹㅇㄴㄹㅇㄹ");
        System.out.println("nullList.size() = "+nullList.size()); // 0
    }

    @Test
    void paging() {
        //given
        memberRepository.save(new Member("양연제1", 15, teamA));
        memberRepository.save(new Member("양연제2", 15, teamA));
        memberRepository.save(new Member("양연제3", 15, teamA));
        memberRepository.save(new Member("양연제4", 15, teamA));
        memberRepository.save(new Member("양연제5", 15, teamA));

        int age = 15;

        //pageRequest 객체 생성.
        PageRequest pageRequest =  PageRequest.of(0,3, Sort.by(Sort.Direction.DESC, "username"));

        //when
        Page<Member> page = memberRepository.findByAge(age, pageRequest);

        //jpql 사용 시
        //Page<Member> page = memberRepository.findByAgeJpql(age, pageRequest);

        //엔티티로 조회한 Page<entity> 를 Page<dto> 로 변환하는 법
        Page<MemberDto> toMap = page.map(member -> new MemberDto(member.getId(),member.getUsername(),member.getTeam().getName()));

        //then
        List<Member> content = page.getContent();
        long totalElements = page.getTotalElements();
        System.out.println("totalElements = "+totalElements);
        for(Member member : content){
            System.out.println("member = "+member);
        }

        assertEquals(content.size(), 3);
        assertEquals(page.getTotalElements(),  5);
        assertEquals(page.getNumber(), 0);
        assertEquals(page.getTotalPages(), 2);
        assertEquals(page.isFirst(), true);
        assertEquals(page.hasNext(), true);

    }

    //** 벌크성 수정 쿼리
    //jpa에서는 기본적으로 조회한엔티티의 값을 변경 하면 더티체킹으로 업데이트가 자동실행되나,
    //별도로 여러row의 데이터를 일괄 수정 시에는 엔티티를 모두 조회해서 값을 수정하는 것보다 update 쿼리를 직접 작성해주는게 더 효율적일 때도 있다.
    //그러나 벌크성 수정 쿼리는 위험성이 있으므로 안쓰는것이 좋다.
    //엔티티 조회 후 벌크성 update 실행 시 영속성 컨텍스트내에서는 update 이전 데이터이나,  실제 데이터는 update 이후 데이터이므로, 오차가 발생함.
    //그래서 벌크성 수정쿼리 후에는 영속성컨텐스트를 다 날려버려야 한다. (벌크성 수정쿼리를 사용할 시에는 다른 서비스로직과 트랙잰셔을 같이 쓰지않도록 하는게 필요함.)
    //repository 메소드에서 (@Modifying(clearAutomatically = true) 사용하는 방법이있음.)
    @Test
    public void bulkUpdate(){
        //given
        memberRepository.save(new Member("송하영", 30, teamA));
        memberRepository.save(new Member("장규리", 31, teamA));
        memberRepository.save(new Member("이나경", 32, teamA));
        memberRepository.save(new Member("박지원", 33, teamA));
        memberRepository.save(new Member("이새롬", 34, teamA));

        //when
        int resultCount = memberRepository.bulkAgePlus(30);

        //then
        assertEquals(resultCount, 5);
    }

    //다음 수업

    @Test
    void test(){
        String a = "기타";
        String b = "12.5";
        Float remain = 30.5f;
        try{
            //System.out.println(remain / Float.valueOf(a));
            System.out.println(remain / Float.valueOf(b));
        }catch (NumberFormatException e){

        }
        System.out.println("end");
    }
}