package com.hotela.domain.vo;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @details Representa um número de telefone (Value Object). Valida um formato específico: código do
 *     país, DDD e número. É imutável.
 */
public final class Telephone {

    /**
     * @details Regex para validar o formato "codigo_pais<espaço>ddd<espaço>numero". Exemplos: "55
     *     31 999999999", "1 415 1234567". - Código do país: 1 a 3 dígitos. - DDD: 2 dígitos. -
     *     Número: 8 a 9 dígitos.
     */
    private static final Pattern TELEPHONE_PATTERN =
            Pattern.compile("^(\\d{1,3})\\s(\\d{2})\\s(\\d{8,9})$");

    private final String value;

    /**
     * @details Constrói uma nova instância de Telephone.
     * @param value A string representando o número de telefone no formato esperado.
     * @throws NullPointerException se o valor fornecido for nulo.
     * @throws IllegalArgumentException se o valor fornecido for em branco ou não corresponder ao
     *     formato de telefone esperado.
     */
    public Telephone(String value) {
        Objects.requireNonNull(value, "Telephone value cannot be null.");
        String trimmedValue = value.trim();

        if (trimmedValue.isEmpty()) {
            throw new IllegalArgumentException("Telephone value cannot be blank.");
        }

        if (!TELEPHONE_PATTERN.matcher(trimmedValue).matches()) {
            throw new IllegalArgumentException(
                    "Invalid telephone format. Expected format like '55 31 999999999', but got: "
                            + value);
        }
        this.value = trimmedValue; // Armazena o valor já validado e trimado
    }

    /**
     * @details Retorna o valor string do número de telefone.
     * @return O número de telefone.
     */
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Telephone telephone = (Telephone) o;
        return Objects.equals(value, telephone.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    /**
     * @details Retorna a representação string do telefone.
     * @return O número de telefone.
     */
    @Override
    public String toString() {
        return value;
    }
}
