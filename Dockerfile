FROM eclipse-temurin:21

WORKDIR /app

COPY . /app/

RUN ./mvnw clean package

EXPOSE 8080

CMD ["./mvnw", "spring-boot:run"]
