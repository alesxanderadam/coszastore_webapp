package alticshaw.com.coszastore.exception;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;

public class ExpiredJwtCustomException extends ExpiredJwtException {
    private String message;

    public ExpiredJwtCustomException(Header header, Claims claims, String message) {
        super(header, claims, message);
    }

    public ExpiredJwtCustomException(Header header, Claims claims, String message, Throwable cause) {
        super(header, claims, message, cause);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
