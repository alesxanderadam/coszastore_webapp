package alticshaw.com.coszastore.exception;

public class ConflictCustomException extends RuntimeException{
    private String message;
    private int statusCode;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public ConflictCustomException(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
    public ConflictCustomException(){}
}
