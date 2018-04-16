package com.comexport.lancamentocontabil.exception;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = {DuplicateException.class})
  protected ResponseEntity<Object> handleConflict(final RuntimeException ex, final WebRequest request) {
    return handleExceptionInternal(ex, new MessageBody(ex.getMessage()), new HttpHeaders(), HttpStatus.CONFLICT, request);
  }

  @ExceptionHandler(value = {NotFoundException.class})
  protected ResponseEntity<Object> handleNotFound(final RuntimeException ex, final WebRequest request) {
    return handleExceptionInternal(ex, new MessageBody(ex.getMessage()), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers,
      final HttpStatus status, final WebRequest request) {

    final List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
    final List<String> errors = new ArrayList<>(fieldErrors.size());
    String error;
    for (final FieldError fieldError : fieldErrors) {
      error = fieldError.getField() + ", " + fieldError.getDefaultMessage();
      errors.add(error);
    }
    return handleExceptionInternal(ex, new ErrorMessage(errors), headers, status, request);
  }
}