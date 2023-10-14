package lk.pubudu.app.util;

import lk.pubudu.app.dto.UserDTO;
import lk.pubudu.app.role.entity.Role;
import lk.pubudu.app.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class Transformer {

    private final ModelMapper modelMapper;

    public User toUserEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setImg(userDTO.getImg());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setMobile(userDTO.getMobile());
        return user;
    }

    public UserDTO toUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setImg(user.getImg());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setMobile(user.getMobile());
        Set<Role> roleSet = user.getRoleSet();
        Integer[] roleIds = new Integer[roleSet.size()];
        int i = 0;
        for (Role role : roleSet) {
            roleIds[i++] = role.getId();
        }
        userDTO.setRoleIds(roleIds);
        userDTO.setFresh(user.getFresh());
        return userDTO;
    }

}
