package kevin.spring.springdatajpa.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    //jpa스펙기준 엔티티들은 protected 기본생성자가 필요하다.
    //jpa 스펙상 private로 선언하면 안됨... 오류발생
    protected Member(){

    }

    public Member(String username){
        this.username = username;
    }
}
