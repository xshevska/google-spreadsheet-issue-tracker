FROM amazoncorretto:17-alpine as build
WORKDIR /workspace/app

RUN apk add --no-cache maven

COPY ../pom.xml .
COPY ../src src

RUN mvn clean package -DskipTests

FROM amazoncorretto:17-alpine
WORKDIR /app

COPY --from=build /workspace/app/target/*.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]
