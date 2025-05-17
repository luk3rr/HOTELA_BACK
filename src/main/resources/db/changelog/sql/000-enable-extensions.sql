--liquibase formatted sql

--changeset leonux_hotela:001-enable-pgcrypto-extension comments:"Habilita a extensão pgcrypto para gen_random_uuid()"
CREATE EXTENSION IF NOT EXISTS "pgcrypto";
--rollback SELECT 1; -- Não há rollback simples para DROP EXTENSION se outros objetos dependerem dela, mas pode ser DROP EXTENSION IF EXISTS "pgcrypto";