package kevin.spring.springdatajpa.dto;

//Projection
//별도로 만든 인터페이스 타입으로 repository 쿼리에서 특정 컬럼값만 받을 수 있다. (네이티브 쿼리에서도 유용하게 사용)
public interface UsernameOnly {
    String getUsername();
}
