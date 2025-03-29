-- Cria o usuário Keycloak
CREATE USER keycloak WITH ENCRYPTED PASSWORD 'keycloak';

-- Cria o banco de dados e define o usuário keycloak como dono
CREATE DATABASE keycloak OWNER keycloak;

-- Conecta ao banco de dados keycloak
\c keycloak;

-- Garante que o usuário keycloak seja dono do schema public
ALTER SCHEMA public OWNER TO keycloak;

-- Concede todas as permissões para keycloak no schema public
GRANT ALL PRIVILEGES ON SCHEMA public TO keycloak;

-- Concede privilégios para tabelas existentes e futuras no schema public
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO keycloak;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO keycloak;

-- Define privilégios padrão para objetos futuros
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON TABLES TO keycloak;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON SEQUENCES TO keycloak;
