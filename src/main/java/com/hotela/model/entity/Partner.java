package com.hotela.model.entity;

import com.hotela.model.enums.PartnerStatus;
import com.hotela.domain.vo.Email;
import com.hotela.domain.vo.Telephone;
import com.hotela.domain.converter.CnpjConverter;
import com.hotela.domain.converter.EmailConverter;
import com.hotela.domain.converter.TelephoneConverter;
import com.hotela.domain.vo.Cnpj;
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
@Table(name = "partner")
public class Partner {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "UUID", updatable = false, nullable = false)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auth_credential_id", referencedColumnName = "id", nullable = false, unique = true)
    private AuthCredential authCredential;

    @Column(name = "company_name", nullable = false, length = 255)
    private String companyName;

    @Column(name = "legal_name", length = 255)
    private String legalName;

    @Convert(converter = CnpjConverter.class)
    @Column(name = "cnpj", unique = true, nullable = false, length = 14)
    private Cnpj cnpj;

    @Convert(converter = EmailConverter.class)
    @Column(name = "primary_contact_email", unique = true, length = 254, nullable = false)
    private Email primaryContactEmail;

    @Convert(converter = TelephoneConverter.class)
    @Column(name = "primary_phone", nullable = false, length = 18)
    private Telephone primaryPhone;

    /**
     * @details Endereço principal do parceiro. Esta é uma relação um-para-um,
     *          onde cada parceiro tem um endereço único e obrigatório.
     */
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false, unique = true)
    private Address address;

    @Column(name = "representative_name", length = 255)
    private String representativeName;

    @Convert(converter = EmailConverter.class)
    @Column(name = "representative_email", length = 254)
    private Email representativeEmail;

    @Convert(converter = TelephoneConverter.class)
    @Column(name = "representative_phone", length = 18)
    private Telephone representativePhone;

    @Column(name = "contract_signed_at")
    private LocalDate contractSignedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private PartnerStatus status = PartnerStatus.ACTIVE;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String notes;
}