FROM ubuntu:latest
LABEL authors="volka"

ENV JAVA_TOOL_OPTIONS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=50.0 -XX:MaxDirectMemorySize=1g -XX:+UseG1GC"
ENTRYPOINT ["java", "-jar", "app.jar"]


