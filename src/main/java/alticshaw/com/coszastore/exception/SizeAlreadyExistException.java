package alticshaw.com.coszastore.exception;

public class SizeAlreadyExistException extends RuntimeException{
    private String message;

    public SizeAlreadyExistException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
