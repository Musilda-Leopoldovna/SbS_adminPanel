package kata.springBootSecurity.adminPanel.rest.controllers;


import kata.springBootSecurity.adminPanel.rest.dto.NewUserDto;
import kata.springBootSecurity.adminPanel.rest.dto.UpdUserDto;
import kata.springBootSecurity.adminPanel.rest.dto.UserDto;
import kata.springBootSecurity.adminPanel.rest.services.UserRestService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
        return apiService.getAllUsers();
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid NewUserDto userDto) {
        return ResponseEntity.ok(apiService.addNewUser(userDto));
    }

    @PatchMapping
    public ResponseEntity<UserDto> updateUser(@RequestBody @Valid UpdUserDto userDto) {
        return ResponseEntity.ok(apiService.changeUser(userDto, userDto.ID()));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUser(@RequestBody @Valid UserDto userDto) {
        apiService.delete(userDto.ID());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
