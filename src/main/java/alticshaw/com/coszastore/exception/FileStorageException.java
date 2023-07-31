package alticshaw.com.coszastore.exception;

public class FileStorageException extends RuntimeException{
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public FileStorageException(String message) {
        this.message = message;
    }
}
