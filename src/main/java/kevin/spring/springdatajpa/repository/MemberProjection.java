package kevin.spring.springdatajpa.repository;

//네이티브 쿼리 - 프로젝션
public interface MemberProjection {
    Long getId();
    String getUsername();
    String getTeamName();
}
