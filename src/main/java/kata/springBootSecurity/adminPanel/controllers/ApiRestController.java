package kata.springBootSecurity.adminPanel.controllers;


import kata.springBootSecurity.adminPanel.dto.UserDto;
import kata.springBootSecurity.adminPanel.service.UserRestService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class ApiRestController {

    private final UserRestService apiService;

    public ApiRestController(UserRestService service) {
        this.apiService = service;
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return apiService.getAll();
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto userDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(apiService.addNewUser(userDto));
    }

    @PutMapping
    public ResponseEntity<UserDto> updateUser(@RequestBody @Valid UserDto userDto) {
        return ResponseEntity.ok(apiService.changeUser(userDto, userDto.ID()));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUser(@RequestBody @Valid UserDto userDto) {
        Long id = userDto.ID();
        if (id != null) {
            apiService.delete(id);
        }
        return ResponseEntity.ok("Пользователь ID=" + id + " удалён из базы данных.");
    }
}
