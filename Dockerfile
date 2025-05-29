# 빌드
FROM gradle:8.4-jdk21-alpine as builder

WORKDIR /app
COPY . .
RUN gradle clean bootJar --no-daemon

# 2단계: 런타임 - Amazon Corretto + Alpine
FROM amazoncorretto:21.0.7-alpine3.21
LABEL authors="volka"

# 필요한 경우 TLS 루트 인증서 설치 (일부 Alpine에는 빠져 있음)
# RUN apk add --no-cache ca-certificates

WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=50.0 -XX:MaxDirectMemorySize=1g -XX:+UseG1GC"
ENTRYPOINT ["java $JAVA_OPTS -jar app.jar"]