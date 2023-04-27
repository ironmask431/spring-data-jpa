package kevin.spring.springdatajpa.dto;

import lombok.Getter;

//Projection
//별도로 만든 dto 클래스 타입으로 repository 쿼리에서 특정 컬럼값만 받을 수 있다.
@Getter
public class UsernameOnlyDto {
    //필드명과 조회 엔티티 필드명이 같아야 조회됨.
    private String username;

    //주의 : 기본생성자가 있을 경우 엔티티타입이 dto로 변환이 안됨...! 기본생성자 빼기
    public UsernameOnlyDto(String username) {
        this.username = username;
    }
}
