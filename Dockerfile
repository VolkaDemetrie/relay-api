FROM ubuntu:latest
LABEL authors="volka"

ENTRYPOINT ["java", "-Xms1G", "-Xmx2G", "-XX:MaxDirectMemorySize=1G", "-jar", "app.jar"]


