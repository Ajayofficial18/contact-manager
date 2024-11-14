package com.backend.smart_contact.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.backend.smart_contact.Entities.User;
import com.backend.smart_contact.Repository.UserRepository;


@Component
public class UserServices {

    @Autowired
    private UserRepository userRepository; 

    // get user by username 
    public User getUserByUsername(String username){
        return userRepository.getUserByUserName(username);
    }

    // save user
    public User saveUser(User user){
        return userRepository.save(user);
    }


}
