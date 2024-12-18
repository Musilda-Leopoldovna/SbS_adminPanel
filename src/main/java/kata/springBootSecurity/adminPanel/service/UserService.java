package ru.kata.spring.boot_security.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.entity.User;
import ru.kata.spring.boot_security.repository.UserRepository;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<User> getListOfUsers() {
        return userRepository.findAll();
    }

    public void addNewUser(User user) {
        userRepository.save(user);
    }

    public void removeUserByID(Long ID) {
        userRepository.deleteById(ID);
    }

    public void changeUser(User updUser) {
        userRepository.save(updUser);
    }

    public User getUserByID(Long userId) {
        return userRepository.findById(userId).orElse(new User());
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}

