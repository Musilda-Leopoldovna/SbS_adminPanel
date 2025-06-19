package kata.springBootSecurity.adminPanel.mvc.services;

import java.util.Collection;
import java.util.List;

import kata.springBootSecurity.adminPanel.configs.CryptConfig;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kata.springBootSecurity.adminPanel.database.entity.Role;
import kata.springBootSecurity.adminPanel.database.repository.RoleRepository;
import kata.springBootSecurity.adminPanel.database.entity.User;
import kata.springBootSecurity.adminPanel.database.repository.UserRepository;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CryptConfig cryptConfig;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, CryptConfig cryptConfig) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.cryptConfig = cryptConfig;
    }

    @Transactional(readOnly = true)
    public List<User> getListOfUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Collection<Role> getListOfRoles() {
        return roleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь " + username + " не найден"));
    }

    public void removeUserByID(Long ID) {
        userRepository.deleteById(ID);
    }

    public void addNewUser(User user, Long roleIds) {
        user.setUsername(user.getFirstName());
        String password = user.getPassword();
        if (password != null && !password.isEmpty()) {
            user.setPassword(cryptConfig.passwordEncoder().encode(password));
        }
        Role role = roleRepository.findById(roleIds).orElseThrow(() -> new IllegalArgumentException("Роль не найдена"));
        user.setRoles(role);
        if (role.getName().contains("ADMIN")) {
            user.setRoles(roleRepository.findByName("USER"));
        }
        userRepository.save(user);
    }

    public void changeUser(User user, Long updId, Long updRole) {
        User updUser = userRepository.findById(updId)
                .orElseThrow(() -> new IllegalArgumentException("При попытке изменить данные пользователь не найден"));
        String newPassword = user.getPassword();
        if (newPassword != null && !newPassword.isEmpty()) {
            updUser.setPassword(cryptConfig.passwordEncoder().encode(newPassword));
        }
        String newFirstName = user.getFirstName();
        if (newFirstName != null && !newFirstName.isEmpty()) {
            updUser.setFirstName(newFirstName);
        }
        String newEmail = user.getEmail();
        if (newEmail != null && !newEmail.isEmpty()) {
            updUser.setEmail(newEmail);
        }
        if (updRole != null) {
            Role addRole = roleRepository.findById(updRole).orElseThrow(() -> new IllegalArgumentException("Роль не найдена"));
<<<<<<< HEAD:src/main/java/kata/springBootSecurity/adminPanel/service/UserService.java
            if (!updUser.getAuthorities().contains(addRole)) {
                updUser.setRoles(addRole);
            } else {
=======
            if (!updUser.getRoles().contains("USER")) {
                updUser.setRoles(addRole);
            }else {
>>>>>>> rest_controllers:src/main/java/kata/springBootSecurity/adminPanel/mvc/services/UserService.java
                updUser.getAuthorities().removeIf(role -> role.equals(addRole));
            }
        }
    }
}
