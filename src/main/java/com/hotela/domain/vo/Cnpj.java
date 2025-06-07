package com.hotela.domain.vo;

import java.util.Objects;
import java.util.regex.Pattern;

public final class Cnpj {

    private static final Pattern CNPJ_PATTERN =
            Pattern.compile("^(\\d{2}\\.?\\d{3}\\.?\\d{3}/?\\d{4}-?\\d{2})$");
    private final String value;

    public Cnpj(String value) {
        Objects.requireNonNull(value, "CNPJ value cannot be null");
        String cleanedValue = value.replaceAll("[^0-9]", "");

        if (cleanedValue.length() != 14) {
            throw new IllegalArgumentException(
                    "Invalid CNPJ length: " + value + " (cleaned: " + cleanedValue + ")");
        }
        if (!CNPJ_PATTERN.matcher(value).matches() && !cleanedValue.matches("^\\d{14}$")) {
            throw new IllegalArgumentException("Invalid CNPJ format: " + value);
        }
        if (!isValidCnpj(cleanedValue)) {
            throw new IllegalArgumentException("Invalid CNPJ digits: " + value);
        }

        this.value = cleanedValue;
    }

    public String getValue() {
        return value;
    }

    public String getFormattedValue() {
        if (value == null || value.length() != 14) {
            return value;
        }
        return String.format(
                "%s.%s.%s/%s-%s",
                value.substring(0, 2),
                value.substring(2, 5),
                value.substring(5, 8),
                value.substring(8, 12),
                value.substring(12, 14));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cnpj cnpj = (Cnpj) o;
        return Objects.equals(value, cnpj.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return getFormattedValue();
    }

    /**
     * @param cnpj
     * @details Função que realiza a validação de um Cnpj baseado no algoritmo dos digitos
     *     verificadores
     * @return booleano que diz se é o ou não válido
     */
    private static boolean isValidCnpj(String cnpj) {
        if (cnpj.chars().distinct().count() == 1) {
            return false;
        }

        try {
            int[] weights1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            int[] weights2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

            int sum1 = 0;
            for (int i = 0; i < 12; i++) {
                sum1 += (cnpj.charAt(i) - '0') * weights1[i];
            }
            int check1 = sum1 % 11;
            check1 = (check1 < 2) ? 0 : 11 - check1;

            int sum2 = 0;
            for (int i = 0; i < 13; i++) {
                int digit = (i < 12) ? (cnpj.charAt(i) - '0') : check1;
                sum2 += digit * weights2[i];
            }
            int check2 = sum2 % 11;
            check2 = (check2 < 2) ? 0 : 11 - check2;

            return check1 == (cnpj.charAt(12) - '0') && check2 == (cnpj.charAt(13) - '0');
        } catch (Exception e) {
            return false;
        }
    }
}
