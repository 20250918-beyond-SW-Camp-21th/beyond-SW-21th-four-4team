# 한화시스템 Beyond SW 21기 프론트엔드 프로젝트 - 4MAC

---
# 😄 팀소개
|[김세현](https://github.com/sekong11)|[양승재](https://github.com/DMXZC)|[이상준](https://github.com/Ongsaem0)|[정규원](https://github.com/Gyuwon-Jung)|
|:-:|:-:|:-:|:-:|
| <img width="300" alt="image" src="https://github.com/user-attachments/assets/43127b72-a3e6-40fc-9bbe-db60160c1bf5" /> | <img width="300" alt="image" src="https://github.com/user-attachments/assets/9555e12a-6aef-4370-84ed-d414ee7e4987" /> | <img width="300" alt="image" src="https://github.com/user-attachments/assets/2533ae0d-629f-4337-822d-7c138a297369" />| <img width="300" alt="image" src="https://github.com/user-attachments/assets/a740436c-5a48-4943-bfd4-493baa530e0a" /> |


---
# 🛠️ 기술 스택
<!-- Tech Stack Badges -->
🖥 Backend  
![Java](https://img.shields.io/badge/Java%2017-007396?style=flat-square&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=flat-square&logo=springboot&logoColor=white)
![Spring](https://img.shields.io/badge/Spring-6DB33F?style=flat-square&logo=spring&logoColor=white)
![REST API](https://img.shields.io/badge/RESTful%20API-000000?style=flat-square&logo=fastapi&logoColor=white)

🌐 Frontend  
![Vue.js](https://img.shields.io/badge/Vue.js%203-4FC08D?style=flat-square&logo=vuedotjs&logoColor=white)
![Axios](https://img.shields.io/badge/Axios-5A29E4?style=flat-square&logo=axios&logoColor=white)

🗄 Database  
![MariaDB](https://img.shields.io/badge/MariaDB-003545?style=flat-square&logo=mariadb&logoColor=white)
![MinIO](https://img.shields.io/badge/MinIO-C72E49?style=flat-square&logo=minio&logoColor=white)

🛜 Infrastructure  
![Docker](https://img.shields.io/badge/Docker-2496ED?style=flat-square&logo=docker&logoColor=white)
![Kubernetes](https://img.shields.io/badge/Kubernetes-326CE5?style=flat-square&logo=kubernetes&logoColor=white)
![Nginx](https://img.shields.io/badge/Nginx-009639?style=flat-square&logo=nginx&logoColor=white)
![MinIO](https://img.shields.io/badge/MinIO-C72E49?style=flat-square&logo=minio&logoColor=white)

🛠 CI / CD  
![Jenkins](https://img.shields.io/badge/Jenkins-D24939?style=flat-square&logo=jenkins&logoColor=white)
![Argo CD](https://img.shields.io/badge/Argo%20CD-FE4C00?style=flat-square&logo=argo&logoColor=white)

🔧 Collaboration & Tools  
![Git](https://img.shields.io/badge/Git-F05032?style=flat-square&logo=git&logoColor=white)
![GitHub](https://img.shields.io/badge/GitHub-181717?style=flat-square&logo=github&logoColor=white)
![Jira](https://img.shields.io/badge/Jira-0052CC?style=flat-square&logo=jira&logoColor=white)
![Discord](https://img.shields.io/badge/Discord-5865F2?style=flat-square&logo=discord&logoColor=white)

---
# ℹ️ 프로젝트 소개
## 1. 프로젝트 개요

본 프로젝트는 웹 기반 서비스 Yeobeul & Pabeul을 대상으로,
실제 운영 환경에 가까운 인프라 구성과 CI/CD 파이프라인을 설계·구현하는 것을 목표로 한 DevOps 프로젝트이다.

애플리케이션 기능 구현에 집중하기보다는,
컨테이너 기반 실행 환경 구성, 자동 빌드 및 배포, Kubernetes 운영 환경 구축을 통해
서비스를 어떻게 안정적으로 배포하고 운영할 수 있는지에 초점을 맞추었다.

여행 블로그 형태의 웹 애플리케이션은
이러한 인프라 및 배포 구조를 검증하기 위한 대상 서비스로 사용되었다.

## 2. 프로젝트 배경

기존 팀 프로젝트에서는 주로 로컬 환경에서 실행하거나,
배포 과정을 수동으로 진행하면서 환경 차이로 인한 오류를 자주 경험하였다.

이에 따라 본 프로젝트에서는
**“개발이 완료된 서비스를 실제로 운영한다면 어떤 준비가 필요할까?”**라는 질문을 출발점으로 삼아,

- 컨테이너 기반 실행 환경
- 빌드 및 배포 자동화
- Kubernetes 기반 운영 환경
- CI/CD 파이프라인을 통한 지속적인 배포 흐름<br>

을 직접 구성하며 DevOps 및 Infrastructure 전반에 대한 경험을 목표로 프로젝트를 진행하였다.

### 🎯 인프라 & 배포 목표

- Docker를 활용한 백엔드 / 프론트엔드 애플리케이션 컨테이너화
- Kubernetes 기반 서비스 배포 및 관리
- Nginx를 이용한 Reverse Proxy 및 트래픽 처리
- MinIO(S3 호환 스토리지)를 활용한 이미지 저장 구조 구성

### 🔁 CI / CD 목표

- Jenkins를 이용한 애플리케이션 빌드 및 이미지 생성 자동화
- Argo CD를 활용한 GitOps 기반 Kubernetes 배포
- 코드 변경 → 자동 빌드 → 자동 배포 흐름 구축

# 💡 주요 기능

본 프로젝트에서 사용한 애플리케이션은
여행 후기 작성 및 조회 기능을 제공하는 웹 서비스로,
DevOps 파이프라인과 인프라 구조를 검증하기 위한 역할을 수행한다.
- Backend: 게시글 작성(Create) / 조회(Read) API 제공
- Frontend: Vue.js 기반 화면 구성
- Storage: MinIO를 활용한 이미지 업로드 관리
- Auth: JWT 기반 사용자 인증

>기능 구현 자체가 목적이 아니라,
인프라 및 배포 구조를 검증하기 위한 수단으로 활용되었다.

---

## 🚀 빌드 성능 개선 (Dockerfile vs Jib)

Jenkins 파이프라인에서 이미지 빌드/푸시 단계의 소요 시간을 비교하여,
컨테이너 이미지 생성 방식을 Dockerfile 기반에서 Jib 기반으로 개선하였다.

<img width="891" height="312" alt="스크린샷 2026-01-20 오후 5 09 39" src="https://github.com/user-attachments/assets/6f42071f-2330-4ff0-bf5c-783ca4caa725" />

| 방식 | Jenkins `Docker Build and Push` 단계 | 결과 |
|---|---:|---|
| Dockerfile 기반 빌드 | 42s | 기준 |
| Jib 기반 빌드 | 22s | 약 20s 단축 (약 2배 개선) |

### 개선 이유
- Dockerfile 방식은 `COPY` 및 빌드 단계에서 레이어 캐시가 자주 무효화되어 빌드 시간이 증가할 수 있다.
- Jib는 Java 애플리케이션(Dependencies / Resources / Classes)을 레이어로 분리하여,
  변경되지 않은 레이어는 재사용하고 변경된 레이어만 갱신함으로써 빌드 및 푸시 시간을 단축한다.

> 본 개선의 효과는 Jenkins 파이프라인 단계별 실행 시간에서 확인되었으며,
> 빌드 시간 차이의 대부분은 `Docker Build and Push` 단계에서 발생했다.

---
# 파이프 라인 구조도
<img width="825" height="359" alt="image" src="https://github.com/user-attachments/assets/8a3f8aa6-6609-4221-bc38-209c296b04ff" />

---
# 요구사항 명세서
<details>
<summary>요구사항 명세서  </summary>
<div markdown="1">

<img width="1738" height="154" alt="image" src="https://github.com/user-attachments/assets/9831bbf5-d7f7-4bfb-bd42-35246b1cde6a" />


</div>
</details>

---
