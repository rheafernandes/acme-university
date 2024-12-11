package de.adobe.acme.university.common;

import de.adobe.acme.university.common.exception.AlreadyExistsException;
import de.adobe.acme.university.common.exception.BadRequestException;
import de.adobe.acme.university.common.exception.NotFoundException;
import de.adobe.acme.university.common.exception.RateLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RateLimitExceededException.class)
    public ResponseEntity<Object> handleRateLimitExceededException(RateLimitExceededException ex) {
        return buildErrorResponse(
                ex.getMessage(),
                HttpStatus.TOO_MANY_REQUESTS,
                "RATE_LIMIT_EXCEEDED");
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<Object> handleAlreadyExistsException(AlreadyExistsException ex) {
        return buildErrorResponse(
                ex.getMessage(),
                HttpStatus.CONFLICT,
                "CONFLICT");
    }

    // Handle NotFoundException
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
        return buildErrorResponse(
                ex.getMessage(),
                HttpStatus.NOT_FOUND,
                "NOT_FOUND");
    }

    // Handle BadRequestException
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException ex) {
        return buildErrorResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST,
                "BAD_REQUEST");
    }

    // Handle General Exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex) {
        return buildErrorResponse(
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                "INTERNAL_SERVER_ERROR");
    }

    private ResponseEntity<Object> buildErrorResponse(
            String message,
            HttpStatusCode httpStatus,
            String key) {
        return ResponseEntity.status(httpStatus)
                .body(ErrorResponse.builder().key(key).message(message).build());
    }
}
