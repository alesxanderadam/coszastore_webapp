package alticshaw.com.coszastore.exception;

import org.springframework.validation.BindingResult;

public class CustomValidationException extends RuntimeException{
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CustomValidationException(BindingResult bindingResult) {
        this.message = bindingResult.getFieldErrors().get(0).getDefaultMessage();
    }

    public CustomValidationException(String message) {
        this.message = message;
    }
}
