package lk.pubudu.app.auth.service;

import jakarta.persistence.EntityNotFoundException;
import lk.pubudu.app.config.JwtService;
import lk.pubudu.app.dto.AuthenticationRequestDTO;
import lk.pubudu.app.dto.AuthenticationResponseDTO;
import lk.pubudu.app.dto.PasswordDTO;
import lk.pubudu.app.dto.UserDetailsDTO;
import lk.pubudu.app.exception.NotFoundException;
import lk.pubudu.app.role.entity.Role;
import lk.pubudu.app.role.entity.RolePermission;
import lk.pubudu.app.user.entity.User;
import lk.pubudu.app.user.repository.UserRepository;
import lk.pubudu.app.util.EMailSender;
import lk.pubudu.app.util.HashGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final HashGenerator hashGenerator;
    private final EMailSender eMailSender;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new EntityNotFoundException("Invalid username or password"));

        Set<Role> roleSet = user.getRoleSet();
        HashMap<String, List<String>> scopes = new HashMap<>();

        Set<String> fullPermissions = new HashSet<>();
        fullPermissions.add("profile_");
        for (Role role : roleSet) {
            List<RolePermission> permissions = role.getRolePermissions();
            for (RolePermission permission : permissions) {
                fullPermissions.add(permission.getPermissionName());
            }
        }

        scopes.put("permissions", new ArrayList<>(fullPermissions));
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("scopes", scopes);

        String jwtToken = jwtService.generateToken(claims, user);
        return new AuthenticationResponseDTO(jwtToken);
    }

    @Transactional(rollbackFor = Throwable.class)
    public String forgotPassword(String email) {
        Optional<User> availability = userRepository.findByEmail(email);
        if (availability.isEmpty()) throw new NotFoundException("No user available with the provided Email address");

        String password = generateTemporaryPassword();
        User user = availability.get();
        user.setPassword(hashGenerator.generate(password));
        user.setFresh(true);
        userRepository.save(user);
        eMailSender.sendResetPasswordMail(user, password);
        return "Success";
    }

    @Transactional(rollbackFor = Throwable.class)
    public String resetPassword(PasswordDTO passwordDTO) {
        Optional<User> availability = userRepository.findByEmail(passwordDTO.getEmail());
        if (availability.isEmpty()) throw new NotFoundException("No user available with the provided Email address");
        User user = availability.get();

        if (!hashGenerator.isMatching(passwordDTO.getTemporaryPassword(), user.getPassword())) {
            throw new NotFoundException("Invalid temporary password");
        }

        user.setPassword(hashGenerator.generate(passwordDTO.getNewPassword()));
        user.setFresh(false);
        userRepository.save(user);
        return "Success";
    }

    public UserDetailsDTO getLoggedUserDetails(String email) {
        Optional<User> availability = userRepository.findByEmail(email);
        if (availability.isEmpty()) throw new NotFoundException("No user available with the provided Email address");
        User user = availability.get();
        Set<Role> roleSet = user.getRoleSet();
        List<String> roleNameList = new ArrayList<>();
        HashSet<String> permissionSet = new HashSet<>();
        for (Role role : roleSet) {
            roleNameList.add(role.getRole());
            List<RolePermission> rolePermissions = role.getRolePermissions();
            for (RolePermission rolePermission : rolePermissions) {
                permissionSet.add(rolePermission.getPermissionName());
            }
        }
        UserDetailsDTO userDetails = new UserDetailsDTO();
        userDetails.setImg(user.getImg());
        userDetails.setFirstName(user.getFirstName());
        userDetails.setLastName(user.getLastName());
        userDetails.setEmail(user.getEmail());
        userDetails.setMobile(user.getMobile());
        userDetails.setRoleNames(roleNameList);
        ArrayList<String> permissionList = new ArrayList<>(permissionSet);
        userDetails.setPermissionNames(permissionList);
        return userDetails;
    }

    public String generateTemporaryPassword() {
        String capitalLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String simpleLetters = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String specialChars = "!@#$%^&*_=+-?<>";

        String allChars = capitalLetters + simpleLetters + numbers + specialChars;
        Random random = new Random();

        int length = random.nextInt(7) + 8;
        char[] password = new char[length];

        password[0] = capitalLetters.charAt(random.nextInt(capitalLetters.length()));
        password[1] = simpleLetters.charAt(random.nextInt(simpleLetters.length()));
        password[2] = numbers.charAt(random.nextInt(numbers.length()));
        password[3] = specialChars.charAt(random.nextInt(specialChars.length()));

        for (int i = 4; i < length; i++) {
            password[i] = allChars.charAt(random.nextInt(allChars.length()));
        }

        return new String(password);
    }

}
