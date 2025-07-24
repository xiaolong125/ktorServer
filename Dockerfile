# 使用Kotlin JVM基础镜像
FROM gradle:8.2.1-jdk17 AS builder

WORKDIR /app

# 拷贝项目文件
COPY . .

# 构建Fat Jar
RUN gradle clean shadowJar --no-daemon

# 运行阶段
FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
