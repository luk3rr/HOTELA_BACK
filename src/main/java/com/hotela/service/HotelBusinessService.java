package com.hotela.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class HotelBusinessService {

    public boolean isValidStarRating(BigDecimal rating) {
        if (rating == null) {
            return false;
        }

        return rating.compareTo(BigDecimal.ZERO) >= 0 &&
                rating.compareTo(BigDecimal.valueOf(5.0)) <= 0;
    }

    public String getHotelCategory(BigDecimal starRating) {
        if (starRating == null || starRating.compareTo(BigDecimal.ZERO) < 0) {
            return "Não Classificado";
        }

        if (starRating.compareTo(BigDecimal.valueOf(1.0)) < 0) {
            return "Econômico";
        } else if (starRating.compareTo(BigDecimal.valueOf(2.0)) < 0) {
            return "Básico";
        } else if (starRating.compareTo(BigDecimal.valueOf(3.0)) < 0) {
            return "Confortável";
        } else if (starRating.compareTo(BigDecimal.valueOf(4.0)) < 0) {
            return "Superior";
        } else {
            return "Luxo";
        }
    }

    public BigDecimal calculateAverageRating(BigDecimal currentRating, int totalReviews, int newRatingValue) {
        if (currentRating == null || totalReviews < 0 || newRatingValue < 1 || newRatingValue > 5) {
            throw new IllegalArgumentException("Invalid parameters for rating calculation");
        }

        if (totalReviews == 0) {
            return BigDecimal.valueOf(newRatingValue);
        }

        BigDecimal currentTotal = currentRating.multiply(BigDecimal.valueOf(totalReviews));
        BigDecimal newTotal = currentTotal.add(BigDecimal.valueOf(newRatingValue));

        return newTotal.divide(BigDecimal.valueOf(totalReviews + 1), 2, RoundingMode.HALF_UP);
    }

    public boolean isValidRoomNumber(String roomNumber, int floor) {
        if (roomNumber == null || roomNumber.trim().isEmpty()) {
            return false;
        }

        // Número do quarto deve começar com o andar (ex: 101, 201, 301)
        if (floor > 0) {
            return roomNumber.startsWith(String.valueOf(floor));
        }

        return true;
    }

    public BigDecimal calculateWeekendPrice(BigDecimal basePrice, boolean isWeekend) {
        if (basePrice == null) {
            return BigDecimal.ZERO;
        }

        // Fim de semana tem acréscimo de 20%
        if (isWeekend) {
            return basePrice.multiply(BigDecimal.valueOf(1.2));
        }

        return basePrice;
    }
}