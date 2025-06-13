package com.hotela.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class CustomerBusinessService {

    public boolean canMakeReservation(LocalDate birthDate) {
        if (birthDate == null) {
            return false;
        }

        // Deve ser maior de 18 anos para fazer reserva
        return Period.between(birthDate, LocalDate.now()).getYears() >= 18;
    }

    public String getCustomerType(int totalBookings, LocalDate firstBookingDate) {
        if (totalBookings <= 0) {
            return "Novo Cliente";
        }

        if (totalBookings == 1) {
            return "Cliente";
        }

        if (totalBookings >= 2 && totalBookings <= 5) {
            return "Cliente Regular";
        }

        if (totalBookings > 5 && firstBookingDate != null) {
            long yearsAsCustomer = Period.between(firstBookingDate, LocalDate.now()).getYears();
            if (yearsAsCustomer >= 2) {
                return "Cliente VIP";
            }
        }

        return "Cliente Frequente";
    }

    public boolean isEligibleForDiscount(int totalBookings, boolean hasRecentCancellation) {
        // Cliente VIP (mais de 5 reservas) tem desconto
        if (totalBookings > 5) {
            return true;
        }

        // Cliente não tem desconto se cancelou recentemente
        if (hasRecentCancellation) {
            return false;
        }

        // Cliente regular (2-5 reservas) tem desconto
        return totalBookings >= 2;
    }

    public int calculateLoyaltyPoints(int totalBookings, int nightsStayed) {
        if (totalBookings <= 0 || nightsStayed <= 0) {
            return 0;
        }

        int basePoints = nightsStayed * 10; // 10 pontos por noite

        // Bônus por número de reservas
        if (totalBookings > 10) {
            return (int) (basePoints * 1.5); // 50% bônus para clientes muito frequentes
        } else if (totalBookings > 5) {
            return (int) (basePoints * 1.2); // 20% bônus para clientes frequentes
        }

        return basePoints;
    }
}