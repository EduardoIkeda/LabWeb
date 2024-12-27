package com.uneb.labweb.enums.validation;

import java.util.List;
import java.util.stream.Stream;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validador personalizado para a anotação {@link ValueOfEnum}.
 * 
 * Verifica se o valor de um campo corresponde a um valor válido dentro de um enum.
 */
public class ValueOfEnumValidator implements ConstraintValidator<ValueOfEnum, CharSequence> {

    private List<String> acceptedValues;

    /**
     * Inicializa o validador com os valores do enum fornecido na anotação.
     * 
     * @param annotation A anotação {@link ValueOfEnum}.
     */
    @Override
    public void initialize(ValueOfEnum annotation) {
        // Obter todos os valores do enum e convertê-los para uma lista de strings
        acceptedValues = Stream.of(annotation.enumClass().getEnumConstants())
                .map(Enum::toString)
                .toList();
    }

    /**
     * Verifica se o valor fornecido é um dos valores válidos do enum.
     * 
     * @param value O valor a ser validado.
     * @param context O contexto da validação.
     * @return true se o valor for válido, false caso contrário.
     */
    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        // Se o valor for nulo, consideramos válido, pois pode ser tratado por outras validações (ex: @NotNull)
        if (value == null) {
            return true;
        }

        // Verifica se o valor existe na lista de valores aceitos
        return acceptedValues.contains(value.toString());
    }
}