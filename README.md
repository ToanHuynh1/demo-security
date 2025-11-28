# Demo Security - Spring Boot Advanced Techniques

## 🚀 Tổng quan

Dự án Spring Boot demo với các kỹ thuật nâng cao cho microservices và enterprise applications.

## 🛠️ Kỹ thuật đã triển khai

### 1. **Caching (Cache)**

- **Caffeine**: Local caching cho performance cao
- **Redis**: Distributed caching cho multi-instance
- **Configuration**: `CacheConfig.java`
- **Usage**: `@Cacheable` annotation trên methods

### 2. **Circuit Breaker & Resilience**

- **Resilience4j**: Circuit breaker, retry, rate limiter
- **Configuration**: `ResilienceConfig.java`
- **Usage**: `@CircuitBreaker`, `@Retry`, `@RateLimiter` annotations

### 3. **Message Queue (RabbitMQ)**

- **Asynchronous processing**: User events, file uploads, email notifications
- **Configuration**: `RabbitMQConfig.java`
- **Usage**: `EventPublisherService` để publish events

### 4. **Monitoring & Observability**

- **Micrometer**: Metrics collection
- **Prometheus**: Metrics export
- **Configuration**: `MonitoringConfig.java`
- **Usage**: `@Timed` annotation cho method metrics

### 5. **Distributed Tracing**

- **Spring Cloud Sleuth**: Request tracing
- **Zipkin**: Trace visualization
- **Configuration**: Application properties

### 6. **Kỹ thuật hiện có**

- ✅ Spring Security + JWT Authentication
- ✅ Spring Data JPA + Hibernate
- ✅ Flyway Database Migrations
- ✅ AOP Logging
- ✅ MapStruct DTO Mapping
- ✅ Spring Integration
- ✅ AWS S3 File Upload
- ✅ OpenAPI/Swagger Documentation
- ✅ Global Exception Handling
- ✅ Spring Boot Actuator

## 🐳 Infrastructure

### Docker Services

```yaml
- app: Spring Boot application
- db: MySQL 8.0 database
- redis: Redis cache server
- rabbitmq: Message queue broker
- prometheus: Metrics collection
- grafana: Metrics visualization
- zipkin: Distributed tracing
```

### Ports

- **8080**: Spring Boot application
- **3307**: MySQL (external access)
- **6379**: Redis
- **5672**: RabbitMQ AMQP
- **15672**: RabbitMQ Management UI
- **9090**: Prometheus
- **3000**: Grafana (admin/admin)
- **9411**: Zipkin

## 🚀 Chạy ứng dụng

### Development

```bash
mvn spring-boot:run
```

### Production (Docker)

```bash
docker-compose up --build
```

### Development with Monitoring (Recommended)

```bash
# Start monitoring stack
docker-compose -f docker-compose.monitoring.yml up -d

# Start application with monitoring
docker-compose -f docker-compose.yml -f docker-compose.override.yml up --build
```

### Monitoring Only

```bash
docker-compose -f docker-compose.monitoring.yml up -d
```

### Access Points

- **Application**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui/index.html

  - Username: `admin`
  - Password: `develop`

- **RabbitMQ Management**: http://localhost:15672

  - Username: `guest`
  - Password: `guest`

- **Prometheus**: http://localhost:9090
- **Grafana**: http://localhost:3000
  - Username: `admin`
  - Password: `admin`
- **Zipkin**: http://localhost:9411

- **Actuator Endpoints**:
  - Health: http://localhost:8080/actuator/health
  - Metrics: http://localhost:8080/actuator/metrics
  - Prometheus: http://localhost:8080/actuator/prometheus

## 📊 Demo Endpoints

### Caching

```bash
GET /api/demo/cached/{userId}
```

### Circuit Breaker + Rate Limiting

```bash
GET /api/demo/external-api?url=https://api.example.com
```

### Message Queue Events

```bash
POST /api/demo/events/user-created?userId=1&username=testuser
POST /api/demo/events/file-uploaded?fileName=test.jpg&fileUrl=https://s3.amazonaws.com/bucket/test.jpg
POST /api/demo/events/email?to=user@example.com&subject=Welcome&body=Hello World
```

## 🔧 Configuration

### Application Properties

```properties
# Redis
spring.data.redis.host=localhost
spring.data.redis.port=6379

# RabbitMQ
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672

# Circuit Breaker
resilience4j.circuitbreaker.instances.externalService.failureRateThreshold=50

# Monitoring
management.endpoints.web.exposure.include=health,info,metrics,prometheus
```

## 📈 Monitoring & Metrics

### Prometheus Metrics

- JVM memory, threads, GC
- HTTP request metrics
- Custom business metrics
- Circuit breaker status
- Cache hit/miss ratios
- RabbitMQ queue metrics

### Grafana Dashboards

- JVM Memory Usage
- HTTP Request Rate
- Circuit Breaker State
- Cache Hit Rate
- RabbitMQ Queue Messages

### Distributed Tracing

- Request tracing across services
- Zipkin integration for visualization
- Sleuth integration for trace IDs

### Monitoring Stack

```bash
# Start monitoring services
docker-compose -f docker-compose.monitoring.yml up -d

# View metrics in Grafana: http://localhost:3000
# View traces in Zipkin: http://localhost:9411
# View raw metrics in Prometheus: http://localhost:9090
```

## 🏗️ Architecture Patterns

### Applied Patterns

- **Repository Pattern**: Data access layer
- **Service Layer**: Business logic
- **DTO Pattern**: Data transfer objects
- **Observer Pattern**: Event publishing
- **Circuit Breaker**: Fault tolerance
- **Cache-Aside**: Caching strategy

### Microservices Ready

- Event-driven architecture
- Service discovery ready
- Configuration externalized
- Health checks implemented
- Metrics exported

## 🔒 Security Features

- JWT Authentication
- Role-based access control
- API rate limiting
- Circuit breaker protection
- Input validation
- CORS configuration

## 📚 Additional Techniques (Có thể thêm)

### Immediate Additions

1. **API Gateway**: Spring Cloud Gateway
2. **Service Discovery**: Eureka/Consul
3. **Config Server**: Spring Cloud Config
4. **OAuth2**: Enhanced authentication
5. **Database Sharding**: Multi-tenant support

### Advanced Features

1. **Event Sourcing**: Audit trail
2. **CQRS**: Read/write separation
3. **Saga Pattern**: Distributed transactions
4. **API Versioning**: Backward compatibility
5. **Feature Flags**: Dynamic configuration

### DevOps Enhancements

1. **Kubernetes**: Container orchestration
2. **Helm Charts**: Package management
3. **CI/CD Pipelines**: Automated deployment
4. **Log Aggregation**: ELK stack
5. **Chaos Engineering**: Resilience testing

## 🤝 Contributing

1. Fork the repository
2. Create feature branch
3. Add tests for new features
4. Ensure all tests pass
5. Submit pull request

## 📄 License

This project is licensed under the MIT License.
