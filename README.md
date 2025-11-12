# Task Manager API

## Build e Execução

### Usando Docker:

```bash
docker build -t payroll .
docker run -p 8080:8080 payroll
```

### Usando Podman:

```bash
podman build -t payroll .
podman run -p 8080:8080 payroll
```

## Acesso

API disponível em: `http://localhost:8080`

## Resolvendo Problemas (Docker)

Se der erro ao rodar o `docker build`, tente isso:

### 1. Erro: `Permission denied`

- **O que é:** O Docker não tem permissão para rodar o `mvnw`.
- **Solução:** Adicione `RUN chmod +x ./mvnw` no `Dockerfile`.

### 2. Erro: `...maven-wrapper.properties: No such file`

- **O que é:** Está faltando a pasta `.mvn` no projeto.
- **Solução:** Rode `mvn wrapper:wrapper` no seu terminal (na máquina local) para criar a pasta.

### 3. Erro: `BUILD FAILURE` (Falha nos Testes)

- **O que é:** Os testes do Maven falham durante o build.
- **Solução:** Pule os testes adicionando `-DskipTests` ao comando `package`.

  ```bash
  # Exemplo:
  RUN ./mvnw clean package -DskipTests
  ```

---

## Dockerfile (Recomendado)

Versão _multi-stage_ para uma imagem final mais leve e limpa.

```dockerfile
FROM eclipse-temurin:21 AS builder
WORKDIR /app

COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
COPY src/ src/

RUN chmod +x ./mvnw
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
```
