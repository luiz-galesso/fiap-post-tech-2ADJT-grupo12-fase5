FROM maven:latest as build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests
#RUN mvn clean package

FROM openjdk:latest 
VOLUME /tmp
COPY --from=build /app/target/ms-item-1.0.0.jar .
ENTRYPOINT ["java","-jar","ms-item-1.0.0.jar"]