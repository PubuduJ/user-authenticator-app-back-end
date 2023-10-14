package lk.pubudu.app.user.entity;

import jakarta.persistence.*;
import lk.pubudu.app.role.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
@ToString(exclude = "roleSet")
public class User implements UserDetails {
    @Serial
    private static final long serialVersionUID = 8778877586868040985L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String img;
    @Column(name = "first_name", nullable = false, columnDefinition = "VARCHAR(100)")
    private String firstName;
    @Column(name = "last_name", nullable = false, columnDefinition = "VARCHAR(100)")
    private String lastName;
    @Column(nullable = false, columnDefinition = "VARCHAR(100)", unique = true)
    private String email;
    @Column(nullable = false, columnDefinition = "VARCHAR(1000)")
    private String password;
    @Column(nullable = false, columnDefinition = "VARCHAR(50)")
    private String mobile;
    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean isFresh;
    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roleSet = new HashSet<>();

    public User(String img, String firstName, String lastName, String email, String password, String mobile, Boolean isFresh) {
        this.img = img;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.mobile = mobile;
        this.isFresh = isFresh;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roleSet) {
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
