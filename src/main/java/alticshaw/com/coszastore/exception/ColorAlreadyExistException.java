package alticshaw.com.coszastore.exception;

public class ColorAlreadyExistException extends RuntimeException{
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ColorAlreadyExistException(String message) {
        this.message = message;
    }
}
