package alticshaw.com.coszastore.exception;

public class CouponAlreadyExistException extends RuntimeException{
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CouponAlreadyExistException(String message) {
        this.message = message;
    }
}
