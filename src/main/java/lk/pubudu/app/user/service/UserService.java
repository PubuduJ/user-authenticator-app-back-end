package lk.pubudu.app.user.service;

import lk.pubudu.app.dto.UserDTO;
import lk.pubudu.app.exception.NotFoundException;
import lk.pubudu.app.role.entity.Role;
import lk.pubudu.app.role.repository.RoleRepository;
import lk.pubudu.app.user.entity.User;
import lk.pubudu.app.user.repository.UserRepository;
import lk.pubudu.app.util.EMailSender;
import lk.pubudu.app.util.HashGenerator;
import lk.pubudu.app.util.Transformer;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final HashGenerator hashGenerator;
    private final EMailSender eMailSender;
    private final Transformer transformer;

    @Transactional(rollbackFor = Throwable.class)
    public UserDTO createUser(UserDTO userDTO) {
        Optional<User> availability = userRepository.findByEmail(userDTO.getEmail());
        if (availability.isPresent()) throw new DuplicateKeyException("A user is already exists with this email id");
        User incomingUser = transformer.toUserEntity(userDTO);
        String tempPassword = generateTemporaryPassword();
        incomingUser.setPassword(hashGenerator.generate(tempPassword));
        incomingUser.setFresh(true);
        User user = userRepository.save(incomingUser);
        Integer[] roleIds = userDTO.getRoleIds();
        for (Integer roleId : roleIds) {
            Optional<Role> role = roleRepository.findById(roleId);
            user.getRoleSet().add(role.get());
        }
//        eMailSender.sendWelcomeMail(userDTO, tempPassword);
        return userDTO;
    }

    @Transactional(rollbackFor = Throwable.class)
    public void deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) throw new NotFoundException("User doesn't exist for this email id");
        Set<Role> roles = user.get().getRoleSet();
        roles.clear();
        userRepository.deleteById(user.get().getId());
    }

    @Transactional(rollbackFor = Throwable.class)
    public UserDTO updateUser(UserDTO userDTO) {
        Optional<User> availability = userRepository.findById(userDTO.getId());
        if (availability.isEmpty()) throw new NotFoundException("User doesn't exist for this email id");
        User incomingUser = transformer.toUserEntity(userDTO);
        User existingUser = availability.get();

        existingUser.setImg(incomingUser.getImg());
        existingUser.setFirstName(incomingUser.getFirstName());
        existingUser.setLastName(incomingUser.getLastName());
        existingUser.setMobile(incomingUser.getMobile());

        User updatedUser = userRepository.save(existingUser);
        updatedUser.getRoleSet().clear();
        Integer[] roleIds = userDTO.getRoleIds();
        for (Integer roleId: roleIds) {
            Optional<Role> role = roleRepository.findById(roleId);
            updatedUser.getRoleSet().add(role.get());
        }
        return userDTO;
    }

    public List<UserDTO> getUsersByQuery(String q) {
        List<UserDTO> userDTOList = new ArrayList<>();
        String query = "%".concat(q).concat("%");
        List<User> usersByQuery = userRepository.findUsersByQuery(query);
        for (User user : usersByQuery) {
            UserDTO userDTO = transformer.toUserDTO(user);
            userDTOList.add(userDTO);
        }
        return userDTOList;
    }

    public void resetPassword(Long id) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            String password = generateTemporaryPassword();
            user.setPassword(hashGenerator.generate(password));
            user.setFresh(true);
            userRepository.save(user);
            eMailSender.sendResetPasswordMail(user, password);
        } else throw new NotFoundException("User doesn't exist to reset the password");
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
