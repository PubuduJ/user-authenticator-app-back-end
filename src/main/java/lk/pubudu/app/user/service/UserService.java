package lk.pubudu.app.user.service;

import lk.pubudu.app.dto.UserDTO;
import lk.pubudu.app.user.entity.User;
import lk.pubudu.app.user.repository.UserRepository;
import lk.pubudu.app.util.Transformer;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final Transformer transformer;

    @Transactional(rollbackFor = Throwable.class)
    public UserDTO createUser(UserDTO userDTO) {
        Optional<User> availability = userRepository.findByEmail(userDTO.getEmail());
        if (availability.isPresent()) throw new DuplicateKeyException("A user is already exists with this email id");
        User incomingUser = transformer.toUserEntity(userDTO);

        return null;
    }

    @Transactional(rollbackFor = Throwable.class)
    public void deleteUser(Long id) {

    }

    @Transactional(rollbackFor = Throwable.class)
    public UserDTO updateUser(UserDTO userDTO) {
        return null;
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
