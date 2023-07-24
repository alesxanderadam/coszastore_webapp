package alticshaw.com.coszastore.exception;

public class NotImageException extends RuntimeException{
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NotImageException(String message) {
        this.message = message;
    }
}
