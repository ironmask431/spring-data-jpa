package kevin.spring.springdatajpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;
import java.util.UUID;

@EnableJpaAuditing //Auditing 을 사용하기위해 추가함.
@SpringBootApplication
public class SpringDataJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDataJpaApplication.class, args);
	}

	//Auditing 을 사용해 엔티티 등록,수정시 등록자, 수정자를 자동으로 입력할때 유저정보를 제공한다.
	@Bean
	public AuditorAware<String> auditorProvider(){
		return () -> Optional.of(Optional.of(UUID.randomUUID()).toString());
		//실무에서는 securityContext 등을 사용해 현재 로그인한 사용자의 아이디등을 넘기는 방식으로 함.
	}
}
