package alticshaw.com.coszastore.exception;


public class UserNotFoundException extends RuntimeException{
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserNotFoundException(String message) {
        this.message = message;
    }
}
