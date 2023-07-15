package alticshaw.com.coszastore.exception;

import alticshaw.com.coszastore.payload.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomException(Exception e) {
        ErrorResponse response = new ErrorResponse();
        response.setStatusCode(500);
        response.setMessage(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<?> handleFileStorageException(Exception e) {
        return ResponseEntity.ok().body(new ErrorResponse(500, e.getMessage()));
    }
}
