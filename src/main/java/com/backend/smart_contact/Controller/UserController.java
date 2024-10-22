package com.backend.smart_contact.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.backend.smart_contact.Entities.Contact;
import com.backend.smart_contact.Entities.User;
import com.backend.smart_contact.Repository.UserRepository;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepoObj;

    // common code for the urls
    @ModelAttribute
    public void addCommonData(Model m, Principal principal){
        String username=principal.getName();
        User user = userRepoObj.getUserByUserName(username);
        m.addAttribute("user", user);
    }

    // dashboard handler
    @RequestMapping("/index")
    public String dashboardHandler(Model model){
        model.addAttribute("Tittle", "User Dashboard - ContactManager");
        return "normal/dashboard";
    }

    // Add contact handler
    @GetMapping("/add-contact")
    public String addContactHandler(Model model){
        model.addAttribute("Tittle","Add Contact - ContactManager");
        model.addAttribute("contact", new Contact());
        return "normal/add_contact_form";
    }

}
