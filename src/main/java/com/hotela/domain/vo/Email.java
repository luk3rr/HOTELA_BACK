package com.hotela.domain.vo;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @details Representa um endereço de e-mail válido (Value Object). Garante que qualquer instância
 *     represente um email que passou por uma validação de formato. É imutável
 */
public class Email {
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");

    private final String value;

    /**
     * @details Constrói uma nova instância de Email.
     * @param value A string representando o endereço de e-mail.
     * @throws IllegalArgumentException se o valor fornecido for nulo, em branco ou não corresponder
     *     a um formato de e-mail válido.
     */
    public Email(String value) {
        Objects.requireNonNull(value, "Email value cannot be null");

        if (value.trim().isEmpty()) {
            throw new IllegalArgumentException("Email value cannot be blank");
        }

        if (!EMAIL_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("Invalid email format: " + value);
        }

        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(value, email.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.value);
    }

    @Override
    public String toString() {
        return value;
    }
}
