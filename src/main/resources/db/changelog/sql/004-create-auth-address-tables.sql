--liquibase formatted sql

--changeset leonux_hotela:create-table-auth_credential comments:"Cria a tabela auth_credential"
CREATE TABLE auth_credential (
    id UUID NOT NULL DEFAULT gen_random_uuid(),
    login_email VARCHAR(254) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    user_type auth_user_type NOT NULL, -- Usando o tipo ENUM criado
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    last_login_at TIMESTAMPTZ,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT auth_credential_pkey PRIMARY KEY (id),
    CONSTRAINT auth_credential_login_email_key UNIQUE (login_email)
);
--rollback DROP TABLE auth_credential;

--changeset leonux_hotela:create-table-address comments:"Cria a tabela address"
CREATE TABLE address (
    id UUID NOT NULL DEFAULT gen_random_uuid(),
    street_address VARCHAR(255) NOT NULL,
    number VARCHAR(20),
    complement VARCHAR(100),
    neighborhood VARCHAR(100),
    city VARCHAR(100) NOT NULL,
    state_province VARCHAR(100) NOT NULL,
    postal_code VARCHAR(10) NOT NULL,
    country_code VARCHAR(2) NOT NULL DEFAULT 'BR',
    CONSTRAINT address_pkey PRIMARY KEY (id)
);
--rollback DROP TABLE address;