# 환자 관리 API

### 프로젝트 실행 방법
1. H2DB 실행
   1. h2db 처음 DB 생성 및 연결
      - Saved Settings: : Generic H2 (Server)
      - Driver Class : org.h2.Driver
      - JDBC URL : jdbc:h2:~/PatientManagementDB
      - User Name: sa

   2. DB 생성 이후부터 h2db 접속 
       - Saved Settings: : Generic H2 (Embedded)
       - Driver Class : org.h2.Driver
       - JDBC URL : jdbc:h2:tcp://localhost/~/PatientManagementDB
       - User Name: sa
      
2. 프로젝트 실행


### restdocs 문서 확인 방법
1. restdocs 문서 확인 방법
   1. application run
   2. http://localhost:8080/docs/index.html 접속

2. restdocs 문서 생성 방법
   1. 테스트코드 run : 테스트가 통과되면 './build/generated-snippets'에 .adoc 파일 생성됨
   2. gradle build : '/src/docs/asciidocs/index.adoc' 파일의 설정대로 'src/main/resources/static/docs'에 index.html 생성됨
   3. application run
   4. http://localhost:8080/docs/index.html 접속


### 기술 스택

- java 8
- spring boot 2.7.12
- spring data jpa
- querydsl
- h2db
- spring restdocs

### 요구 사항
1. 웹 프로젝트 구성
- Packaging 을 Jar로 선택
- Dependencies : Spring Web, Spring Data JPA, H2 Databse


2. H2 설정하기
- application.yml 추가 : h2-console 활성화, spring의 datasource 설정


3. Entity 클래스 및 Repository 생성
- Hospital (병원), Patient (환자), Visit (환자방문) entity 정의
    - @OneToMany, @ManyToOne 관계 설정
    - 코드, 코드그룹 entity 정의
- JpaRepository 를 상속하여 PatientRepository와 VisitRepository 생성


4. 기본 CRUD API 구현
- VisitController 와 PatientController 를 생성하여 기본적인 CRUD API를 생성
- endpoint 설계는 RESTful 방식


5. 기본 API 구현
- 환자 등록
    - 환자등록번호는 병원별로 중복되지 않도록 서버에서 생성 해주세요.
    - 환자 수정
    - 환자 삭제
    - 환자 조회
        - 환자id를 이용해 한 환자의 정보를 조회합니다. __[환자 Entity 의 모든 속성과 내원 정보를 목록으로 함께 조회해주세요.]__
    - 환자 목록 조회
        - 조회 데이터 : 이름, 환자등록번호, 설명, 생년원일, 휴대전화, __[최근방문]__


6. 환자 목록 조회 API 확장 - 동적 검색 조건
    - 동적 검색 조건 : 이름, 환자등록번호, 생년원일 각각을 조회조건으로 조회합니다. (querydsl 사용)


7. 환자 목록 조회 API 확장 - 페이징
    - pageSize (한 번에 조회하는 최대 항목 수), pageNo (1부터 시작, 페이지 번호)를 요청 인자로 전달받아서 페이징을 구현


8. spring restdocs을 적용