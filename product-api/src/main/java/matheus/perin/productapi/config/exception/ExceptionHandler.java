package matheus.perin.productapi.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {


    @org.springframework.web.bind.annotation.ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidationExceprtion(ValidationException exception) {
        return ResponseEntity
            .badRequest()
            .body(
                ExceptionDetails.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(exception.getMessage())
                .build()
            );
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleValidationExceprtion(AuthenticationException exception) {
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(
                ExceptionDetails.builder()
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .message(exception.getMessage())
                .build()
            );
    }

}
