package kata.springBootSecurity.adminPanel.mvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class UserController {

    @GetMapping("/user")
    public String getUserData() {
        return "user";
    }

    @GetMapping("/")
    public String rootPage(Principal principal) {
        if(principal != null) {
            return "redirect:/user";
        }
        return "redirect:/login";
    }
}
