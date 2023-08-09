package alticshaw.com.coszastore.exception;

public class SizeNotFoundException extends RuntimeException{
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SizeNotFoundException(String message) {
        this.message = message;
    }
}
