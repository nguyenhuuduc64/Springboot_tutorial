# Viecs - Online Recruitment Platform (Backend API)

Hệ thống API phục vụ nền tảng tuyển dụng trực tuyến, chịu trách nhiệm xử lý logic nghiệp vụ, phân quyền bảo mật cao cấp, lưu trữ đám mây, tích hợp các dịch vụ AI giải mã dữ liệu văn bản và tự động hóa quy trình phân tích hồ sơ ứng viên.

## 🚀 Tính năng cốt lõi

- **Kiến trúc RESTful API chuẩn:** Xây dựng trên nền tảng Spring Boot 3.2.5 (Java 17), tối ưu hóa cấu trúc phân tầng và tăng tốc biên dịch.
- **Bảo mật OAuth2 Resource Server:** Tích hợp `spring-boot-starter-oauth2-resource-server` và `nimbus-jose-jwt` xử lý mã hóa, giải mã định danh JWT, bảo mật luồng dữ liệu truyền tải giữa FE và BE.
- **Tích hợp Trí tuệ nhân tạo (Spring AI):** Sử dụng `spring-ai-openai-spring-boot-starter` hỗ trợ phân tích thông tin tuyển dụng và đề xuất công việc thông minh.
- **Trích xuất dữ liệu thông minh (OCR):** Tích hợp `google-cloud-vision` tự động quét, đọc và bóc tách dữ liệu từ các file CV định dạng ảnh hoặc tài liệu PDF của ứng viên sang dữ liệu thô.
- **Ánh xạ dữ liệu tối ưu (Data Mapping):** Sử dụng `mapstruct` để chuyển đổi tự động và hiệu năng cao giữa hệ Entity dữ liệu (`spring-boot-starter-data-jpa`) và các đối tượng truyền tải DTO.
- **Giám sát hệ thống:** Tích hợp `spring-boot-starter-actuator` cung cấp các endpoint kiểm tra trạng thái sức khỏe (Healthcheck) và hiệu năng vận hành của ứng dụng.

## 📁 Thư viện công nghệ chính (Tech Stack & Dependencies)

Chi tiết hệ thống thư viện lõi khai báo trong `pom.xml`:

| Nhóm chức năng | Thư viện / Artifact sử dụng |
| :--- | :--- |
| **Framework Core** | Spring Boot 3.2.5, Spring Web, Spring Boot DevTools |
| **Bảo mật / Auth** | Spring Security Crypto, OAuth2 Resource Server, Nimbus JOSE JWT |
| **Trí tuệ nhân tạo** | Spring AI OpenAI Starter, Google Cloud Vision |
| **Dữ liệu & Lưu trữ**| Spring Data JPA, MySQL Connector-J, Google Cloud Datastore |
| **Microservices** | Spring Cloud Starter OpenFeign (Hỗ trợ gọi API ngoài) |
| **Tiện ích biên dịch**| Lombok 1.18.30, MapStruct 1.5.5.Final, Validation |

## 🛠️ Hướng dẫn cài đặt và chạy ứng dụng

### 1. Tải mã nguồn về máy cục bộ
```bash
git clone [https://github.com/nguyenhuuduc64/viecs-springboot-BE.git](https://github.com/nguyenhuuduc64/viecs-springboot-BE.git)
cd viecs-springboot-BE

 Tạo hoặc cập nhật cấu hình kết nối cơ sở dữ liệu và API Key:
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/viecs_db?useSSL=false&serverTimezone=UTC
    username: your_db_username
    password: your_db_password
  ai:
    openai:
      api-key: your_openai_api_key
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: your_auth_issuer_uri
# Cài đặt thư viện và biên dịch mã nguồn
./mvnw clean install

# Khởi chạy ứng dụng
./mvnw spring-boot:run

