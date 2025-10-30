# Payroll API

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