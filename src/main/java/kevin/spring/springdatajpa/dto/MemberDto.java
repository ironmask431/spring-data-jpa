package kevin.spring.springdatajpa.dto;

import kevin.spring.springdatajpa.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
public class MemberDto {
    private Long id;
    private String username;
    private String teamName;

    public MemberDto(Long id, String username, String teamName) {
        this.id = id;
        this.username = username;
        this.teamName = teamName;
    }

    public MemberDto(Member member){
        this.id = member.getId();
        this.username = member.getUsername();
        this.teamName = member.getTeam().getName();
    }
}
