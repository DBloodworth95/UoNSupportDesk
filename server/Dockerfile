FROM gradle:6.8-jdk11

WORKDIR /app

COPY . .

RUN gradle shadowJar

RUN ls

ENTRYPOINT ["java", "-jar", "build/libs/server-1.0-all.jar"]