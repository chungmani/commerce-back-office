# 🛒 Commerce Back Office

이커머스 백오피스 시스템

Spring Boot 기반으로 고객, 상품, 주문 및 관리자 기능을 관리하는 백오피스 시스템입니다.

---

## 📌 프로젝트 목표

- 관리자 및 고객 관리
- 상품 등록 및 관리
- 주문 관리 및 주문 취소
- 관리자 역할(Role)에 따른 권한 관리
- RESTful API 설계 및 구현
- Git Branch / PR 기반 협업 방식 연습

---

## 🛠️ Tech Stack

- Java
- Spring Boot
- Spring Data JPA
- MySQL
- Gradle
- Git / GitHub

---

## 📐 ERD

![ERD](docs/erd.png)

> ERD 파일은 `docs` 디렉토리에서 관리합니다.

---

## 📡 REST API

### 🔐 Admin

#### 관리자 회원가입

```htt
POST /auth/admins
```

관리자 계정을 생성합니다.

#### 관리자 로그인

```http
POST /auth/admins/login
```

관리자 로그인을 처리합니다.

#### 관리자 목록 조회

```http
GET /auth/admins
```

슈퍼 관리자가 전체 관리자 목록을 조회합니다.

#### 내 프로필 조회

```http
GET /admins/me
```

로그인한 관리자가 자신의 프로필을 조회합니다.

#### 관리자 정보 수정

```http
PATCH /auth/admins/{adminId}
```

관리자 정보를 수정합니다.

#### 관리자 역할 변경

```http
PATCH /auth/admins/{adminId}/role
```

관리자의 역할을 변경합니다.

#### 관리자 상태 변경

```http
PATCH /auth/admins/{adminId}/state
```

관리자의 상태를 변경합니다.

#### 관리자 삭제

```http
DELETE /auth/admins/{adminId}
```

관리자를 삭제합니다.

---

### 👤 Customer

#### 고객 목록 조회

```http
GET /customers
```

고객 목록을 조회합니다.

#### 고객 상세 조회

```http
GET /customers/{customerId}
```

특정 고객의 상세 정보를 조회합니다.

#### 고객 정보 수정

```http
PATCH /customers/{customerId}
```

고객 정보를 수정합니다.

#### 고객 상태 변경

```http
PATCH /customers/{customerId}/state
```

고객의 상태를 변경합니다.

#### 고객 삭제

```http
DELETE /customers/{customerId}
```

고객을 삭제합니다.

---

### 📦 Product

#### 상품 등록

```http
POST /products
```

관리자가 새로운 상품을 등록합니다.

#### 상품 목록 조회

```http
GET /products
```

상품 목록을 조회합니다.

#### 상품 상세 조회

```http
GET /products/{productId}
```

특정 상품의 상세 정보를 조회합니다.

#### 상품 정보 수정

```http
PATCH /products/{productId}
```

상품 정보를 수정합니다.

#### 상품 재고 수정

```http
PATCH /products/{productId}/stock
```

상품의 재고 수량을 수정합니다.

#### 상품 상태 변경

```http
PATCH /products/{productId}/state
```

상품의 상태를 변경합니다.

#### 상품 삭제

```http
DELETE /products/{productId}
```

상품을 삭제합니다.

---

### 🧾 Order

#### 주문 생성

```http
POST /orders
```

고객의 주문을 생성합니다.

주문 생성 시 결제 당시의 상품명과 상품 가격을 주문 정보에 저장합니다.

#### 주문 목록 조회

```http
GET /orders
```

주문 목록을 조회합니다.

#### 주문 상세 조회

```http
GET /orders/{orderId}
```

특정 주문의 상세 정보를 조회합니다.

#### 주문 상태 변경

```http
PATCH /orders/{orderId}
```

주문의 상태를 변경합니다.

#### 주문 취소

```http
PATCH /orders/{orderId}/cancel
```

주문을 취소하고 취소 사유를 저장합니다.

#### 주문 취소 Request

```json
{
  "cancelReason": "단순 변심"
}
```

#### 주문 취소 정책

- `PREPARING` 상태의 주문만 취소할 수 있습니다.
- `SHIPPING` 상태의 주문은 취소할 수 없습니다.
- `DELIVERED` 상태의 주문은 취소할 수 없습니다.
- 주문 취소 시 주문 상태를 `CANCELLED`로 변경합니다.
- 주문 취소 시 주문 수량만큼 상품 재고를 복구합니다.
- 단종된 상품은 재고는 복구하지만 상품 상태는 `DISCONTINUED`를 유지합니다.

---

## 🔑 Role & Permission

관리자는 역할에 따라 접근 가능한 기능이 다릅니다.

| 기능 | SUPER_ADMIN | OPERATION_ADMIN | CS_ADMIN |
| --- | :---: | :---: | :---: |
| 관리자 관리 | ✅ | ❌ | ❌ |
| 고객 관리 | ❌ | ❌ | ✅ |
| 상품 관리 | ❌ | ✅ | ❌ |
| 주문 조회 | ✅ | ✅ | ✅ |
| 주문 취소 | ❌ | ✅ | ✅ |

### 관리자 역할

- `SUPER_ADMIN` : 전체 관리자 관리
- `OPERATION_ADMIN` : 상품 및 주문 운영 관리
- `CS_ADMIN` : 고객 및 주문 관련 CS 관리

---

## 🌿 Git Branch Strategy

프로젝트는 Git Flow와 유사한 브랜치 전략을 사용합니다.

```text
main
  ↑
develop
  ↑
feature/*
```

### main

배포 가능한 안정적인 코드를 관리합니다.

### develop

개발된 기능들을 통합하는 브랜치입니다.

### feature

특정 기능을 개발하기 위한 브랜치입니다.

브랜치 예시:

```text
feature/admin-signup
feature/customer-api
feature/product-create
feature/order-cancel
```

---

## 📝 Commit Convention

커밋 메시지는 다음 형식을 사용합니다.

```text
<type>: <description>
```

### Commit Type

| Type | 설명 |
| --- | --- |
| `feat` | 새로운 기능 추가 |
| `fix` | 버그 수정 |
| `refactor` | 코드 리팩토링 |
| `docs` | 문서 수정 |
| `test` | 테스트 코드 추가 및 수정 |
| `chore` | 설정 및 기타 작업 |

### Commit 예시

```text
feat: 관리자 회원가입 API 구현
fix: 주문 취소 시 재고 복구 오류 수정
refactor: 주문 서비스 로직 개선
docs: API 명세 수정
test: 상품 조회 테스트 추가
chore: 프로젝트 초기 설정
```

---

## 🔀 Pull Request Convention

PR 제목은 다음 형식을 사용합니다.

```text
[TYPE] 작업 내용
```

예시:

```text
[FEATURE] 관리자 회원가입 API 구현
[FIX] 주문 취소 시 재고 복구 오류 수정
[REFACTOR] 주문 서비스 로직 개선
```

### PR 작성 템플릿

```markdown
## 📌 개요

작업 내용을 작성합니다.

## 🔨 변경사항

- 변경사항 1
- 변경사항 2
- 변경사항 3

## 🧪 테스트

- 테스트 내용 1
- 테스트 내용 2

## 📎 참고사항

추가로 전달할 내용을 작성합니다.
```

---

## 🔄 Development Workflow

기능 개발은 다음 순서로 진행합니다.

```text
1. develop에서 feature 브랜치 생성
            ↓
2. 기능 개발
            ↓
3. Commit
            ↓
4. GitHub에 Push
            ↓
5. Pull Request 생성
            ↓
6. Code Review
            ↓
7. develop으로 Squash Merge
            ↓
8. 개발 완료 후 main으로 PR
            ↓
9. main으로 Squash Merge
```

---

## 📁 Project Structure

```text
src
├── main
│   ├── java
│   │   └── com.example.commercebackoffice
│   │       ├── controller
│   │       ├── service
│   │       ├── repository
│   │       ├── entity
│   │       ├── dto
│   │       └── ...
│   │
│   └── resources
│       └── application.properties
│
└── test
    └── java
```

---

## 📚 Documentation

- [ERD](docs/erd.png)
- REST API 명세
- Git Convention
- Branch Strategy
- Pull Request Convention