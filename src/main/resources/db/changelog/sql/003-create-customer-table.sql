--liquibase formatted sql

--changeset leonux_hotela:create-table-customer comments:"Cria a tabela customer"
CREATE TABLE customer (
    id UUID NOT NULL DEFAULT gen_random_uuid(),
    auth_credential_id UUID NOT NULL, -- FK será adicionada depois
    full_name VARCHAR(255) NOT NULL,
    contact_email_secondary VARCHAR(254),
    primary_phone VARCHAR(18) NOT NULL,
    birth_date DATE NOT NULL,
    document_id_type VARCHAR(255) NOT NULL,
    document_id_value VARCHAR(255) NOT NULL,
    main_address_id UUID, -- FK será adicionada depois
    CONSTRAINT customer_pkey PRIMARY KEY (id),
    CONSTRAINT customer_contact_email_secondary_key UNIQUE (contact_email_secondary),
    CONSTRAINT customer_auth_credential_id_key UNIQUE (auth_credential_id) -- Garante 1 para 1
    -- UNIQUE (document_id_type, document_id_value) constraint será adicionada com as FKs ou como um addUniqueConstraint
);
--rollback DROP TABLE customer;