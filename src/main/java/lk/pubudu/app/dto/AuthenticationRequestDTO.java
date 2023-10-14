package lk.pubudu.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequestDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 6333229621393424742L;
    private String email;
    private String password;
}
