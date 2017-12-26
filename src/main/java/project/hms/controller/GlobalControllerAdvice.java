package project.hms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import project.hms.data.UserInfo;
import project.hms.service.AuthorizeService;

import java.security.Principal;

/**
 * 全局控制器附加信息
 */
@ControllerAdvice
public class GlobalControllerAdvice {


    private final AuthorizeService authorizeService;

    @Autowired
    public GlobalControllerAdvice(AuthorizeService authorizeService) {
        this.authorizeService = authorizeService;
    }

    /**
     * 为所有view添加用户信息
     *
     * @param principal 用户认证信息
     */
    @ModelAttribute("userInfo")
    public UserInfo userInfo(Principal principal) {

        if (principal == null) {
            return new UserInfo();
        }

        UserDetails userDetails;

        try {
            userDetails = authorizeService.loadUserByUsername(principal.getName());
        } catch (UsernameNotFoundException e) {
            return new UserInfo();
        }

        return new UserInfo(userDetails);
    }

}
