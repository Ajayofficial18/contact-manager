package com.backend.smart_contact.Controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public void addCommonData(Model m, Principal principal) {
        String username = principal.getName();
        User user = userRepoObj.getUserByUserName(username);
        m.addAttribute("user", user);
    }

    // dashboard handler
    @RequestMapping("/index")
    public String dashboardHandler(Model model) {
        model.addAttribute("Tittle", "User Dashboard - ContactManager");
        return "normal/dashboard";
    }

    // Add contact handler
    @GetMapping("/add-contact")
    public String addContactHandler(Model model) {
        model.addAttribute("Tittle", "Add Contact - ContactManager");
        model.addAttribute("contact", new Contact());
        return "normal/add_contact_form";
    }

    // process-contact handler
    @PostMapping("/process-contact")
    public String processContactForm(@ModelAttribute Contact contactData,
            @RequestParam("profileImage") MultipartFile file, Model model, Principal principal, RedirectAttributes redirectAttributes) {
        try {
            String username = principal.getName();// finding user name
            User user = userRepoObj.getUserByUserName(username);// getting user by username

            // Processing and uploading file
            if (file.isEmpty()) {
                System.out.println("file is empty");
            } else {
                contactData.setImage(file.getOriginalFilename());
                File saveFile = new ClassPathResource("static/img").getFile();
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("img is uploaded");
            }

            user.getContacts().add(contactData);// adding list of contact to user
            contactData.setUser(user);// adding user to contact
            userRepoObj.save(user);// saving user
            model.addAttribute("Tittle", "Add Contact - ContactManager");
            System.out.println("Data " + contactData);

            // handling alert message
            // Success message using RedirectAttributes
            redirectAttributes.addFlashAttribute("message", "Your Contact is added, Add more contacts!");
            redirectAttributes.addFlashAttribute("alertType", "success");
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
            e.printStackTrace();

            // handling alert message
            // Error message using RedirectAttributes
            redirectAttributes.addFlashAttribute("message", "Something went wrong! Try Again.");
            redirectAttributes.addFlashAttribute("alertType", "danger");
        }

        return "redirect:/user/add-contact"; // Redirect after processing the form
    }


    // show or view contacts
    @GetMapping("/show-contacts")
    public String viewContactsHandler(Model m){
        m.addAttribute("Tittle", "All Contacts - ContactManager");
        return "normal/show_contacts";
    }

}
