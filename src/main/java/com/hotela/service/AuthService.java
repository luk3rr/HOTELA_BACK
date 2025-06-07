package com.hotela.service;

import com.hotela.web.dto.CustomerRegisterRequestDto;
import com.hotela.web.dto.LoginRequestDto;
import com.hotela.web.dto.LoginResponseDto;
import com.hotela.web.dto.PartnerRegisterRequestDto;
import com.hotela.web.dto.RegistrationResponseDto;

public interface AuthService {

    RegistrationResponseDto registerCustomer(CustomerRegisterRequestDto customerDto);

    RegistrationResponseDto registerPartner(PartnerRegisterRequestDto partnerDto);

    LoginResponseDto loginUser(LoginRequestDto loginRequestDto);
}
