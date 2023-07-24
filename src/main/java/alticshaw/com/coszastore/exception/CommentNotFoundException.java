package alticshaw.com.coszastore.exception;

public class CommentNotFoundException extends RuntimeException{
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CommentNotFoundException(String message) {
        this.message = message;
    }
}
