FROM maven:latest as build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:latest 
VOLUME /tmp
COPY --from=build /app/target/ms-carrinho-1.0.0.jar .
ENTRYPOINT ["java","-jar","ms-carrinho-1.0.0.jar"]