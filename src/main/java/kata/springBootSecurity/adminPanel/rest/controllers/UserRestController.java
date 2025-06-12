package kata.springBootSecurity.adminPanel.rest.controllers;

import jakarta.servlet.http.HttpSession;
import kata.springBootSecurity.adminPanel.rest.dto.UserDto;
import kata.springBootSecurity.adminPanel.rest.services.UserRestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class UserRestController {

    private final UserRestService apiService;

    public UserRestController(UserRestService service) {
        this.apiService = service;
    }

    @GetMapping
    public UserDto getCurrentUser(Principal principal, HttpSession session) {
        System.out.println("SESSION ID: " + session.getId());
        System.out.println("Principal: " + principal);
        return apiService.getByUsername(principal.getName());
    }
}
