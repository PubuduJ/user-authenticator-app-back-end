package lk.pubudu.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 8611368488769193389L;
    private Integer id;
    private String permissionName;
}
