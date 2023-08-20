package alticshaw.com.coszastore.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactRequest {
    @NotNull(message = "Email can not be null!")
    @NotEmpty(message = "Email can not be empty!")
    @Email(message = "Email must be in right format!",
            regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
    private String email;

    @NotNull(message = "Message can not be null!")
    @NotEmpty(message = "Message can not be empty!")
    private String message;
}
