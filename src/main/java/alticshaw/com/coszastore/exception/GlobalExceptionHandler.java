package alticshaw.com.coszastore.exception;

import alticshaw.com.coszastore.payload.response.BaseResponse;
import alticshaw.com.coszastore.payload.response.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {
    BaseResponse baseResponse;
    @Autowired
    public GlobalExceptionHandler(BaseResponse baseResponse){
        this.baseResponse = baseResponse;
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomException(Exception e) {
        ErrorResponse response = new ErrorResponse();
        response.setStatusCode(500);
        response.setMessage(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(AuthCustomException.class)
    public ResponseEntity<?> handleAuthException(Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), e.getMessage()));
    }

    @ExceptionHandler(JwtCustomException.class)
    public ResponseEntity<?> handleJwtCustomException(JwtCustomException e) {
        return ResponseEntity.status(e.getStatusCode())
                .body(new ErrorResponse(e.getStatusCode(),e.getMessage()));
    }

    @ExceptionHandler(ValidationCustomException.class)
    public ResponseEntity<?> handleValidationException(ValidationCustomException exc) {
        return ResponseEntity.status(exc.getStatusCode())
                .body(new ErrorResponse(HttpStatus.CONFLICT.value(), exc.getMessage()));
    }

    @ExceptionHandler(ConflictCustomException.class)
    public ResponseEntity<?> handleConflictException(Exception e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(HttpStatus.CONFLICT.value(), e.getMessage()));
    }

    @ExceptionHandler(RoleCustomException.class)
    public ResponseEntity<?> handleRoleNotFoundException(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
    }

    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<?> handleFileStorageException(Exception e) {
        return ResponseEntity.ok()
                .body(new ErrorResponse(500, e.getMessage()));
    }
}
