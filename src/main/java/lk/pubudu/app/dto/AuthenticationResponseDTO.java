package lk.pubudu.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponseDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 5644110009780401837L;
    private String token;
}
