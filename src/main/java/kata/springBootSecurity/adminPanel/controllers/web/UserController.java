package kata.springBootSecurity.adminPanel.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import kata.springBootSecurity.adminPanel.entity.User;
import kata.springBootSecurity.adminPanel.service.UserService;

import java.security.Principal;

@Controller
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/user")
    public String getUserData(Model model, Principal principal) {
        User user = service.getUserByUsername(principal.getName());
        model.addAttribute("user", user);
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
