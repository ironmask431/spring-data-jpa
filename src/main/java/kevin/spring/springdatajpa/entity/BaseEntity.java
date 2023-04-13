package kevin.spring.springdatajpa.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class) //create, update 등 이벤트가 있을때 동작하는 클래스임을 나타냄.
@MappedSuperclass //Entity들의 공통요소들을 슈퍼클래스에서 상속받아서 사용하고자 할때 슈퍼클래스에 선언준다.
@Getter
public class BaseEntity {

    //등록일자
    @CreatedDate //insert 시 생성시간을 자동으로 입력하도록 지원
    @Column(updatable = false)
    private LocalDateTime createDate;

    //수정일자
    @LastModifiedDate //update 시 수정시간을 자동으로 업데이트하도록 지원
    private LocalDateTime lastModifiedDate;

    //등록자
    @CreatedBy
    @Column(updatable = false)
    private String createBy;

    //수정자
    @LastModifiedBy
    private String lastModifiedBy;
}
