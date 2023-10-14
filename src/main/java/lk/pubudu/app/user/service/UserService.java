package lk.pubudu.app.user.service;

import lk.pubudu.app.dto.UserDTO;
import lk.pubudu.app.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

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
        return null;
    }

}
