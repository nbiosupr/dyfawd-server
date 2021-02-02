# dyfawd-server ("운전 중에 잠이 오나?!" 프로젝트 - 서버)

[![시연영상](https://img.youtube.com/vi/5onWKiN6sK4/0.jpg)](https://youtu.be/5onWKiN6sK4)


## 기능
- 졸음운전을 감지하여 사용자를 깨우고 데이터를 수집
- 졸음운전 데이터를 실시간으로 수집하여 통계자료로 가공하고 제공하는 서비스
- 지역별 졸음 빈도, 시간별 졸음 빈도, 졸음 발생 시간과 위치 데이터 제공
- 실시간 졸음 빈도를 졸음 레벨로 계산하여 시각화
- OPEN API 형태로 데이터를 제공하고 API문서를 제공.

## 사용 기술
- (센서) 졸음감지 센서의 하드웨어는 NVDIA Jatson Nano(하드웨어) + GPS센서 + 카메라모듈 + 가속도센서.
- (센서) 졸음감지 센서의 로직은 파이썬으로 pynmea2, GPIO, urlib, ssl, smbus, OpenCV 사용.
- (센서, 서버) 졸음감지 센서와 수집 서버의 통신은 센서의 인증 문제를 해결하기 위해 JWT 토큰을 사용.
- (서버) 종단간 암호화를 위해 HTTPS 적용.
- (서버) 수집 및 통계 API서버는 AWS EC2 컨테이너에 스프링 부트를 기반으로 개발된 자바 어플리케이션.
- (서버) JWT 토큰과 HTTPS 통신 등을 위해 Spring Security 모듈을 적극활용하였고 Spring Data JPA(ORM)을 사용하여 DB를 제어.
- (서버) Swagger를 사용하여 REST API에 대한 문서를 제공
- (프론트) 프론트는 지도 시각화를 위해 Kakao Map API를 사용.

## 사용 도구
- JDK8
- Spring Boot 2
- MariaDB
- H2
- Spring data JPA
- Gradle
- Git, Github
- JUnit
- Retrofit
- Naver Reverse Geocoding API

## 개발 환경
- Windows 10 1909 x64
- IntelliJ Professional 2020.2
- Gradle
- AWS EC2, RDS
