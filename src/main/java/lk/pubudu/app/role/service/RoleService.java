package lk.pubudu.app.role.service;

import lk.pubudu.app.role.entity.Role;
import lk.pubudu.app.role.repository.RolePermissionRepository;
import lk.pubudu.app.role.repository.RoleRepository;
import lk.pubudu.app.user.repository.UserRepository;
import lk.pubudu.app.util.Transformer;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private final Transformer transformer;

    @Transactional
    public void createRole(Role role) {
        Optional<Role> availability = roleRepository.findByRole(role.getRole());
        if (availability.isPresent()) throw new DuplicateKeyException("A role is already exists with this role name");
        roleRepository.save(role);
    }

}
