package com.hotela.service;

import com.hotela.model.enums.BookingStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class BookingBusinessService {

    public boolean canMakeReservation(LocalDate checkinDate, LocalDate checkoutDate) {
        if (checkinDate == null || checkoutDate == null) {
            return false;
        }

        // Não pode fazer reserva para o passado
        if (checkinDate.isBefore(LocalDate.now())) {
            return false;
        }

        // Checkout deve ser depois do checkin
        if (!checkoutDate.isAfter(checkinDate)) {
            return false;
        }

        // Reserva mínima de 1 dia
        return ChronoUnit.DAYS.between(checkinDate, checkoutDate) >= 1;
    }

    public BigDecimal calculateTotalPrice(BigDecimal pricePerNight, LocalDate checkinDate, LocalDate checkoutDate) {
        if (pricePerNight == null || checkinDate == null || checkoutDate == null) {
            throw new IllegalArgumentException("Parameters cannot be null");
        }

        long numberOfNights = ChronoUnit.DAYS.between(checkinDate, checkoutDate);

        if (numberOfNights <= 0) {
            throw new IllegalArgumentException("Invalid date range");
        }

        return pricePerNight.multiply(BigDecimal.valueOf(numberOfNights));
    }

    public boolean canCancelReservation(BookingStatus currentStatus, LocalDate checkinDate) {
        if (currentStatus == null || checkinDate == null) {
            return false;
        }

        // Não pode cancelar se já foi cancelada
        if (currentStatus == BookingStatus.CANCELLED_BY_CUSTOMER) {
            return false;
        }

        // Não pode cancelar se já fez check-in ou check-out
        if (currentStatus == BookingStatus.CHECKED_IN || currentStatus == BookingStatus.CHECKED_OUT) {
            return false;
        }

        // Só pode cancelar até 24h antes do checkin
        return checkinDate.isAfter(LocalDate.now().plusDays(1));
    }

    public boolean isValidGuestCount(int numberOfGuests, int roomCapacity) {
        return numberOfGuests > 0 && numberOfGuests <= roomCapacity;
    }

    public BigDecimal calculateRefundAmount(BigDecimal totalAmount, LocalDate cancellationDate, LocalDate checkinDate) {
        if (totalAmount == null || cancellationDate == null || checkinDate == null) {
            return BigDecimal.ZERO;
        }

        long daysUntilCheckin = ChronoUnit.DAYS.between(cancellationDate, checkinDate);

        // Cancelamento com mais de 7 dias: reembolso de 100%
        if (daysUntilCheckin > 7) {
            return totalAmount;
        }

        // Cancelamento entre 2-7 dias: reembolso de 50%
        if (daysUntilCheckin >= 2) {
            return totalAmount.multiply(BigDecimal.valueOf(0.5));
        }

        // Cancelamento com menos de 2 dias: sem reembolso
        return BigDecimal.ZERO;
    }
}