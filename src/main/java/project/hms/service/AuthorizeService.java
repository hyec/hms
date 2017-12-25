package project.hms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.hms.data.UserDetail;
import project.hms.model.User;
import project.hms.repository.UserRepository;
import project.hms.util.Authority;

import java.util.Optional;

@Service
public class AuthorizeService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public AuthorizeService(UserRepository users) {
        this.userRepository = users;
    }

    /**
     * 根据用户名获得用户认证信息
     *
     * @param username 用户名
     * @return 用户信息
     * @throws UsernameNotFoundException 用户不存在
     * @see Authority
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> userOptional = userRepository.findByUsernameIgnoreCase(username);

        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException("Username \"" + username + "\" not found.");
        }

        User user = userOptional.get();

        return new UserDetail(user.getUsername(), user.getPassword(), Authority.getAuthorities(user));
    }
}
