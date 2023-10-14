package lk.pubudu.app.user.repository;

import lk.pubudu.app.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query("SELECT U FROM User U WHERE U.firstName LIKE ?1 OR U.lastName LIKE ?1 OR U.email LIKE ?1 OR U.mobile LIKE ?1")
    List<User> findUsersByQuery(String query);

}
