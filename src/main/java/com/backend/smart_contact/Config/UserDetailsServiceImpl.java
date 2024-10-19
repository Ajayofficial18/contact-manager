package com.backend.smart_contact.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.backend.smart_contact.Entities.User;
import com.backend.smart_contact.Repository.UserRepository;

public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private UserRepository obj;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // fetching user from database
        User user= obj.getUserByUserName(username);

        if(user==null){
            throw new UsernameNotFoundException("Could not found user !");
        }
        CustomUserDetails customUserDetails = new CustomUserDetails(user);
        return customUserDetails;
    }

}
