package project.hms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.hms.data.UserDetail;
import project.hms.data.UserInfo;
import project.hms.model.User;
import project.hms.repository.UserRepository;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;

@Service
public class AuthorizeService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public AuthorizeService(UserRepository users) {
        this.userRepository = users;
    }

    public UserInfo getUserInfo(String username) {
        Optional<User> userOptional = userRepository.findByUsernameIgnoreCase(username);

        return userOptional.map(UserInfo::new).orElseGet(UserInfo::new);
    }

    public UserDetail getUserDetail(UserInfo info) {
        Collection<GrantedAuthority> authorities = new LinkedList<>();

        if (!info.isValid()) {
            return null;
        }

        authorities.add(new SimpleGrantedAuthority("USER"));

        if (info.isCashier())
            authorities.add(new SimpleGrantedAuthority("CASHIER"));

        if (info.isCleaner())
            authorities.add(new SimpleGrantedAuthority("CLEANER"));

        if (info.isManager())
            authorities.add(new SimpleGrantedAuthority("MANAGER"));

        return new UserDetail(info.getUsername(), info.getPassword(), authorities);
    }

    public UserDetail getUserDetail(String username) {
        UserInfo info = this.getUserInfo(username);
        return getUserDetail(info);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDetail userDetail = getUserDetail(username);

        if (userDetail == null) {
            throw new UsernameNotFoundException("Username \"" + username + "\" not found.");
        }

        return userDetail;
    }
}
