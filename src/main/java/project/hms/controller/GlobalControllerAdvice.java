package project.hms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import project.hms.data.UserInfo;
import project.hms.service.AuthorizeService;

import java.security.Principal;

@ControllerAdvice
public class GlobalControllerAdvice {


    private final AuthorizeService authorizeService;

    @Autowired
    public GlobalControllerAdvice(AuthorizeService authorizeService) {
        this.authorizeService = authorizeService;
    }

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
