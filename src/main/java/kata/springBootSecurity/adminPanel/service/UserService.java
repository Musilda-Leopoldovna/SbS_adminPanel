package kata.springBootSecurity.adminPanel.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kata.springBootSecurity.adminPanel.entity.Role;
import kata.springBootSecurity.adminPanel.repository.RoleRepository;
import kata.springBootSecurity.adminPanel.entity.User;
import kata.springBootSecurity.adminPanel.repository.UserRepository;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public List<User> getListOfUsers() {
        return userRepository.findAll();
    }

    public void addNewUser(User user) {
        user.setUsername();
        String password = user.getPassword();
        if (password != null && !password.isEmpty()) {
            user.setPassword(passwordEncoder.encode(password));
        }
//        List<Role> allRoles = user.getRoles().stream()
//                        .map(role -> roleRepository.findByName(role.getAuthority()))
//                                .collect(Collectors.toList());
//        user.setRoles(allRoles);

        userRepository.save(user);
    }

    public void changeUser(User user) {
        User updUser = userRepository.findById(user.getID())
                .orElseThrow(() -> new IllegalArgumentException("При попытке изменить данные пользователь не найден"));
        String newPassword = user.getPassword();
        if (newPassword != null && !newPassword.isEmpty()) {
            updUser.setPassword(passwordEncoder.encode(newPassword));
        }
        updUser.setFirstName(user.getFirstName());
        updUser.setEmail(user.getEmail());
        userRepository.save(updUser);
    }

    public void removeUserByID(Long ID) {
        userRepository.deleteById(ID);
    }

//    public User getUserByID(Long userId) {
//        return userRepository.findById(userId).orElse(new User());
//    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Set<Role> getRolesByIds(List<Long> ids) {
        return new HashSet<>(roleRepository.findAllById(ids));
    }
    public List<String> getAllRoles(Authentication authentication) {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        return roles.stream().toList();
    }
}
