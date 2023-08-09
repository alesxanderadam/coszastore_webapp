package alticshaw.com.coszastore.exception;

public class ColorNotFoundException extends RuntimeException{
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ColorNotFoundException(String message) {
        this.message = message;
    }
}
