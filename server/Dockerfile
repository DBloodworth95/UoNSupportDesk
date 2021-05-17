FROM gradle:6.8-jdk11 as builder

WORKDIR /app

COPY . .

RUN gradle shadowJar

ENTRYPOINT ["java", "-jar", "uon.jar"]