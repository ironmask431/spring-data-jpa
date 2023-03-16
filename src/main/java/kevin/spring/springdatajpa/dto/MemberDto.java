package kevin.spring.springdatajpa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Data
public class MemberDto {
    private Long id;
    private String username;
    private String teamName;
}
