Enum partner_status {
  ACTIVE
  INACTIVE
}

Enum room_status {
  AVAILABLE [note: 'Disponível para reserva']
  BOOKED [note: 'Reservado e ocupado ou aguardando check-in']
  MAINTENANCE [note: 'Em manutenção, indisponível']
  UNAVAILABLE [note: 'Indisponível por outros motivos']
}

Enum payment_method {
  CREDIT_CARD
  DEBIT_CARD
  PIX
  BANK_TRANSFER
  PAYPAL
  CASH
  VOUCHER
}

Enum payment_status {
  PENDING   [note: 'Aguardando processamento ou confirmação']
  PAID      [note: 'Pagamento confirmado com sucesso']
  FAILED    [note: 'Pagamento falhou']
  REFUNDED  [note: 'Pagamento totalmente reembolsado']
  PARTIALLY_REFUNDED [note: 'Pagamento parcialmente reembolsado']
  CANCELLED [note: 'Pagamento cancelado antes da conclusão efetiva']
}

Enum booking_status {
  PENDING_CONFIRMATION [note: 'Reserva aguardando confirmação (ex: pagamento, aprovação)']
  CONFIRMED            [note: 'Reserva confirmada e garantida']
  CANCELLED_BY_CUSTOMER
  CANCELLED_BY_HOTEL
  CHECKED_IN           [note: 'Hóspede realizou check-in']
  CHECKED_OUT          [note: 'Hóspede realizou check-out']
  NO_SHOW              [note: 'Hóspede não compareceu para o check-in']
}

Enum auth_user_type {
  CUSTOMER
  PARTNER
}

Table auth_credential {
  id uuid [pk, default: `gen_random_uuid()`, note: 'Chave primária única para cada credencial']
  login_email varchar(254) [unique, not null, note: 'Email de login, único globalmente. Max 254 para compatibilidade.']
  password_hash varchar(255) [not null, note: 'Hash da senha (ex: bcrypt)']
  user_type auth_user_type [not null, note: 'Indica se a credencial é de um CUSTOMER ou PARTNER']
  is_active boolean [not null, default: true, note: 'Se a conta de login está ativa']
  last_login_at timestamptz [note: 'Atualizado programaticamente no login bem-sucedido']
  created_at timestamptz [not null, default: `now()`, note: 'Quando a credencial foi criada']
  updated_at timestamptz [not null, default: `now()`, note: 'Quando a credencial (ex: senha, is_active) foi alterada']
}

Table address {
  id uuid [pk, default: `gen_random_uuid()`]
  street_address varchar(255) [not null]
  number varchar(20) [note: 'Número do endereço, pode conter letras ex: 123B']
  complement varchar(100)
  neighborhood varchar(100)
  city varchar(100) [not null]
  state_province varchar(100) [not null, note: 'Estado, província ou região']
  postal_code varchar(10) [not null, note: 'Ex: 12345-678 para CEP BR. Ajustar tamanho se internacional.']
  country_code varchar(2) [not null, default: 'BR', note: 'ISO 3166-1 alpha-2 (ex: BR, US, PT)']
}

Table partner {
  id uuid [pk, default: `gen_random_uuid()`]
  auth_credential_id uuid [unique, not null, ref: > auth_credential.id, note: 'FK para as credenciais de login do parceiro. Relação 1:1']
  company_name varchar(255) [not null, note: 'Nome fantasia ou principal da empresa parceira']
  legal_name varchar(255) [note: 'Razão Social, se aplicável e diferente do company_name']
  cnpj varchar(18) [unique, not null, note: 'CNPJ formatado: XX.XXX.XXX/XXXX-XX']
  primary_contact_email varchar(254) [unique, note: 'Email de contato principal da empresa (pode ser diferente do login_email)']
  primary_phone varchar(18) [not null, note: 'Telefone principal formatado, ex: +55 (XX) XXXXX-XXXX']
  address_id uuid [ref: > address.id, note: 'FK para o endereço principal do parceiro. Pode ser NULL? Decisão de negócio.']
  representative_name varchar(255) [note: 'Nome do representante legal ou contato principal da empresa']
  representative_email varchar(254) [note: 'Email do representante']
  representative_phone varchar(18) [note: 'Telefone do representante']
  contract_signed_at date [note: 'Data em que o contrato foi assinado']
  status partner_status [not null, default: 'ACTIVE']
  notes text
}

Table hotel {
  id uuid [pk, default: `gen_random_uuid()`]
  partner_id uuid [not null, ref: > partner.id]
  name varchar(255) [not null]
  address_id uuid [not null, ref: > address.id, note: 'Endereço do hotel. Um hotel DEVE ter um endereço.']
  main_phone varchar(18) [not null]
  main_email varchar(254) [unique]
  website varchar(255)
  description text
  star_rating decimal(2,1) [not null, default: 0.0, note: 'Ex: 3.5. Implementar com CHECK (star_rating >= 0.0 AND star_rating <= 5.0)']
  latitude decimal(10,8) [not null, note: 'Precisão para coordenadas geográficas']
  longitude decimal(11,8) [not null, note: 'Precisão para coordenadas geográficas']
  indexes {
    (partner_id)
  }
}

Table room_type {
  id uuid [pk, default: `gen_random_uuid()`]
  name varchar(100) [unique, not null, note: 'Ex: Standard Solteiro, Casal Deluxe, Suíte Presidencial Vista Mar']
  description text
}

Table room {
  id uuid [pk, default: `gen_random_uuid()`]
  hotel_id uuid [not null, ref: > hotel.id]
  room_type_id uuid [not null, ref: > room_type.id]
  room_number varchar(10) [not null, note: 'Número ou identificador do quarto no hotel (ex: 101, 203A, "Suíte Lagoa")']
  floor int [note: 'Andar onde o quarto está localizado, se aplicável']
  price_per_night decimal(10,2) [not null, note: 'Preço base da diária para este quarto']
  capacity int [not null, note: 'Capacidade máxima de hóspedes para este quarto específico']
  status room_status [not null, default: 'AVAILABLE']
  description text [note: 'Descrição ou características específicas deste quarto individual']
  indexes {
    (hotel_id, room_number) [unique, note: 'Garante que o número do quarto é único dentro de cada hotel']
  }
}

Table customer {
  id uuid [pk, default: `gen_random_uuid()`]
  auth_credential_id uuid [unique, not null, ref: > auth_credential.id, note: 'FK para as credenciais de login do cliente. Relação 1:1']
  full_name varchar(255) [not null]
  contact_email_secondary varchar(254) [unique, note: 'Email de contato opcional, diferente do login_email']
  primary_phone varchar(18) [not null]
  birth_date date [not null]
  document_id_type varchar(50) [note: 'Tipo do documento de identificação. Ex: CPF, PASSPORT, NATIONAL_ID']
  document_id_value varchar(50) [note: 'O número/código do documento. Unicidade depende do tipo e legislação.']
  main_address_id uuid [ref: > address.id, note: 'FK para o endereço principal do cliente. Pode ser NULL? Decisão de negócio.']
  indexes {
    (document_id_type, document_id_value) [unique, note: 'Garante que o mesmo documento não seja de dois clientes, se aplicável.']
  }
}

Table booking {
  id uuid [pk, default: `gen_random_uuid()`]
  customer_id uuid [not null, ref: > customer.id]
  hotel_id uuid [not null, ref: > hotel.id, note: 'Denormalizado para facilitar queries, mas consistente com room.hotel_id']
  room_id uuid [not null, ref: > room.id]
  checkin_date date [not null, note: 'Data de entrada no hotel']
  checkout_date date [not null, note: 'Data de saída do hotel. Implementar CHECK (checkout_date > checkin_date)']
  number_of_guests int [not null, default: 1, note: 'Número total de hóspedes. Implementar CHECK (number_of_guests >= 1)']
  status booking_status [not null, default: 'PENDING_CONFIRMATION']
  special_requests text [note: 'Pedidos especiais do hóspede para esta reserva']
  booked_at timestamptz [not null, default: `now()`, note: 'Timestamp de quando a reserva foi criada. Serve como created_at.']
  indexes {
    (room_id, checkin_date, checkout_date) [note: 'Para ajudar a verificar sobreposição de datas. A lógica de overlap é mais complexa.']
    (customer_id, booked_at) 
    (hotel_id, checkin_date) 
  }
}

Table payment {
  id uuid [pk, default: `gen_random_uuid()`]
  booking_id uuid [not null, ref: > booking.id]
  external_transaction_id varchar(100) [unique, not null, note: 'ID da transação fornecido pelo gateway de pagamento']
  amount_paid decimal(10,2) [not null]
  payment_method payment_method [not null]
  status payment_status [not null, default: 'PENDING']
  created_at timestamptz [not null, default: `now()`, note: 'Quando este registro de tentativa/transação de pagamento foi criado no sistema']
  processed_at timestamptz [note: 'Momento em que o pagamento foi efetivamente confirmado/falhou/reembolsado pelo gateway']
  payment_details jsonb [note: 'JSON para guardar detalhes adicionais do gateway, como parcelas, nsu, etc.']
  indexes { (booking_id) }
}

Table review {
  id uuid [pk, default: `gen_random_uuid()`]
  booking_id uuid [not null, unique, ref: > booking.id, note: 'Garante um review por reserva. O cliente só pode avaliar após a estadia? (Regra de negócio)']
  customer_id uuid [not null, ref: > customer.id, note: 'Normalizado do booking.customer_id para facilitar queries de reviews por cliente']
  hotel_id uuid [not null, ref: > hotel.id, note: 'Normalizado do booking.hotel_id para facilitar queries de reviews por hotel']
  rating int [not null, note: 'Rating de 1 a 5. Implementar com CHECK (rating >= 1 AND rating <= 5)']
  title varchar(255)
  comment text
  is_anonymous boolean [not null, default: false]
  reviewed_at timestamptz [not null, default: `now()`, note: 'Quando o review foi submetido. Serve como created_at.']
  updated_at timestamptz [not null, default: `now()`, note: 'Quando o review foi atualizado pela última vez.']
}