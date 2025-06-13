package com.hotela.service;

import com.hotela.domain.vo.Email;
import com.hotela.domain.vo.Telephone;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {

    public boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        try {
            new Email(email);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public boolean isValidPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }
        try {
            new Telephone(phone);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public boolean isAdult(java.time.LocalDate birthDate) {
        if (birthDate == null) {
            return false;
        }
        return java.time.Period.between(birthDate, java.time.LocalDate.now()).getYears() >= 18;
    }

    public String formatCustomerName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            return "";
        }
        return fullName.trim().replaceAll("\\s+", " ");
    }
}