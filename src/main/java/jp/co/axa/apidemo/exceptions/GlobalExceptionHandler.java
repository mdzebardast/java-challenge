package jp.co.axa.apidemo.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Value("${log.stackTrace:false}")
    private boolean printStackTrace;

    @Override
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        return handleFieldError(ex.getBindingResult());
    }

    @Override
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    protected ResponseEntity<Object> handleBindException(BindException ex,
                                                         HttpHeaders headers,
                                                         HttpStatus status,
                                                         WebRequest request) {
        return handleFieldError(ex.getBindingResult());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<Object> onConstraintValidationException(
            ConstraintViolationException e) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Validation error. Check 'errors' field for details.");
        for (ConstraintViolation violation : e.getConstraintViolations()) {
            errorResponse.addValidationError(violation.getPropertyPath().toString(), violation.getMessage());
        }

        return ResponseEntity.unprocessableEntity().body(errorResponse);
    }

    @ExceptionHandler(NoSuchElementFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleNoSuchElementFoundException(NoSuchElementFoundException itemNotFoundException) {
        log.error("Failed to find the requested element", itemNotFoundException);
        return buildErrorResponse(itemNotFoundException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleAllUncaughtException(Exception exception) {
        log.error("Unknown error occurred", exception);
        return buildErrorResponse(exception, "Unknown error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Object> handleFieldError(BindingResult bindingResult) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Validation error. Check 'errors' field for details.");
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorResponse.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.unprocessableEntity().body(errorResponse);
    }

    private ResponseEntity<Object> buildErrorResponse(Exception exception,
                                                      HttpStatus httpStatus) {
        return buildErrorResponse(exception, exception.getMessage(), httpStatus);
    }

    private ResponseEntity<Object> buildErrorResponse(Exception exception,
                                                      String message,
                                                      HttpStatus httpStatus) {
        ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), message);
        if (printStackTrace) {
            log.error("Exceptions: {}", ExceptionUtils.getStackTrace(exception));
        }
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    @Override
    public ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            Object body,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        return buildErrorResponse(ex, status);
    }
}