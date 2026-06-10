package br.com.lapes.ecommerce.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;

public record ValidationErrorResponse(
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime timestamp,
    int status,
    String error,
    List<FieldError> fields,
    String path
) {
    public record FieldError(String field, String message) {}

    public static ValidationErrorResponse of(List<FieldError> fields, String path) {
        return new ValidationErrorResponse(
            LocalDateTime.now(), 400, "VALIDATION_ERROR", fields, path);
    }
}
