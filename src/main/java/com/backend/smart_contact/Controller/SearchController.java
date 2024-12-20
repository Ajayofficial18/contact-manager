package com.backend.smart_contact.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.backend.smart_contact.Entities.Contact;
import com.backend.smart_contact.Entities.User;
import com.backend.smart_contact.Repository.ContactRepository;
import com.backend.smart_contact.Repository.UserRepository;

@RestController
public class SearchController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ContactRepository contactRepo;

    // search handler
    @GetMapping("/search/{query}")
    public ResponseEntity<?> search(@PathVariable("query") String query, Principal principal){

        // System.out.println(query);
        User user=this.userRepo.getUserByUserName(principal.getName());
        List<Contact> contacts=this.contactRepo.findByNameContainingAndUser(query,user);
        // System.out.println(contacts);

        return ResponseEntity.ok(contacts);
    }
    
}
