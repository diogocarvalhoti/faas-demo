FROM --platform=linux/arm64 alpine:3.19
# FROM --platform=linux/amd64 alpine:3.19

# Instalar bibliotecas necessárias para binários nativos
RUN apk add --no-cache libc6-compat zlib

# Copiar o binário nativo (já buildado localmente para Linux)
COPY target/function /app/function

# Tornar executável
RUN chmod +x /app/function

# Expor porta padrão do Spring Boot
EXPOSE 8080

# Usar usuário não-root (segurança)
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

# Executar o binário nativo
ENTRYPOINT ["/app/function"]