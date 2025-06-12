--liquibase formatted sql

--changeset leonux_hotela:create-enum-partner_status comments:"Cria o tipo ENUM para status do parceiro"
-- REMOVED: Replaced with VARCHAR in table definitions
--rollback SELECT 1;

--changeset leonux_hotela:create-enum-room_status comments:"Cria o tipo ENUM para status do quarto"
-- REMOVED: Replaced with VARCHAR in table definitions
--rollback SELECT 1;

--changeset leonux_hotela:create-enum-payment_method comments:"Cria o tipo ENUM para método de pagamento"
-- REMOVED: Replaced with VARCHAR in table definitions
--rollback SELECT 1;

--changeset leonux_hotela:create-enum-payment_status comments:"Cria o tipo ENUM para status do pagamento"
-- REMOVED: Replaced with VARCHAR in table definitions
--rollback SELECT 1;

--changeset leonux_hotela:create-enum-booking_status comments:"Cria o tipo ENUM para status da reserva"
-- REMOVED: Replaced with VARCHAR in table definitions
--rollback SELECT 1;

--changeset leonux_hotela:create-enum-auth_user_type comments:"Cria o tipo ENUM para tipo de usuário de autenticação"
-- REMOVED: Replaced with VARCHAR in table definitions
--rollback SELECT 1;