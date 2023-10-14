package lk.pubudu.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -5120540903909433091L;
    private Long id;
    private String img;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private Integer roleIds;
    private Boolean fresh;
}
