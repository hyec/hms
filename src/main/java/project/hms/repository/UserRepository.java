package project.hms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.hms.model.User;

import java.util.Optional;

/**
 * 用户仓库类
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * 根据用户名不分大小写获取用户
     *
     * @param username 用户名，不区分大小写
     * @return 用户
     */
    Optional<User> findByUsernameIgnoreCase(String username);

    /**
     * 根据用户名获得用户
     * @param username 用户名
     * @return 用户
     */
    User findByUsername(String username);

}
