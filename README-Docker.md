# Running the application with Docker

This repository contains a small Spring Boot application. The following files were added to run it inside Docker:

- `Dockerfile` - multi-stage build that compiles the application with the Maven wrapper and packages a JRE image with the built jar.
- `.dockerignore` - files to ignore when building the image.
- `docker-compose.yml` - basic compose file that builds and runs the image, mapping port 8080.

Quick start (Windows PowerShell):

```powershell
# Build the project jar locally (optional, the Dockerfile will build it too):
mvnw.cmd package -DskipTests

# Build the Docker image (from repository root):
docker build -t demo-security:latest .

# Run the container:
docker run --rm -p 8080:8080 demo-security:latest

# Or use docker-compose:
docker-compose up --build
```

## Spring Boot Actuator - Monitoring Endpoints

Ứng dụng này đã được tích hợp Spring Boot Actuator để giám sát và quản lý. Sau khi chạy ứng dụng, bạn có thể truy cập các endpoint sau:

### Health Check

- `GET /actuator/health` - Kiểm tra trạng thái tổng thể của ứng dụng
- Bao gồm kiểm tra database và các thành phần khác

### Application Info

- `GET /actuator/info` - Thông tin về ứng dụng (tên, phiên bản, mô tả)

### Metrics

- `GET /actuator/metrics` - Danh sách các metrics có sẵn
- `GET /actuator/metrics/{metric-name}` - Chi tiết metric cụ thể
- Ví dụ: `/actuator/metrics/login_attempts_total` - Số lần thử đăng nhập

### Environment

- `GET /actuator/env` - Thông tin về environment và cấu hình

### Mappings

- `GET /actuator/mappings` - Danh sách tất cả các endpoint HTTP

### Cấu hình bảo mật

- Trong production, nên cấu hình `management.endpoints.web.exposure.include` để chỉ expose các endpoint cần thiết
- Có thể thêm authentication cho các actuator endpoints

## Database Migration với Flyway

Ứng dụng sử dụng Flyway để quản lý database schema migrations. Tất cả migration scripts nằm trong `src/main/resources/db/migration/`.

### Quy tắc đặt tên Migration Scripts:

- `V{version}__{description}.sql` (VD: `V1__Initial_schema.sql`)
- Version phải là số nguyên hoặc sử dụng format như `1.2.3`

### Migration Scripts hiện có:

- `V1__Initial_schema.sql` - Tạo các bảng cơ bản (users, categories, courses, uploaded_files)
- `V2__Add_initial_data.sql` - Thêm dữ liệu mẫu
- `V3__Add_user_email.sql` - Thêm trường email cho users

### Lệnh Flyway hữu ích:

```bash
# Kiểm tra trạng thái migrations
./mvnw flyway:info

# Chạy migrations
./mvnw flyway:migrate

# Tạo baseline (cho database đã có dữ liệu)
./mvnw flyway:baseline

# Validate migrations
./mvnw flyway:validate

# Rollback (chỉ với Flyway Teams/Enterprise)
./mvnw flyway:undo
```

### Cấu hình Flyway:

- `spring.flyway.enabled=true` - Bật Flyway
- `spring.flyway.locations=classpath:db/migration` - Vị trí scripts
- `spring.flyway.baseline-on-migrate=true` - Tạo baseline tự động
- `spring.flyway.validate-on-migrate=true` - Validate checksums

```
Notes:

- The Dockerfile uses the Maven wrapper (`mvnw`) to build the project inside the image. If you prefer to build the jar locally, you can replace the Dockerfile steps by copying the jar into the runtime image.
- The app exposes port 8080 by default. If your application is configured to use a different port, update `docker-compose.yml` and the `EXPOSE`/ports mapping.
```
