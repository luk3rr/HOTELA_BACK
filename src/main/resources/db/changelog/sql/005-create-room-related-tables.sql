--liquibase formatted sql

--changeset leonux_hotela:create-table-room_type comments:"Cria a tabela room_type"
CREATE TABLE room_type (
    id UUID NOT NULL DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL,
    description TEXT,
    CONSTRAINT room_type_pkey PRIMARY KEY (id),
    CONSTRAINT room_type_name_key UNIQUE (name)
);
--rollback DROP TABLE room_type;

--changeset leonux_hotela:create-table-room comments:"Cria a tabela room"
CREATE TABLE room (
    id UUID NOT NULL DEFAULT gen_random_uuid(),
    hotel_id UUID NOT NULL, -- FK será adicionada depois
    room_type_id UUID NOT NULL, -- FK será adicionada depois
    room_number VARCHAR(10) NOT NULL,
    floor INTEGER,
    price_per_night DECIMAL(10,2) NOT NULL,
    capacity INTEGER NOT NULL,
    status room_status NOT NULL DEFAULT 'AVAILABLE',
    description TEXT,
    CONSTRAINT room_pkey PRIMARY KEY (id)
    -- UNIQUE (hotel_id, room_number) constraint será adicionada com as FKs ou como um addUniqueConstraint
);
--rollback DROP TABLE room;