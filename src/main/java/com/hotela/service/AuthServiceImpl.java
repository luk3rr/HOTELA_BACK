package com.hotela.service;

import com.hotela.domain.vo.Cnpj;
import com.hotela.domain.vo.Email;
import com.hotela.domain.vo.Telephone;
import com.hotela.error.HotelaException;
import com.hotela.model.entity.Address;
import com.hotela.model.entity.AuthCredential;
import com.hotela.model.entity.Customer;
import com.hotela.model.entity.Partner;
import com.hotela.model.enums.AuthUserType;
import com.hotela.repository.AddressRepository;
import com.hotela.repository.AuthCredentialRepository;
import com.hotela.repository.CustomerRepository;
import com.hotela.repository.PartnerRepository;
import com.hotela.security.jwt.JwtTokenProvider;
import com.hotela.web.dto.AddressDto;
import com.hotela.web.dto.CustomerRegisterRequestDto;
import com.hotela.web.dto.LoginRequestDto;
import com.hotela.web.dto.LoginResponseDto;
import com.hotela.web.dto.PartnerRegisterRequestDto;
import com.hotela.web.dto.RegistrationResponseDto;
import java.time.OffsetDateTime;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthCredentialRepository authCredentialRepository;
    private final CustomerRepository customerRepository;
    private final PartnerRepository partnerRepository;
    private final AddressRepository
            addressRepository; // Necessário para salvar o endereço do Customer
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(
            AuthCredentialRepository authCredentialRepository,
            CustomerRepository customerRepository,
            PartnerRepository partnerRepository,
            AddressRepository addressRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtTokenProvider jwtTokenProvider) {
        this.authCredentialRepository = authCredentialRepository;
        this.customerRepository = customerRepository;
        this.partnerRepository = partnerRepository;
        this.addressRepository = addressRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    @Transactional
    public RegistrationResponseDto registerCustomer(CustomerRegisterRequestDto customerDto) {
        Email loginEmailVo = new Email(customerDto.email());
        if (authCredentialRepository.findByLoginEmail(loginEmailVo).isPresent()) {
            throw new HotelaException.EmailAlreadyRegisteredException();
        }

        AuthCredential authCredential =
                AuthCredential.builder()
                        .loginEmail(loginEmailVo)
                        .passwordHash(passwordEncoder.encode(customerDto.password()))
                        .userType(AuthUserType.CUSTOMER)
                        .isActive(true)
                        .createdAt(OffsetDateTime.now())
                        .updatedAt(OffsetDateTime.now())
                        .build();
        AuthCredential savedAuthCredential = authCredentialRepository.save(authCredential);

        Address mainAddress = mapAddressDtoToEntity(customerDto.mainAddress());
        Address savedAddress = addressRepository.save(mainAddress); // Salva o endereço primeiro

        Customer customer =
                Customer.builder()
                        .authCredential(savedAuthCredential)
                        .fullName(customerDto.fullName())
                        // O email de contato principal do Customer pode ser o mesmo do loginEmail
                        // Se fossem diferentes, você teria um campo 'contactEmail' no DTO.
                        // Vamos assumir que é o mesmo por enquanto.
                        .contactEmailSecondary(
                                customerDto.contactEmailSecondary() != null
                                        ? new Email(customerDto.contactEmailSecondary())
                                        : null)
                        .primaryPhone(new Telephone(customerDto.primaryPhone()))
                        .birthDate(customerDto.birthDate())
                        .documentIdType(customerDto.documentIdType())
                        .documentIdValue(
                                customerDto.documentIdValue()) // Validação do formato específico
                        // (CPF/CNPJ) pode ser
                        // aqui ou antes
                        .mainAddress(savedAddress)
                        .build();

        try {
            Customer savedCustomer = customerRepository.save(customer);
            return new RegistrationResponseDto(
                    savedCustomer.getId(), "Customer registered successfully.");
        } catch (DataIntegrityViolationException e) {
            // Pode ocorrer se houver violação de constraint unique (ex: documento)
            // Idealmente, verificaríamos a unicidade do documento antes de tentar salvar.
            // Por simplicidade, estamos tratando a exceção genérica aqui.
            // Rollback da AuthCredential não é automático sem @Transactional em toda a
            // unidade de trabalho,
            // mas como auth_credential_id em customer é FK, a falha no save do customer
            // impediria.
            // Se auth_credential_id fosse nullável, precisaríamos de tratamento mais
            // robusto para deleção órfã.
            authCredentialRepository.delete(
                    savedAuthCredential); // Tenta limpar a AuthCredential órfã
            throw new HotelaException.InvalidDataException(); // Ou uma exceção mais específica
        }
    }

    @Override
    @Transactional
    public RegistrationResponseDto registerPartner(PartnerRegisterRequestDto partnerDto) {
        Email loginEmailVo = new Email(partnerDto.primaryContactEmail());
        if (authCredentialRepository.findByLoginEmail(loginEmailVo).isPresent()) {
            throw new HotelaException.EmailAlreadyRegisteredException();
        }

        AuthCredential authCredential =
                AuthCredential.builder()
                        .loginEmail(loginEmailVo)
                        .passwordHash(passwordEncoder.encode(partnerDto.password()))
                        .userType(AuthUserType.PARTNER)
                        .isActive(true)
                        .createdAt(OffsetDateTime.now())
                        .updatedAt(OffsetDateTime.now())
                        .build();
        AuthCredential savedAuthCredential = authCredentialRepository.save(authCredential);

        // Para Partner, o Address é OneToOne com CascadeType.ALL,
        // então não precisamos salvar o Address separadamente antes.
        // O JPA cuidará disso quando o Partner for salvo.
        Address partnerAddress = mapAddressDtoToEntity(partnerDto.address());

        Partner partner =
                Partner.builder()
                        .authCredential(savedAuthCredential)
                        .companyName(partnerDto.companyName())
                        .legalName(partnerDto.legalName())
                        .cnpj(new Cnpj(partnerDto.cnpj()))
                        .primaryContactEmail(loginEmailVo) // O mesmo do login
                        .primaryPhone(new Telephone(partnerDto.primaryPhone()))
                        .address(partnerAddress) // JPA/Hibernate deve salvar o Address devido ao
                        // CascadeType.ALL
                        .representativeName(partnerDto.representativeName())
                        .representativeEmail(
                                partnerDto.representativeEmail() != null
                                        ? new Email(partnerDto.representativeEmail())
                                        : null)
                        .representativePhone(
                                partnerDto.representativePhone() != null
                                        ? new Telephone(partnerDto.representativePhone())
                                        : null)
                        .contractSignedAt(partnerDto.contractSignedAt())
                        .notes(partnerDto.notes())
                        .build();

        try {
            Partner savedPartner = partnerRepository.save(partner);
            return new RegistrationResponseDto(
                    savedPartner.getId(), "Partner registered successfully.");
        } catch (DataIntegrityViolationException e) {
            // Similar ao customer, tratar violações de constraints.
            authCredentialRepository.delete(savedAuthCredential);
            throw new HotelaException.InvalidDataException();
        }
    }

    @Override
    public LoginResponseDto loginUser(LoginRequestDto loginRequestDto) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequestDto.email(), loginRequestDto.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication);

        // Para obter a data de expiração do token para a resposta:
        // Precisamos extrair do token gerado, pois o jwtExpirationMs é a *duração*.
        OffsetDateTime expiryDate =
                OffsetDateTime.now().plusNanos(jwtTokenProvider.getJwtExpirationMs() * 1_000_000L);

        return new LoginResponseDto(jwt, expiryDate);
    }

    // Método helper para mapear AddressDto para Address Entidade
    private Address mapAddressDtoToEntity(AddressDto dto) {
        if (dto == null) {
            return null;
        }
        return Address.builder()
                .streetAddress(dto.streetAddress())
                .number(dto.number())
                .complement(dto.complement())
                .neighborhood(dto.neighborhood())
                .city(dto.city())
                .stateProvince(dto.stateProvince())
                .postalCode(dto.postalCode())
                .countryCode(
                        dto.countryCode() != null ? dto.countryCode() : "BR") // Default se não vier
                .build();
    }
}
