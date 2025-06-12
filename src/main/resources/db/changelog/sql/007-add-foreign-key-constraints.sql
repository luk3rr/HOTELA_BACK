--liquibase formatted sql

--changeset leonux_hotela:add-fk-partner-auth_credential comments:"Adiciona FK de partner para auth_credential"
ALTER TABLE partner
    ADD CONSTRAINT fk_partner_auth_credential
    FOREIGN KEY (auth_credential_id)
    REFERENCES auth_credential(id);
--rollback ALTER TABLE partner DROP CONSTRAINT fk_partner_auth_credential;

--changeset leonux_hotela:add-fk-partner-address comments:"Adiciona FK de partner para address"
ALTER TABLE partner
    ADD CONSTRAINT fk_partner_address
    FOREIGN KEY (address_id)
    REFERENCES address(id)
    ON DELETE RESTRICT;
--rollback ALTER TABLE partner DROP CONSTRAINT fk_partner_address;

--changeset leonux_hotela:add-fk-hotel-partner comments:"Adiciona FK de hotel para partner"
ALTER TABLE hotel
    ADD CONSTRAINT fk_hotel_partner
    FOREIGN KEY (partner_id)
    REFERENCES partner(id)
    ON DELETE CASCADE; -- Exemplo: se o parceiro for deletado, seus hotéis também são. Decisão de negócio!
--rollback ALTER TABLE hotel DROP CONSTRAINT fk_hotel_partner;

--changeset leonux_hotela:add-fk-hotel-address comments:"Adiciona FK de hotel para address"
ALTER TABLE hotel
    ADD CONSTRAINT fk_hotel_address
    FOREIGN KEY (address_id)
    REFERENCES address(id)
    ON DELETE RESTRICT; -- Exemplo: não permite deletar um endereço se um hotel o usa.
--rollback ALTER TABLE hotel DROP CONSTRAINT fk_hotel_address;

--changeset leonux_hotela:add-fk-room-hotel comments:"Adiciona FK de room para hotel"
ALTER TABLE room
    ADD CONSTRAINT fk_room_hotel
    FOREIGN KEY (hotel_id)
    REFERENCES hotel(id)
    ON DELETE CASCADE;
--rollback ALTER TABLE room DROP CONSTRAINT fk_room_hotel;

--changeset leonux_hotela:add-unique-room_hotel_id_room_number comments:"Adiciona restrição UNIQUE para hotel_id e room_number na tabela room"
ALTER TABLE room
    ADD CONSTRAINT uq_room_hotel_id_room_number
    UNIQUE (hotel_id, room_number);
--rollback ALTER TABLE room DROP CONSTRAINT uq_room_hotel_id_room_number;

--changeset leonux_hotela:add-fk-customer-auth_credential comments:"Adiciona FK de customer para auth_credential"
ALTER TABLE customer
    ADD CONSTRAINT fk_customer_auth_credential
    FOREIGN KEY (auth_credential_id)
    REFERENCES auth_credential(id);
--rollback ALTER TABLE customer DROP CONSTRAINT fk_customer_auth_credential;

--changeset leonux_hotela:add-fk-customer-address comments:"Adiciona FK de customer para address"
ALTER TABLE customer
    ADD CONSTRAINT fk_customer_address
    FOREIGN KEY (main_address_id)
    REFERENCES address(id)
    ON DELETE SET NULL;
--rollback ALTER TABLE customer DROP CONSTRAINT fk_customer_address;

--changeset leonux_hotela:add-unique-customer_document comments:"Adiciona restrição UNIQUE para document_id_type e document_id_value na tabela customer"
ALTER TABLE customer
    ADD CONSTRAINT uq_customer_document
    UNIQUE (document_id_type, document_id_value);
--rollback ALTER TABLE customer DROP CONSTRAINT uq_customer_document;

--changeset leonux_hotela:add-fk-booking-customer comments:"Adiciona FK de booking para customer"
ALTER TABLE booking
    ADD CONSTRAINT fk_booking_customer
    FOREIGN KEY (customer_id)
    REFERENCES customer(id);
--rollback ALTER TABLE booking DROP CONSTRAINT fk_booking_customer;

--changeset leonux_hotela:add-fk-booking-hotel comments:"Adiciona FK de booking para hotel"
ALTER TABLE booking
    ADD CONSTRAINT fk_booking_hotel
    FOREIGN KEY (hotel_id)
    REFERENCES hotel(id);
--rollback ALTER TABLE booking DROP CONSTRAINT fk_booking_hotel;

--changeset leonux_hotela:add-fk-booking-room comments:"Adiciona FK de booking para room"
ALTER TABLE booking
    ADD CONSTRAINT fk_booking_room
    FOREIGN KEY (room_id)
    REFERENCES room(id);
--rollback ALTER TABLE booking DROP CONSTRAINT fk_booking_room;

--changeset leonux_hotela:add-fk-payment-booking comments:"Adiciona FK de payment para booking"
ALTER TABLE payment
    ADD CONSTRAINT fk_payment_booking
    FOREIGN KEY (booking_id)
    REFERENCES booking(id);
--rollback ALTER TABLE payment DROP CONSTRAINT fk_payment_booking;

--changeset leonux_hotela:add-fk-review-booking comments:"Adiciona FK de review para booking"
ALTER TABLE review
    ADD CONSTRAINT fk_review_booking
    FOREIGN KEY (booking_id)
    REFERENCES booking(id);
--rollback ALTER TABLE review DROP CONSTRAINT fk_review_booking;

--changeset leonux_hotela:add-fk-review-customer comments:"Adiciona FK de review para customer"
ALTER TABLE review
    ADD CONSTRAINT fk_review_customer
    FOREIGN KEY (customer_id)
    REFERENCES customer(id);
--rollback ALTER TABLE review DROP CONSTRAINT fk_review_customer;

--changeset leonux_hotela:add-fk-review-hotel comments:"Adiciona FK de review para hotel"
ALTER TABLE review
    ADD CONSTRAINT fk_review_hotel
    FOREIGN KEY (hotel_id)
    REFERENCES hotel(id);
--rollback ALTER TABLE review DROP CONSTRAINT fk_review_hotel;

-- Adicionar ÍNDICES para colunas de FK comuns (melhora performance de JOINs)
-- Muitas FKs já são indexadas por padrão quando criadas, mas é bom garantir para as mais usadas.
--changeset leonux_hotela:add-indexes-fks comments:"Adiciona índices em colunas de chave estrangeira frequentemente usadas"
CREATE INDEX IF NOT EXISTS idx_hotel_partner_id ON hotel(partner_id);
CREATE INDEX IF NOT EXISTS idx_room_hotel_id ON room(hotel_id);
CREATE INDEX IF NOT EXISTS idx_booking_customer_id ON booking(customer_id);
CREATE INDEX IF NOT EXISTS idx_booking_hotel_id ON booking(hotel_id);
CREATE INDEX IF NOT EXISTS idx_booking_room_id ON booking(room_id);
CREATE INDEX IF NOT EXISTS idx_payment_booking_id ON payment(booking_id);
CREATE INDEX IF NOT EXISTS idx_review_customer_id ON review(customer_id);
CREATE INDEX IF NOT EXISTS idx_review_hotel_id ON review(hotel_id);
--rollback DROP INDEX IF EXISTS idx_hotel_partner_id; 
--rollback DROP INDEX IF EXISTS idx_room_hotel_id; 
--rollback DROP INDEX IF EXISTS idx_booking_customer_id; 
--rollback DROP INDEX IF EXISTS idx_booking_hotel_id; 
--rollback DROP INDEX IF EXISTS idx_booking_room_id; 
--rollback DROP INDEX IF EXISTS idx_payment_booking_id; 
--rollback DROP INDEX IF EXISTS idx_review_customer_id; 
--rollback DROP INDEX IF EXISTS idx_review_hotel_id;