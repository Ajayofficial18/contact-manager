package com.backend.smart_contact.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.backend.smart_contact.Entities.User;
import com.backend.smart_contact.HelperMessage.Message;
import com.backend.smart_contact.Repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepositoryObject;

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
    public String do_registerHandler(@ModelAttribute("user") User user, @RequestParam(value = "agreement", defaultValue = "false") boolean agreement, Model model, RedirectAttributes redirectAttributes){

        try {

            if(!agreement){
                System.out.println("you have not agreed the terms and condtions");
                throw new Exception("you have not agreed the terms and condtions");
            }
    
            user.setRole("Role_user");
            user.setEnabled(true);
    
            User result = userRepositoryObject.save(user);

            // =====old code=======

            // System.out.println("Agreement "+agreement);
            // System.out.println("Model "+model);
            // System.out.println("User "+user);
            // model.addAttribute("user", new User());
            // session.setAttribute("message", new Message("Successfully Registered !!", "alert-success"));

            model.addAttribute("user", new User());
            // Add success message as a flash attribute
            redirectAttributes.addFlashAttribute("message", new Message("Successfully Registered !!", "alert-success"));

            return "redirect:/signup";
            
        } catch (Exception e) {
            e.printStackTrace();
            
            // =====old code=======

            // model.addAttribute("user", user);
            // session.setAttribute("message", new Message("Something went Wrong !!", "alert-error"));
            // return "signup";

            model.addAttribute("user", user);
            // Add error message as a flash attribute
            redirectAttributes.addFlashAttribute("message", new Message("Something went Wrong !!", "alert-danger"));
            return "redirect:/signup";
        }
        
    }

    

}
