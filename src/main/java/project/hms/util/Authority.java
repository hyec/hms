package project.hms.util;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import project.hms.model.Employee;
import project.hms.model.User;

import java.util.Collection;
import java.util.LinkedList;

/**
 * 用户角色工具库
 */
public class Authority {

    public static GrantedAuthority USER_ROLE = new SimpleGrantedAuthority("ROLE_USER");
    public static GrantedAuthority CLEANER_ROLE = new SimpleGrantedAuthority("ROLE_CLEANER");
    public static GrantedAuthority CASHIER_ROLE = new SimpleGrantedAuthority("ROLE_CASHIER");
    public static GrantedAuthority MANAGER_ROLE = new SimpleGrantedAuthority("ROLE_MANAGER");

    /**
     * 根据用户生成权限集合
     *
     * @param user 用户
     * @return 权限集合
     */
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
