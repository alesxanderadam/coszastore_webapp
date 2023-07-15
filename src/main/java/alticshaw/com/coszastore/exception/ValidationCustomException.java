package alticshaw.com.coszastore.exception;

import alticshaw.com.coszastore.payload.response.BaseResponse;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class ValidationCustomException extends RuntimeException{
    private String message;
    private int statusCode;

    public ValidationCustomException(BindingResult bindingResult){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(400);
        baseResponse.setMessage("Validation error");

        for (FieldError error : bindingResult.getFieldErrors()) {
            baseResponse.setMessage(baseResponse.getMessage() + " - " + error.getDefaultMessage());
        }

        this.message = baseResponse.getMessage();
        this.statusCode = baseResponse.getStatusCode();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
