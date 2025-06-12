--liquibase formatted sql

--changeset leonux_hotela:create-table-booking comments:"Cria a tabela booking"
CREATE TABLE booking (
    id UUID NOT NULL DEFAULT gen_random_uuid(),
    customer_id UUID NOT NULL, -- FK
    hotel_id UUID NOT NULL, -- FK
    room_id UUID NOT NULL, -- FK
    checkin_date DATE NOT NULL,
    checkout_date DATE NOT NULL,
    number_of_guests INTEGER NOT NULL DEFAULT 1,
    status VARCHAR(255) NOT NULL DEFAULT 'PENDING_CONFIRMATION',
    special_requests TEXT,
    booked_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT booking_pkey PRIMARY KEY (id),
    CONSTRAINT check_booking_checkout_after_checkin CHECK (checkout_date > checkin_date),
    CONSTRAINT check_booking_guests CHECK (number_of_guests >= 1)
);
--rollback DROP TABLE booking;

--changeset leonux_hotela:create-table-payment comments:"Cria a tabela payment"
CREATE TABLE payment (
    id UUID NOT NULL DEFAULT gen_random_uuid(),
    booking_id UUID NOT NULL, -- FK
    external_transaction_id VARCHAR(100) NOT NULL,
    amount_paid DECIMAL(10,2) NOT NULL,
    payment_method VARCHAR(255) NOT NULL,
    status VARCHAR(255) NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    processed_at TIMESTAMPTZ,
    payment_details JSONB,
    CONSTRAINT payment_pkey PRIMARY KEY (id),
    CONSTRAINT payment_external_transaction_id_key UNIQUE (external_transaction_id)
);
--rollback DROP TABLE payment;

--changeset leonux_hotela:create-table-review comments:"Cria a tabela review"
CREATE TABLE review (
    id UUID NOT NULL DEFAULT gen_random_uuid(),
    booking_id UUID NOT NULL, -- FK
    customer_id UUID NOT NULL, -- FK
    hotel_id UUID NOT NULL, -- FK
    rating INTEGER NOT NULL,
    title VARCHAR(255),
    comment TEXT,
    is_anonymous BOOLEAN NOT NULL DEFAULT FALSE,
    reviewed_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT review_pkey PRIMARY KEY (id),
    CONSTRAINT review_booking_id_key UNIQUE (booking_id), -- Um review por reserva
    CONSTRAINT check_review_rating CHECK (rating >= 1 AND rating <= 5)
);
--rollback DROP TABLE review;