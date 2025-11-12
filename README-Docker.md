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

Notes:

- The Dockerfile uses the Maven wrapper (`mvnw`) to build the project inside the image. If you prefer to build the jar locally, you can replace the Dockerfile steps by copying the jar into the runtime image.
- The app exposes port 8080 by default. If your application is configured to use a different port, update `docker-compose.yml` and the `EXPOSE`/ports mapping.
