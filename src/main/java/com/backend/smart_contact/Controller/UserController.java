package com.backend.smart_contact.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/index")
    public String dashboardHandler(){
        return "normal/dashboard";
    }

}
