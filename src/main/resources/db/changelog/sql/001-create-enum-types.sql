--liquibase formatted sql

--changeset leonux_hotela:create-enum-partner_status comments:"Cria o tipo ENUM para status do parceiro"
CREATE TYPE partner_status AS ENUM (
    'ACTIVE',
    'INACTIVE'
);
--rollback DROP TYPE partner_status;

--changeset leonux_hotela:create-enum-room_status comments:"Cria o tipo ENUM para status do quarto"
CREATE TYPE room_status AS ENUM (
    'AVAILABLE',
    'BOOKED',
    'MAINTENANCE',
    'UNAVAILABLE'
);
--rollback DROP TYPE room_status;

--changeset leonux_hotela:create-enum-payment_method comments:"Cria o tipo ENUM para método de pagamento"
CREATE TYPE payment_method AS ENUM (
    'CREDIT_CARD',
    'DEBIT_CARD',
    'PIX',
    'BANK_TRANSFER',
    'PAYPAL',
    'CASH',
    'VOUCHER'
);
--rollback DROP TYPE payment_method;

--changeset leonux_hotela:create-enum-payment_status comments:"Cria o tipo ENUM para status do pagamento"
CREATE TYPE payment_status AS ENUM (
    'PENDING',
    'PAID',
    'FAILED',
    'REFUNDED',
    'PARTIALLY_REFUNDED',
    'CANCELLED'
);
--rollback DROP TYPE payment_status;

--changeset leonux_hotela:create-enum-booking_status comments:"Cria o tipo ENUM para status da reserva"
CREATE TYPE booking_status AS ENUM (
    'PENDING_CONFIRMATION',
    'CONFIRMED',
    'CANCELLED_BY_CUSTOMER',
    'CANCELLED_BY_HOTEL',
    'CHECKED_IN',
    'CHECKED_OUT',
    'COMPLETED',
    'NO_SHOW'
);
--rollback DROP TYPE booking_status;

--changeset leonux_hotela:create-enum-auth_user_type comments:"Cria o tipo ENUM para tipo de usuário de autenticação"
CREATE TYPE auth_user_type AS ENUM (
    'CUSTOMER',
    'PARTNER'
);
--rollback DROP TYPE auth_user_type;