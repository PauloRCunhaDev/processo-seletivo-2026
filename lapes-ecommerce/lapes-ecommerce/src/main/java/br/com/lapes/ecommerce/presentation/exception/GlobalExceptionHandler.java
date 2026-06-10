package br.com.lapes.ecommerce.presentation.exception;

import br.com.lapes.ecommerce.domain.exception.BusinessException;
import br.com.lapes.ecommerce.domain.exception.OrderCancellationNotAllowedException;
import br.com.lapes.ecommerce.domain.exception.ResourceNotFoundException;
import br.com.lapes.ecommerce.presentation.dto.response.ErrorResponse;
import br.com.lapes.ecommerce.presentation.dto.response.ValidationErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ValidationErrorResponse> handleValidation(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        List<ValidationErrorResponse.FieldError> fields = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(e -> new ValidationErrorResponse.FieldError(e.getField(), e.getDefaultMessage()))
            .toList();

        return ResponseEntity.badRequest()
            .body(ValidationErrorResponse.of(fields, request.getRequestURI()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ErrorResponse.of(404, ex.getErrorCode(), ex.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(OrderCancellationNotAllowedException.class)
    ResponseEntity<ErrorResponse> handleCancellation(
            OrderCancellationNotAllowedException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
            .body(ErrorResponse.of(422, ex.getErrorCode(), ex.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(BusinessException.class)
    ResponseEntity<ErrorResponse> handleBusiness(BusinessException ex, HttpServletRequest request) {
        int status = ex.getErrorCode().endsWith("CONFLICT") ? 409 : 422;
        return ResponseEntity.status(status)
            .body(ErrorResponse.of(status, ex.getErrorCode(), ex.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(AuthenticationException.class)
    ResponseEntity<ErrorResponse> handleAuth(AuthenticationException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(ErrorResponse.of(401, "UNAUTHORIZED", "Autenticacao necessaria", request.getRequestURI()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    ResponseEntity<ErrorResponse> handleForbidden(AccessDeniedException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(ErrorResponse.of(403, "FORBIDDEN", "Acesso negado", request.getRequestURI()));
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<ErrorResponse> handleGeneric(Exception ex, HttpServletRequest request) {
        log.error("Erro interno nao tratado", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorResponse.of(500, "INTERNAL_ERROR", "Erro interno do servidor", request.getRequestURI()));
    }
}
