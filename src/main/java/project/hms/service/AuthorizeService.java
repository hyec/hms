package project.hms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.hms.model.Employee;
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

    private static Collection<? extends GrantedAuthority> getAuthorities(User user) {

        Collection<SimpleGrantedAuthority> authorities = new LinkedList<>();

        if (user != null) {
            authorities.add(new SimpleGrantedAuthority("USER"));

            Employee employee = user.getEmployee();
            if (employee != null) {
                authorities.add(new SimpleGrantedAuthority("EMPLOYEE"));
                switch (employee.getType()) {
                    case CLEANER:
                        authorities.add(new SimpleGrantedAuthority("CLEANER"));
                    case CASHIER:
                        authorities.add(new SimpleGrantedAuthority("CASHIER"));
                    case MANAGER:
                        authorities.add(new SimpleGrantedAuthority("MANAGER"));
                }
            }
        }

        return authorities;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> userOptional = userRepository.findByUsernameIgnoreCase(username);

        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException("Username \"" + username + "\" not found.");
        }

        return new UserDetailsImpl(userOptional.get());
    }

    private static class UserDetailsImpl implements UserDetails {

        private final String username;
        private final String password;
        private final Collection<? extends GrantedAuthority> authorities;

        UserDetailsImpl(User user) {
            this.username = user.getUsername();
            this.password = user.getPassword();
            this.authorities = AuthorizeService.getAuthorities(user);
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return authorities;
        }

        @Override
        public String getPassword() {
            return password;
        }

        @Override
        public String getUsername() {
            return username;
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
