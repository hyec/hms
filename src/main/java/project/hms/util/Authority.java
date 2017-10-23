package project.hms.util;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import project.hms.model.Employee;
import project.hms.model.User;

import java.util.Collection;
import java.util.LinkedList;

public class Authority {

    public static GrantedAuthority USER_ROLE = new SimpleGrantedAuthority("USER");
    public static GrantedAuthority CLEANER_ROLE = new SimpleGrantedAuthority("CLEANER");
    public static GrantedAuthority CASHIER_ROLE = new SimpleGrantedAuthority("CASHIER");
    public static GrantedAuthority MANAGER_ROLE = new SimpleGrantedAuthority("MANAGER");

    public static Collection<GrantedAuthority> getAuthorities(User user) {
        Collection<GrantedAuthority> authorities = new LinkedList<>();

        if (user == null)
            return authorities;

        authorities.add(USER_ROLE);

        Employee employee = user.getEmployee();
        if (employee == null)
            return authorities;

        switch (employee.getType()) {
            case MANAGER:
                authorities.add(MANAGER_ROLE);
            case CASHIER:
                authorities.add(CASHIER_ROLE);
            case CLEANER:
                authorities.add(CLEANER_ROLE);
        }

        return authorities;
    }

}
