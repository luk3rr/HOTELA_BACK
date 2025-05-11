--liquibase formatted sql

--changeset leonux_hotela:create-table-partner comments:"Cria a tabela partner"
CREATE TABLE partner (
    id UUID NOT NULL DEFAULT gen_random_uuid(),
    auth_credential_id UUID NOT NULL, -- FK será adicionada depois
    company_name VARCHAR(255) NOT NULL,
    legal_name VARCHAR(255),
    cnpj VARCHAR(18) NOT NULL,
    primary_contact_email VARCHAR(254),
    primary_phone VARCHAR(18) NOT NULL,
    address_id UUID, -- FK será adicionada depois
    representative_name VARCHAR(255),
    representative_email VARCHAR(254),
    representative_phone VARCHAR(18),
    contract_signed_at DATE,
    status partner_status NOT NULL DEFAULT 'ACTIVE',
    notes TEXT,
    CONSTRAINT partner_pkey PRIMARY KEY (id),
    CONSTRAINT partner_cnpj_key UNIQUE (cnpj),
    CONSTRAINT partner_primary_contact_email_key UNIQUE (primary_contact_email),
    CONSTRAINT partner_auth_credential_id_key UNIQUE (auth_credential_id) -- Garante 1 para 1
);
--rollback DROP TABLE partner;

--changeset leonux_hotela:create-table-hotel comments:"Cria a tabela hotel"
CREATE TABLE hotel (
    id UUID NOT NULL DEFAULT gen_random_uuid(),
    partner_id UUID NOT NULL, -- FK será adicionada depois
    name VARCHAR(255) NOT NULL,
    address_id UUID NOT NULL, -- FK será adicionada depois
    main_phone VARCHAR(18) NOT NULL,
    main_email VARCHAR(254),
    website VARCHAR(255),
    description TEXT,
    star_rating DECIMAL(2,1) NOT NULL DEFAULT 0.0,
    standard_checkin_time TIME DEFAULT '14:00:00',
    standard_checkout_time TIME DEFAULT '12:00:00',
    latitude DECIMAL(10,8) NOT NULL,
    longitude DECIMAL(11,8) NOT NULL,
    CONSTRAINT hotel_pkey PRIMARY KEY (id),
    CONSTRAINT hotel_main_email_key UNIQUE (main_email),
    CONSTRAINT check_hotel_star_rating CHECK (star_rating >= 0.0 AND star_rating <= 5.0)
);
--rollback DROP TABLE hotel;