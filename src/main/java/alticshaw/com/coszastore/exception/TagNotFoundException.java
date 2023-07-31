package alticshaw.com.coszastore.exception;

public class TagNotFoundException extends RuntimeException{
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TagNotFoundException(String message) {
        this.message = message;
    }
}
