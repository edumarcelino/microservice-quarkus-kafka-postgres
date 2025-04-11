# Etapa de build
FROM quay.io/keycloak/keycloak:latest AS builder

# Ativar suporte a health check e métricas
ENV KC_HEALTH_ENABLED=true
ENV KC_METRICS_ENABLED=true

WORKDIR /opt/keycloak

# Gerar um certificado SSL autoassinado (somente para desenvolvimento)
RUN keytool -genkeypair -storepass password -storetype PKCS12 -keyalg RSA -keysize 2048 \
    -dname "CN=server" -alias server \
    -ext "SAN:c=DNS:localhost,IP:127.0.0.1" \
    -keystore conf/server.keystore

# Build do Keycloak
RUN /opt/keycloak/bin/kc.sh build

# Etapa final
FROM quay.io/keycloak/keycloak:latest

# Copia os arquivos da etapa de build
COPY --from=builder /opt/keycloak/ /opt/keycloak/

# Configuração do banco de dados será definida no docker-compose.yml
#ENV KC_HOSTNAME=keycloak

# Entrada padrão do container
ENTRYPOINT ["/opt/keycloak/bin/kc.sh"]
CMD ["start-dev", "--import-realm"]
