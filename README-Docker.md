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

```
Notes:

- The Dockerfile uses the Maven wrapper (`mvnw`) to build the project inside the image. If you prefer to build the jar locally, you can replace the Dockerfile steps by copying the jar into the runtime image.
- The app exposes port 8080 by default. If your application is configured to use a different port, update `docker-compose.yml` and the `EXPOSE`/ports mapping.
```
