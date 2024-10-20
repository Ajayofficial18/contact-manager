package com.backend.smart_contact.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.backend.smart_contact.Entities.User;
import com.backend.smart_contact.Repository.UserRepository;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepoObj;

    // dashboard handler
    @RequestMapping("/index")
    public String dashboardHandler(Model model, Principal principal){

        String username=principal.getName();
        User user = userRepoObj.getUserByUserName(username);
        model.addAttribute("Title", "Dashboard-ContactManager");
        model.addAttribute("user", user);
        return "normal/dashboard";
    }

}
