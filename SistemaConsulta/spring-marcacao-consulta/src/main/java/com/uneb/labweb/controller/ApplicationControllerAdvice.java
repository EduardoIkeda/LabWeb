package com.uneb.labweb.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.uneb.labweb.exception.AppointmentCancelException;
import com.uneb.labweb.exception.InvalidDateTimeException;
import com.uneb.labweb.exception.InvalidDayOfWeekException;
import com.uneb.labweb.exception.RecordAlreadyExistsException;
import com.uneb.labweb.exception.RecordNotFoundException;
import com.uneb.labweb.exception.WrongPasswordException;

import jakarta.validation.ConstraintViolationException;

/**
 * Classe responsável pelo tratamento centralizado de exceções.
 */
@RestControllerAdvice
public class ApplicationControllerAdvice {

    /**
     * Lida com exceção quando um registro não é encontrado.
     */
    @ExceptionHandler(RecordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException(RecordNotFoundException ex) {
        return ex.getMessage();
    } 
    
    /**
     * Lida com exceção quando um registro já existe.
     */
    @ExceptionHandler(RecordAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleAlreadyExistsException(RecordAlreadyExistsException ex) {
        return ex.getMessage();
    }
    
    /**
     * Lida com exceção de senha incorreta.
     */
    @ExceptionHandler(WrongPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleWrongPasswordException(WrongPasswordException ex) {
        return ex.getMessage();
    }

    /**
     * Lida com exceção quando a data/hora fornecida é inválida.
     */
    @ExceptionHandler(InvalidDateTimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidDateTimeException(InvalidDateTimeException ex) {
        return ex.getMessage();
    }

    /**
     * Lida com exceção quando o dia da semana fornecido é inválido.
     */
    @ExceptionHandler(InvalidDayOfWeekException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidDayOfWeekException(InvalidDayOfWeekException ex) {
        return ex.getMessage();
    }

    /**
     * Lida com exceção de cancelamento de agendamento.
     */
    @ExceptionHandler(AppointmentCancelException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleAppointmentCancelException(AppointmentCancelException ex) {
        return ex.getMessage();
    }

    /**
     * Lida com exceção quando há violação de restrições de validação.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleConstraintViolationException(ConstraintViolationException ex) {
        return ex.getConstraintViolations()
                .stream()
                .map(error -> error.getPropertyPath() + " " + error.getMessage())
                .reduce("", (acc, error) -> acc + error + "\n");
    }

    /**
     * Lida com exceção quando há erros de validação de argumentos em métodos.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getField() + " " + error.getDefaultMessage())
                .reduce("", (acc, error) -> acc + error + "\n");
    }

    /**
     * Lida com exceção quando o tipo do argumento fornecido não corresponde ao esperado.
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        if (ex != null) {
            Class<?> requiredType = ex.getRequiredType();

            if (requiredType != null) {
                String type = requiredType.getName();
                String[] typeParts = type.split("\\.");
                String typeName = typeParts[typeParts.length - 1];
                return ex.getName() + " should be of type " + typeName;
            }
        }
        return "Argument type not valid";
    }
}
