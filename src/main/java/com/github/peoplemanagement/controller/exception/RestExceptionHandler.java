package com.github.peoplemanagement.controller.exception;

import com.github.peoplemanagement.service.exception.NotFoundException;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashSet;
import java.util.Set;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, @NotNull HttpHeaders headers, @NotNull HttpStatusCode status, @NotNull WebRequest request) {
        final Set<String> errors = new HashSet<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> errors.add(fieldError.getField() + " " + fieldError.getDefaultMessage()));

        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, "Invalid request content.");
        problemDetail.setProperty("errors", errors);

        return createResponseEntity(problemDetail, headers, status, request);
    }

    @ExceptionHandler(value = NotFoundException.class)
    protected ResponseEntity<ProblemDetail> handleNotFoundException(NotFoundException exception) {
        final HttpStatus statusCode = HttpStatus.NOT_FOUND;
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(statusCode, exception.getMessage());
        return ResponseEntity.status(statusCode).body(problemDetail);
    }

}
