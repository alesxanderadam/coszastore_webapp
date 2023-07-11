package alticshaw.com.coszastore.exception;

public class CustomException extends RuntimeException{
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CustomException() {}

    public CustomException(String message) {
        this.message = message;
    }
}
