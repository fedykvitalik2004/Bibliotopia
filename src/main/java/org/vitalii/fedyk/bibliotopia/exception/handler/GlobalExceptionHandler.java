package org.vitalii.fedyk.bibliotopia.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.vitalii.fedyk.bibliotopia.exception.EmailVerificationTokenExpiredException;

import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ConstraintViolationResponse handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {
        final List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        final Map<String, List<String>> groupOfMessages = new HashMap<>();
        for (FieldError fieldError : fieldErrors) {
            groupOfMessages.merge(fieldError.getField(),
                    Collections.singletonList(fieldError.getDefaultMessage()),
                    (e1, e2) -> {
                        List<String> list = new ArrayList<>(e1);
                        list.addAll(e2);
                        return list;
                    });
        }
        return new ConstraintViolationResponse(groupOfMessages);
    }

    @ExceptionHandler({EmailVerificationTokenExpiredException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleEmailVerificationTokenExpiredException(final EmailVerificationTokenExpiredException exception) {
        return new ExceptionResponse(exception.getMessage());
    }
}
