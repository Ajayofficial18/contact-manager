package com.backend.smart_contact.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.backend.smart_contact.Entities.User;
import com.backend.smart_contact.HelperMessage.Message;
import com.backend.smart_contact.Repository.UserRepository;

import jakarta.validation.Valid;


@Controller
public class HomeController {

    @Autowired
    private PasswordEncoder passwordEncoder;

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
    public String do_registerHandler(@Valid @ModelAttribute("user") User user, BindingResult result, @RequestParam(value = "agreement", defaultValue = "false") boolean agreement, Model model, RedirectAttributes redirectAttributes){

        try {

            if (userRepositoryObject.findByEmail(user.getEmail()) != null) {
                result.rejectValue("email", "error.user", "An account with this email already exists.");
            }

            if(!agreement){
                System.out.println("you have not agreed the terms and condtions");
                throw new Exception("you have not agreed the terms and condtions");
            }
    
            if (result.hasErrors()) {
                model.addAttribute("user", user);
                return "signup"; // Return back to the form if validation fails
            }

            user.setRole("ROLE_USER");
            user.setEnabled(true);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
    
            userRepositoryObject.save(user);
            model.addAttribute("user", new User());
            // Add success message as a flash attribute
            redirectAttributes.addFlashAttribute("message", new Message("Successfully Registered !!", "alert-success"));
            return "redirect:/signup";
            
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("user", user);
            // Add error message as a flash attribute
            redirectAttributes.addFlashAttribute("message", new Message("Something went Wrong !!", "alert-danger"));
            return "redirect:/signup";
        }
        
    }

    // LoginPageHandler
    @GetMapping("/signin")
    public String loginHandler(Model model){
        model.addAttribute("Title", "Login-Page");
        return "login";
    }

}
