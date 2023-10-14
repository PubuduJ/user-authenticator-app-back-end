package lk.pubudu.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -9108140182766637630L;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
