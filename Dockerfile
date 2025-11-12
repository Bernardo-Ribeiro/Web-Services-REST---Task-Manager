# Etapa 1: Build
FROM eclipse-temurin:21 AS builder
WORKDIR /app

COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
COPY src/ src/

RUN chmod +x ./mvnw
RUN ./mvnw clean package -DskipTests

# Etapa 2: Run
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copia o .jar da etapa anterior
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]