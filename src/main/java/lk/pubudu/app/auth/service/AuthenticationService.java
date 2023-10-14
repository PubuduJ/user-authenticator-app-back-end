package lk.pubudu.app.auth.service;

import jakarta.persistence.EntityNotFoundException;
import lk.pubudu.app.config.JwtService;
import lk.pubudu.app.dto.AuthenticationRequestDTO;
import lk.pubudu.app.dto.AuthenticationResponseDTO;
import lk.pubudu.app.role.entity.Role;
import lk.pubudu.app.role.entity.RolePermission;
import lk.pubudu.app.user.entity.User;
import lk.pubudu.app.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new EntityNotFoundException("Invalid username or password"));

        Set<Role> roleSet = user.getRoleSet();
        HashMap<String, List<String>> scopes = new HashMap<>();

        Set<String> fullPermissions = new HashSet<>();
        fullPermissions.add("home_");
        for (Role role : roleSet) {
            Set<RolePermission> permissions = role.getRolePermissions();
            for (RolePermission permission : permissions) {
                fullPermissions.add(permission.getPermissionName());
            }
        }

        scopes.put("permissions", new ArrayList<>(fullPermissions));
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("scopes" , scopes);

        String jwtToken = jwtService.generateToken(claims, user);
        return new AuthenticationResponseDTO(jwtToken);
    }

}
