package project.hms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.hms.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsernameIgnoreCase(String username);

    User findByUsername(String username);

}
