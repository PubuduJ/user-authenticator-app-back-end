package lk.pubudu.app.role.service;

import lk.pubudu.app.dto.RoleDTO;
import lk.pubudu.app.exception.NotFoundException;
import lk.pubudu.app.role.entity.Role;
import lk.pubudu.app.role.entity.RolePermission;
import lk.pubudu.app.role.repository.RolePermissionRepository;
import lk.pubudu.app.role.repository.RoleRepository;
import lk.pubudu.app.user.repository.UserRepository;
import lk.pubudu.app.util.Transformer;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private final Transformer transformer;

    @Transactional(rollbackFor = Throwable.class)
    public RoleDTO createRole(RoleDTO roleDTO) {
        Optional<Role> availability = roleRepository.findByRole(roleDTO.getRole());
        if (availability.isPresent()) throw new DuplicateKeyException("A role is already exists with this role name");
        Role role = roleRepository.save(transformer.toRoleEntity(roleDTO));
        return transformer.toRoleDTO(role);
    }

    @Transactional(rollbackFor = Throwable.class)
    public void deleteRole(Integer id) {
        Optional<Role> availability = roleRepository.findById(id);
        if (availability.isEmpty()) throw new NotFoundException("Role doesn't exist");
        Role existingRole = availability.get();
        Set<RolePermission> rolePermissions = existingRole.getRolePermissions();
        roleRepository.deleteById(id);
        for (RolePermission rolePermission : rolePermissions) {
            rolePermissionRepository.deleteById(rolePermission.getId());
        }
    }

    @Transactional(rollbackFor = Throwable.class)
    public RoleDTO updateRole(RoleDTO roleDTO) {
        Optional<Role> availability = roleRepository.getRoleByNameAndId(roleDTO.getId(), roleDTO.getRole());
        if (availability.isPresent()) throw new DuplicateKeyException("A role is already exists with this role name");
        Role existingRole = roleRepository.findById(roleDTO.getId()).get();
        Set<RolePermission> rolePermissions = existingRole.getRolePermissions();
        Role role = transformer.toRoleEntity(roleDTO);
        existingRole.setRole(role.getRole());
        existingRole.setRolePermissions(role.getRolePermissions());
        for (RolePermission rolePermission : rolePermissions) {
            rolePermissionRepository.deleteById(rolePermission.getId());
        }
        return transformer.toRoleDTO(existingRole);
    }

    public List<RoleDTO> getRolesByRoleName(String q) {
        String query = "%".concat(q).concat("%");
        List<Role> filteredRoles = roleRepository.findRolesByRoleName(query);
        ArrayList<RoleDTO> filteredRoleDTOs = new ArrayList<>();
        for (Role role : filteredRoles) {
            RoleDTO roleDTO = transformer.toRoleDTO(role);
            roleDTO.setUserCount(roleRepository.getUserCountByRoleId(role.getId()));
            filteredRoleDTOs.add(roleDTO);
        }
        return filteredRoleDTOs;
    }

    public List<Role> getAll() {
        return roleRepository.findAll();
    }

}
