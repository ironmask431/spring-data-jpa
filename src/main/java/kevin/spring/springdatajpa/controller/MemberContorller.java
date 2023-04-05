package kevin.spring.springdatajpa.controller;

import kevin.spring.springdatajpa.dto.MemberDto;
import kevin.spring.springdatajpa.entity.Member;
import kevin.spring.springdatajpa.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;


@RestController
@RequiredArgsConstructor
public class MemberContorller {
    private final MemberRepository memberRepository;

    //web확장 - 페이징과 정렬
    //?page=2&size=3&sort=id,desc 파라메타 가능.
    //application.yml 파일에 글로벌 페이지 설정 가능. (application.yml 참조)
    //이 api만의 페이지 설정은 @PageableDefault 추가해서 속성 적용가능.
    @GetMapping("/members")
    public Page<MemberDto> list(@PageableDefault(size = 5, sort = "username") Pageable pageable){
        Page<Member> page = memberRepository.findAll(pageable); //findAll 호출 시 pageable 객체를 넘기면 Page타입으로 리턴됨.
        //entity를 반환타입으로 지정하는것은 여러 문제발생시킴. dto 타입으로 반환하는것이 바람직.
        Page<MemberDto> dtoPage = page.map(MemberDto::new);
        return dtoPage;
    }

    //테스트데이터 입력용. @PostConstruct > 서버구동될때 1회 실행됨.  
    @PostConstruct
    public void init(){
        for(int i=0; i<100; i++){
            memberRepository.save(new Member("user"+i, i));
        }
    }
}
