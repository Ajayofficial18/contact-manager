package com.backend.smart_contact.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/home")
    public String homeHandler(Model model){
        model.addAttribute("message", "this is ajay gour Learning thymeleaf!");
        model.addAttribute("Title", "HomePage-ContactManager");
        return "home";
    }

    @RequestMapping("/about")
    public String aboutHandler(Model model){
        model.addAttribute("message", "this is ajay gour Learning thymeleaf!");
        model.addAttribute("Title", "AboutPage-ContactManager");
        return "about";
    }

}
