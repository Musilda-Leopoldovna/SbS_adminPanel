package kata.springBootSecurity.adminPanel.controllers;


import kata.springBootSecurity.adminPanel.dto.UserDto;
import kata.springBootSecurity.adminPanel.service.UserRestService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiRestController {

    private final UserRestService apiService;

    public ApiRestController(UserRestService service) {
        this.apiService = service;
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return apiService.getById(id);
    }

    @GetMapping("/users")
    public List<UserDto> getAllUsers() {
        return apiService.getAll();
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto,
                                              @RequestParam(name = "roleId") Long roleId) {
        return ResponseEntity.ok(apiService.addNewUser(userDto, roleId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id,
                                              @Valid @RequestBody UserDto userDto,
                                              @RequestParam(name = "updRole") Long updRole) {
        return ResponseEntity.ok(apiService.changeUser(userDto, id, updRole));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (id != null) {
            apiService.delete(id);
        }
        return ResponseEntity.noContent().build();
    }
}
