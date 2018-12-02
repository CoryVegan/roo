package io.aweris.roo.infrastructure.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.aweris.roo.infrastructure.rest.error.ApiError;
import io.aweris.roo.infrastructure.rest.error.ValidationError;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
@Slf4j
public class RestErrorHandler {

    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    ApiError handle(ValidationException e) {
        ValidationError error = new ValidationError();
        error.setMessage(e.getMessage());
        return error;
    }

    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    ApiError handle(HttpMessageNotReadableException e) {
        ValidationError error = new ValidationError();
        error.setMessage(getMessageFor(e));
        return error;
    }

    String getMessageFor(HttpMessageNotReadableException e) {
        if(e.getCause() instanceof JsonProcessingException) {
            JsonProcessingException jsonError = (JsonProcessingException) e.getCause();
            return jsonError.getOriginalMessage();
        }
        return e.getMostSpecificCause().getMessage();
    }

    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    ApiError handle(BindException e) {
        return mapToApiError(e.getBindingResult().getFieldErrors());
    }

    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    ApiError handle(MethodArgumentNotValidException e) {
        return mapToApiError(e.getBindingResult().getFieldErrors());
    }

    private ApiError mapToApiError(List<FieldError> fieldErrors) {
        List<ApiFieldError> errors = fieldErrors.stream()
                                                .map(error -> new ApiFieldError(error.getField(), error.getDefaultMessage()))
                                                .collect(toList());

        ValidationError error = new ValidationError();
        error.setMessage("Some fields does not have valid values.");
        error.setDetails(errors);
        return error;
    }

    @Data
    @NoArgsConstructor
    public static class ApiFieldError {

        public ApiFieldError(String field, String message) {
            this.field = field;
            this.message = message;
        }

        String field;
        String message;
    }

}
