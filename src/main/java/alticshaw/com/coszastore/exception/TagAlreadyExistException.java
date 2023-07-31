package alticshaw.com.coszastore.exception;

public class TagAlreadyExistException extends RuntimeException{
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TagAlreadyExistException(String message) {
        this.message = message;
    }
}
