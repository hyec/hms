package project.hms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import project.hms.service.AuthorizeService;

@EnableWebSecurity
public class MultiHttpSecurityConfig {

    private final AuthorizeService authorizeService;

    @Autowired
    public MultiHttpSecurityConfig(AuthorizeService authorizeService) {
        this.authorizeService = authorizeService;
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        return userService;
//    }

    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authorizeService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Configuration
    @EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
    public static class SecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .anyRequest().permitAll()
                    .and()

                    .formLogin()
                    .loginPage("/user/login").permitAll()
                    .and()

                    .csrf().disable();
        }
    }

}
