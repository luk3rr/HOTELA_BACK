package com.hotela.domain.vo;

import java.util.Objects;
import java.util.regex.Pattern;

public final class Cpf {

    private static final Pattern CPF_PATTERN =
            Pattern.compile("^(\\d{3}\\.?\\d{3}\\.?\\d{3}-?\\d{2})$");
    private final String value;

    public Cpf(String value) {
        Objects.requireNonNull(value, "CPF value cannot be null");
        String cleanedValue = value.replaceAll("[^0-9]", "");

        if (cleanedValue.length() != 11) {
            throw new IllegalArgumentException(
                    "Invalid CPF length: " + value + " (cleaned: " + cleanedValue + ")");
        }
        if (!CPF_PATTERN.matcher(value).matches() && !cleanedValue.matches("^\\d{11}$")) {
            throw new IllegalArgumentException("Invalid CPF format: " + value);
        }
        if (!isValidCpf(cleanedValue)) {
            throw new IllegalArgumentException("Invalid CPF digits: " + value);
        }

        this.value = cleanedValue;
    }

    public String getValue() {
        return value;
    }

    public String getFormattedValue() {
        if (value == null || value.length() != 11) {
            return value;
        }
        return String.format(
                "%s.%s.%s-%s",
                value.substring(0, 3),
                value.substring(3, 6),
                value.substring(6, 9),
                value.substring(9, 11));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cpf cpf = (Cpf) o;
        return Objects.equals(value, cpf.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return getFormattedValue();
    }

    private static boolean isValidCpf(String cpf) {
        // Elimina CPFs com todos os dígitos iguais
        if (cpf.chars().distinct().count() == 1) {
            return false;
        }

        try {
            int sum1 = 0, sum2 = 0;
            for (int i = 0; i < 9; i++) {
                int digit = cpf.charAt(i) - '0';
                sum1 += digit * (10 - i);
                sum2 += digit * (11 - i);
            }
            int check1 = sum1 % 11;
            check1 = (check1 < 2) ? 0 : 11 - check1;
            sum2 += check1 * 2;
            int check2 = sum2 % 11;
            check2 = (check2 < 2) ? 0 : 11 - check2;

            return check1 == (cpf.charAt(9) - '0') && check2 == (cpf.charAt(10) - '0');
        } catch (Exception e) {
            return false;
        }
    }
}
