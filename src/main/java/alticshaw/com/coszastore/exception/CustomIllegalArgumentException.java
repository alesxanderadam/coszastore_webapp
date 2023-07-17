package alticshaw.com.coszastore.exception;

public class CustomIllegalArgumentException extends RuntimeException{
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CustomIllegalArgumentException(String message) {
        this.message = message;
    }
}
