package project.hms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.hms.models.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findById(int id);

    @Query("select u from User u where u.username = :username and u.password = :password")
    Optional<User> authorize(String username, String password);

}
