package com.hotela.repository;

import com.hotela.model.entity.Payment;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface de repositório para a entidade Payment.
 * Fornece operações CRUD e consultas personalizadas para Payment.
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    /**
     * Busca um pagamento pelo ID da reserva associada.
     *
     * @param bookingId o UUID da reserva
     * @return um Optional contendo o pagamento, se encontrado, ou vazio caso contrário
     */
    Optional<Payment> findByBookingId(UUID bookingId);

    /**
     * Busca um pagamento pelo seu ID de transação externa.
     *
     * @param externalTransactionId o identificador externo da transação
     * @return um Optional contendo o pagamento, se encontrado, ou vazio caso contrário
     */
    Optional<Payment> findByExternalTransactionId(String externalTransactionId);
}