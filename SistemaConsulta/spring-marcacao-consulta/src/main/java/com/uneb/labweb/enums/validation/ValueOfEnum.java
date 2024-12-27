package com.uneb.labweb.enums.validation;

import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * Anotação personalizada para validar se um valor de campo corresponde a um valor válido de um enum.
 * 
 * A validação é realizada pela classe `ValueOfEnumValidator`.
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = ValueOfEnumValidator.class)
public @interface ValueOfEnum {

    /**
     * Define a classe do enum que será validada.
     *
     * @return A classe do enum a ser validada.
     */
    Class<? extends Enum<?>> enumClass();

    /**
     * A mensagem de erro caso a validação falhe.
     * 
     * A mensagem padrão inclui o nome da classe do enum.
     *
     * @return A mensagem de erro.
     */
    String message() default "must be any of enum {enumClass}";

    /**
     * Define os grupos de validação a serem aplicados.
     * 
     * @return Os grupos de validação.
     */
    Class<?>[] groups() default {};

    /**
     * Define a carga útil associada à validação. Usado para transportar informações adicionais
     * durante o processo de validação.
     * 
     * @return A carga útil associada à validação.
     */
    Class<? extends Payload>[] payload() default {};
}