package com.github.peoplemanagement.controller.exception;

import com.github.peoplemanagement.service.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = NotFoundException.class)
    protected ResponseEntity<Object> handleNotFoundException(NotFoundException exception) {
        final HttpStatus statusCode = HttpStatus.NOT_FOUND;
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(statusCode, exception.getMessage());
        return ResponseEntity.status(statusCode).body(problemDetail);
    }

}
