package com.backend.smart_contact.Controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.backend.smart_contact.Entities.Contact;
import com.backend.smart_contact.Entities.User;
import com.backend.smart_contact.Repository.ContactRepository;
import com.backend.smart_contact.Repository.UserRepository;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepoObj;

    @Autowired
    private ContactRepository contactRepoObj;

    // common code for the urls
    @ModelAttribute
    public void addCommonData(Model m, Principal principal) {
        String username = principal.getName();
        User user = userRepoObj.getUserByUserName(username);
        m.addAttribute("user", user);
    }

    // dashboard handler
    @RequestMapping("/index")
    public String dashboardHandler(Model m, Principal principal) {
        // we need to send all the contact of this user
        String username = principal.getName();
        User user = this.userRepoObj.getUserByUserName(username);
        List<Contact> allContacts = this.contactRepoObj.findContactByUser(user.getId());
        m.addAttribute("contacts", allContacts);
        m.addAttribute("Tittle", "User Dashboard - ContactManager");
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
                contactData.setImage("contact.png");
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

        return "redirect:/user/show-contacts"; // Redirect after processing the form
    }

    // show or view contacts
    @GetMapping("/show-contacts")
    public String viewContactsHandler(Model m, Principal principal){
        
        m.addAttribute("Tittle", "All Contacts - ContactManager");
        // we need to send all the contact of this user
        String username = principal.getName();
        User user = this.userRepoObj.getUserByUserName(username);
        List<Contact> allContacts = this.contactRepoObj.findContactByUser(user.getId());
        m.addAttribute("allContacts", allContacts);

        return "normal/show_contacts";
    }

    // showing particular contact
    @RequestMapping("/{cId}/contact")
    public String showContactDetail(@PathVariable("cId") Integer cId, Model m){

        Optional<Contact> optional = contactRepoObj.findById(cId);
        Contact contact = optional.get();

        m.addAttribute("contact", contact);
        m.addAttribute("Tittle", "Contact - ContactManager");
        return "normal/contact_detail";
    }

    // Deleting a particular contact
    @GetMapping("/delete/{cId}")
    public String deleteContact(@PathVariable("cId") Integer cId) {

        Optional<Contact> optional = this.contactRepoObj.findById(cId);
        if(optional.isPresent()) {
            Contact contact = optional.get();
            // Perform the deletion
            this.contactRepoObj.delete(contact);

            // Set flash message after successful deletion
            // redirectAttributes.addFlashAttribute("message", "Contact Deleted Successfully...");
            // redirectAttributes.addFlashAttribute("alertType", "success");
        } else {
            // Handle case where the contact is not found
            // redirectAttributes.addFlashAttribute("message", "Contact not found!");
            // redirectAttributes.addFlashAttribute("alertType", "danger");
        }

        // Redirect back to the contact list
        return "redirect:/user/show-contacts";
    }

    // updating a contact handler
    @GetMapping("/updateContact/{cId}")
    public String updateHandlerSupport(@PathVariable("cId") int cId, Model model) {
        Optional<Contact> contactOptional = contactRepoObj.findById(cId);
        if (contactOptional.isPresent()) {
            model.addAttribute("contact", contactOptional.get());
            model.addAttribute("Tittle", "Update Contact - ContactManager");
            return "normal/update_contact"; // Make sure this template exists
        } else {
            // Handle case where the contact is not found
            return "redirect:/user/show-contacts"; // Redirect if contact is not found
        }
    }

    // process-update handler
    @PostMapping("/update-contact/{cId}")
    public String updateContact(@PathVariable("cId") int cId, @ModelAttribute Contact contactdata, @RequestParam("profileImage") MultipartFile file, RedirectAttributes redirectAttributes) {
        Optional<Contact> optional = this.contactRepoObj.findById(cId);
        
        if (optional.isPresent()) {
            Contact contact = optional.get();
            // Update logic here
            contact.setName(contactdata.getName());
            contact.setSecondName(contactdata.getSecondName());
            contact.setWork(contactdata.getWork());
            contact.setEmail(contactdata.getEmail());
            contact.setPhone(contactdata.getPhone());
            contact.setImage(contactdata.getImage());// this is not working
            try {
                if (file.isEmpty()) {
                    System.out.println("file is empty");
                    contactdata.setImage("contact.png");
                } else {
                    contactdata.setImage(file.getOriginalFilename());
                    File saveFile = new ClassPathResource("static/img").getFile();
                    Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
                    Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("img is uploaded");
                } 
            } 
            catch (Exception e) {
                System.out.println("Error : " + e.getMessage());
                e.printStackTrace();
            }
            contact.setImage(contactdata.getImage());
            contact.setDescription(contactdata.getDescription());
            this.contactRepoObj.save(contact);

            redirectAttributes.addFlashAttribute("message", "Contact updated Successfully...");
            redirectAttributes.addFlashAttribute("alertType", "success");
        } else {
            redirectAttributes.addFlashAttribute("message", "Contact not found!");
            redirectAttributes.addFlashAttribute("alertType", "danger");
        }
        return "redirect:/user/show-contacts";
    }

    // profile handler
    @GetMapping("/profile")
    public String profileHandler(Model m, Principal principal){
        String username = principal.getName();
        User user = userRepoObj.getUserByUserName(username);
        m.addAttribute("Tittle", "User "+user.getName());
        return "normal/profile";
    }
    
    // update or edit user handler
    @GetMapping("/updateUser/{id}")
    public String updateUserHandler(@PathVariable("id") int id, Model m){
        Optional<User> userOptional=userRepoObj.findById(id);
        if(userOptional.isPresent()){
            m.addAttribute("user", userOptional.get());
            m.addAttribute("Tittle", "Edit User");
            return "normal/editProfile";
        }else {
            // Handle case where the user is not found
            return "redirect:/user/profile"; // Redirect if user is not found
        }
    }
}
