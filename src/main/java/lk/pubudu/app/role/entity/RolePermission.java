package lk.pubudu.app.role.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "role_permission")
public class RolePermission implements Serializable {
    @Serial
    private static final long serialVersionUID = -2615437460171337653L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "permission_name", nullable = false, columnDefinition = "VARCHAR(100)")
    private String permissionName;
}
