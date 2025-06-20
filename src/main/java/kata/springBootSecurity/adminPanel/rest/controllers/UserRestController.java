package kata.springBootSecurity.adminPanel.rest.controllers;

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
    public UserDto getCurrentUser(Principal principal) {
        return apiService.getByUsername(principal.getName());
//        // вариант для работы при сложных конфигурациях прокси и фильтров (?) изучить вариант
//        Authentication currentAuthUser = SecurityContextHolder.getContext().getAuthentication();;
//        return apiService.getByUsername(currentAuthUser.getName());
    }
}
