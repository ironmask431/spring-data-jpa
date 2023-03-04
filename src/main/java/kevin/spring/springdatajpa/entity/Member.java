package kevin.spring.springdatajpa.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

//jpa스펙기준 엔티티들은 protected 기본생성자가 필요하다.
//jpa 스펙상 private로 선언하면 안됨... 오류발생

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of={"id","username","age"})
public class Member {
    @Id
    @GeneratedValue
    @Column(name="member_id")
    private Long id;

    private String username;

    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    public Member(String username, int age){
        this.username = username;
        this.age = age;
    }

    public Member(String username, int age, Team team){
        this.username = username;
        this.age = age;
        if(team != null){
            changeTeam(team);
        }
    }

    //연관관계 변경 메소드
    public void changeTeam(Team team){
        this.team = team;
        team.getMembers().add(this);
    }
}
