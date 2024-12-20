package com.uneb.labweb.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.uneb.labweb.exception.InvalidDateTimeException;
import com.uneb.labweb.exception.InvalidDayOfWeekException;
import com.uneb.labweb.exception.RecordAlreadyExistsException;
import com.uneb.labweb.exception.RecordNotFoundException;
import com.uneb.labweb.exception.WrongPasswordException;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(RecordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException(RecordNotFoundException ex) {
        return ex.getMessage();
    } 
    
    @ExceptionHandler(RecordAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleAlreadyExistsException(RecordAlreadyExistsException ex) {
        return ex.getMessage();
    }
    
    @ExceptionHandler(WrongPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleWrongPasswordException(WrongPasswordException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(InvalidDateTimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidDateTimeException(InvalidDateTimeException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(InvalidDayOfWeekException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidDayOfWeekException(InvalidDayOfWeekException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleConstraintViolationException(ConstraintViolationException ex) {
        return ex.getConstraintViolations()
                .stream()
                .map(error -> error.getPropertyPath() + " " + error.getMessage())
                .reduce("", (acc, error) -> acc + error + "\n");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getField() + " " + error.getDefaultMessage())
                .reduce("", (acc, error) -> acc + error + "\n");
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        if (ex != null) {
            Class<?> requiredType = ex.getRequiredType();

            if (requiredType != null) {
                // String typeName = requiredType.getSimpleName();
                String type = requiredType.getName();
                String[] typeParts = type.split("\\.");
                String typeName = typeParts[typeParts.length - 1];
                return ex.getName() + " should be of type " + typeName;
            }
        }
        return "Argument type not valid";
    }
}
