package alticshaw.com.coszastore.exception;

import alticshaw.com.coszastore.payload.response.BaseResponse;
import alticshaw.com.coszastore.payload.response.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Resource;

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

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<?> handleAuthException(Exception e){
        baseResponse.setMessage(e.getMessage());
        baseResponse.setStatusCode(401);

        return new ResponseEntity<>(baseResponse, HttpStatus.UNAUTHORIZED);
    }
}
