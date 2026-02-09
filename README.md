# Reactrip Backend

여행 정보·일기·커뮤니티 기능을 제공하는 Spring Boot 기반 REST API 서버입니다.

## 기술 스택

| 구분 | 기술 |
|------|------|
| 언어 | Java 21 |
| 프레임워크 | Spring Boot 3.5.9 |
| 보안 | Spring Security, JWT (jjwt) |
| DB | Oracle (ojdbc11), JPA, MyBatis |
| 문서 | SpringDoc OpenAPI (Swagger UI) |
| 기타 | AWS S3, WebSocket, OSHI (시스템 메트릭), OkHttp |

## 주요 기능

- **인증/회원**: 로그인, 회원가입, JWT 기반 인증
- **여행지**: 지역·테마별 여행지 조회
- **일기**: 여행 일기 CRUD
- **관리자**: 회원/여행지/공지/댓글/일기 관리, 대시보드
- **라즈베리파이**: 서버 메트릭 수집·조회 (OSHI)

## 사전 요구사항

- **JDK 21**
- **Oracle Database** (연결 정보는 설정 파일에서 관리)
- **Gradle** (Wrapper 포함)

## 설정

1. 프로젝트 루트에 `application.yml`, `application-private.yml`을 두고 DB·JWT·S3 등 비공개 설정을 작성합니다.  
   (저장소에는 포함되지 않으며, `config.js` 등 민감 설정은 `.gitignore` 대상입니다.)

2. Oracle DB에 테이블·시퀀스를 생성한 뒤, 필요 시 시드/스키마 변경 스크립트를 실행합니다.
   - `scripts/seed-region-theme.sql` — 지역·테마 시드 데이터
   - `scripts/alter-notice-add-image.sql` — 공지 이미지 관련 스키마 변경

## 빌드 및 실행

```bash
# 빌드
./gradlew build

# 실행 (Spring Boot)
./gradlew bootRun
```

서버 기동 후 API 문서는 **SpringDoc Swagger UI**에서 확인할 수 있습니다.  
(예: `http://localhost:8080/swagger-ui.html` — 포트는 `application.yml` 기준)

## 프로젝트 구조 (요약)

```
src/main/java/com/kh/reactrip/
├── ReactripApplication.java          # 진입점, @EnableScheduling, @MapperScan
├── auth/                             # 로그인·JWT
├── member/                           # 회원 API
├── diary/                            # 일기 API
├── place/                            # 여행지 API
├── admin/                            # 관리자 API
│   ├── members/
│   ├── travel/
│   ├── notices/
│   ├── community/                    # 댓글, 일기 관리
│   └── adminDashboard/               # Raspberry 메트릭 수집
├── exception/                        # 전역 예외 처리
└── ...
src/main/resources/mapper/            # MyBatis XML 매퍼
scripts/                              # DB 시드·스키마 스크립트
```

## 라이선스

이 프로젝트는 데모/교육 목적의 Spring Boot 프로젝트입니다.
