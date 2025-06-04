package com.hotela.model.entity;

import com.hotela.domain.converter.EmailConverter;
import com.hotela.domain.converter.TelephoneConverter;
import com.hotela.domain.vo.Email;
import com.hotela.domain.vo.Telephone;
import com.hotela.model.enums.DocumentType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "customer", uniqueConstraints = {
        @UniqueConstraint(name = "uq_customer_document", columnNames = { "document_id_type", "document_id_value" })
})
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "UUID", updatable = false, nullable = false)
    private UUID id;

    /**
     * @details Credencial de autenticação associada a este cliente.
     *          A relação é OneToOne, e o carregamento é LAZY para otimização.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auth_credential_id", referencedColumnName = "id", nullable = false, unique = true)
    private AuthCredential authCredential;

    @Column(name = "full_name", nullable = false, length = 255)
    private String fullName;

    /**
     * @details Email de contato secundário, opcional. Utiliza o Value Object Email.
     */
    @Convert(converter = EmailConverter.class)
    @Column(name = "contact_email_secondary", unique = true, length = 254)
    private Email contactEmailSecondary;

    /**
     * @details Telefone principal de contato do cliente. Utiliza o Value Object
     *          Telephone.
     */
    @Convert(converter = TelephoneConverter.class) 
    @Column(name = "primary_phone", nullable = false, length = 18)
    private Telephone primaryPhone;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    /**
     * @details Tipo do documento de identificação (ex: CPF, PASSPORT).
     *          Mapeado para o Enum DocumentType e persistido como String.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "document_id_type", length = 50, nullable = false)
    private DocumentType documentIdType;

    /**
     * @details O valor do documento de identificação (ex: número do CPF, número do
     *          passaporte).
     *          Mantido como String para flexibilidade de armazenamento de
     *          diferentes tipos de documentos,
     *          com a validação e o uso de Value Objects específicos (como Cpf)
     *          ocorrendo na camada de serviço.
     *          A coluna no banco é VARCHAR(50).
     */
    @Column(name = "document_id_value", length = 50, nullable = false)
    private String documentIdValue;

    /**
     * @details Endereço principal do cliente.
     *          Pode ser nulo se o cliente não tiver um endereço principal
     *          registrado.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "main_address_id")
    private Address mainAddress;
}