package alticshaw.com.coszastore.exception;

public class BlogNotFoundException extends RuntimeException{
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BlogNotFoundException(String message) {
        this.message = message;
    }
}
