--liquibase formatted sql

--changeset leonux_hotela:create-table-room comments:"Cria a tabela room"
CREATE TABLE room (
    id UUID NOT NULL DEFAULT gen_random_uuid(),
    hotel_id UUID NOT NULL, -- FK será adicionada depois
    room_type VARCHAR(255) NOT NULL,
    room_status VARCHAR(255) NOT NULL DEFAULT 'AVAILABLE',
    room_number VARCHAR(10) NOT NULL,
    floor INTEGER,  
    price_per_night DECIMAL(10,2) NOT NULL,
    capacity INTEGER NOT NULL,
    status VARCHAR(255) NOT NULL DEFAULT 'AVAILABLE',
    description TEXT,
    CONSTRAINT room_pkey PRIMARY KEY (id)
    -- UNIQUE (hotel_id, room_number) constraint será adicionada com as FKs ou como um addUniqueConstraint
);
--rollback DROP TABLE room;