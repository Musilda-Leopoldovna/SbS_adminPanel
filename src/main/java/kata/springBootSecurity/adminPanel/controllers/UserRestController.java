package kata.springBootSecurity.adminPanel.controllers;

import kata.springBootSecurity.adminPanel.dto.UserDto;
import kata.springBootSecurity.adminPanel.service.UserRestService;
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
    public UserDto getUser(Principal principal) {
        return apiService.getByUsername(principal.getName());
    }
}
