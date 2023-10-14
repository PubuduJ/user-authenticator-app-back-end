package lk.pubudu.app.role.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "role")
@ToString(exclude = "rolePermissionSet")
@EqualsAndHashCode(exclude = "rolePermissionSet")
public class Role implements Serializable {
    @Serial
    private static final long serialVersionUID = 5981801158962140394L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, columnDefinition = "VARCHAR(100)")
    private String role;
    @Setter(AccessLevel.NONE)
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Set<RolePermission> rolePermissions = new HashSet<>();
}
