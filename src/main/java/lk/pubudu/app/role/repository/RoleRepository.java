package lk.pubudu.app.role.repository;

import lk.pubudu.app.role.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByRole(String role);

    @Query("SELECT R FROM Role R WHERE R.role = ?2 AND R.id != ?1")
    Optional<Role> getRoleByNameAndId(Integer id, String role);

    @Query("SELECT R FROM Role R WHERE R.role LIKE ?1")
    List<Role> findRolesByRoleName(String query);

    @Query(value = "SELECT COUNT(*) FROM user_role WHERE role_id=?1", nativeQuery = true)
    Integer getUserCountByRoleId(Integer id);

}
