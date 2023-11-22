package lk.pubudu.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 533931909855584586L;
    private String img;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private List<String> roleNames;
    private List<String> permissionNames;
}
