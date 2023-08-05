package alticshaw.com.coszastore.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCustomException extends RuntimeException{
    private String message;
    private int statusCode;
}
