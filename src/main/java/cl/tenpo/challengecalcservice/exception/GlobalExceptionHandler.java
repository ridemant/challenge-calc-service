package cl.tenpo.challengecalcservice.exception;

import cl.tenpo.challengecalcservice.dto.ApiResponseBody;
import cl.tenpo.challengecalcservice.dto.ErrorDetails;
import cl.tenpo.challengecalcservice.dto.FieldError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PercentageFailedException.class)
    public ResponseEntity<ApiResponseBody<ErrorDetails>> handlePercentageUnavailable(PercentageFailedException ex) {
        ApiResponseBody<ErrorDetails> response = new ApiResponseBody<>(
                false,
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                new ErrorDetails(ex.getMessage())
        );
        return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseBody> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<FieldError> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> new FieldError(err.getField(), err.getDefaultMessage()))
                .toList();

        ApiResponseBody<List<FieldError>> response = new ApiResponseBody<>(
                false,
                HttpStatus.BAD_REQUEST.value(),
                errors
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseBody<ErrorDetails>> handleGenericException(Exception ex) {
        ApiResponseBody<ErrorDetails> response = new ApiResponseBody<>(
                false,
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new ErrorDetails("Error interno: " + ex.getMessage())
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}