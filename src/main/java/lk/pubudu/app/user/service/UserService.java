package lk.pubudu.app.user.service;

import lk.pubudu.app.dto.UserDTO;
import lk.pubudu.app.user.entity.User;
import lk.pubudu.app.user.repository.UserRepository;
import lk.pubudu.app.util.Transformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final Transformer transformer;

    @Transactional(rollbackFor = Throwable.class)
    public UserDTO createUser(UserDTO userDTO) {
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

}
