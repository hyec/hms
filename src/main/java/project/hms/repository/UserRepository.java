package project.hms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.hms.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {


    Optional<User> findById(int id);

    User findByUsernameIgnoreCase(String username);

    User findByUsername(String username);

}
