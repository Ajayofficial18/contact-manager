package com.backend.smart_contact.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import com.backend.smart_contact.Entities.User;
import com.backend.smart_contact.Repository.UserRepository;

@Configuration
@EnableWebSecurity
public class MyConfig {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            User user = userRepository.getUserByUserName(username);
            if (user == null) {
                throw new UsernameNotFoundException("User not found");
            }
            return new CustomUserDetails(user); // Create a CustomUserDetails class
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeHttpRequests()
            .requestMatchers("/user/**").authenticated()  // Protect URLs starting with /user
            .anyRequest().permitAll()  // Allow all other URLs without authentication
            .and()
            .formLogin()
            .loginPage("/signin")  // Custom login page
            .loginProcessingUrl("/doLogin")
            .defaultSuccessUrl("/user/index", true)  // Redirect after successful login
            .and()
            .logout()
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login?logout")
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID");

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // For encoding passwords
    }
}
