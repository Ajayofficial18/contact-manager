package com.backend.smart_contact.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.backend.smart_contact.Entities.User;

@Controller
public class HomeController {

    // homeHandler
    @RequestMapping("/")
    public String homeHandler(Model model){
        model.addAttribute("Title", "HomePage-ContactManager");
        return "home";
    }

    // aboutHandler
    @RequestMapping("/about")
    public String aboutHandler(Model model){
        model.addAttribute("Title", "AboutPage-ContactManager");
        return "about";
    }

    // signUpHandler
    @RequestMapping("/signup")
    public String signUpHandler(Model model){
        model.addAttribute("Title", "SignUpPage-ContactManager");
        model.addAttribute("user", new User());
        return "signup";
    }

    // do_registerHandler
    @RequestMapping(value = "/do_register", method = RequestMethod.POST)
    public String do_registerHandler(@ModelAttribute("user") User user, @RequestParam(value = "agreement", defaultValue = "false") boolean agreement, Model model){
        System.out.println("Agreement "+agreement);
        System.out.println("Model "+model);
        System.out.println("User "+user);
        return "signup";
    }

    

}
