package kata.springBootSecurity.adminPanel.rest.services;

import kata.springBootSecurity.adminPanel.database.entity.User;
import kata.springBootSecurity.adminPanel.database.entity.Role;
import kata.springBootSecurity.adminPanel.rest.dto.NewUserDto;
import kata.springBootSecurity.adminPanel.rest.dto.UpdUserDto;
import kata.springBootSecurity.adminPanel.rest.dto.UserToDtoMapper;
import kata.springBootSecurity.adminPanel.rest.dto.UserDto;
import kata.springBootSecurity.adminPanel.database.repository.UserRepository;
import kata.springBootSecurity.adminPanel.database.repository.RoleRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserRestService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserToDtoMapper userToDtoMapper;

    public UserRestService(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           UserToDtoMapper userToDtoMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userToDtoMapper = userToDtoMapper;
    }

    private Set<Role> getRolesFromDto(Set<String> roleNames) {
        return roleNames.stream()
                .map(roleRepository::findByName)
                .collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userToDtoMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserDto getByUsername(String username) {
        return userToDtoMapper.toDto(userRepository.findByUsername(username));
    }

    public UserDto addNewUser(NewUserDto dto) {

        User user = new User();
        user.setFirstName(dto.userName());
        user.setEmail(dto.userEmail());
        // login-password
        user.setUsername(dto.userName());
        String password = dto.userPassword();
        if (!password.isEmpty()) {
            user.setPassword(passwordEncoder.encode(password));
        }
        // set roles
        getRolesFromDto(dto.roleNames()).forEach(user::setRoles);
        userRepository.save(user);
        return userToDtoMapper.toDto(user);
    }

    public UserDto changeUser(UpdUserDto dto, Long id) {

        User updUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("При попытке изменить данные пользователь не найден"));

        String firstName = dto.userName();
        if (firstName != null && !firstName.isEmpty()) {
            updUser.setFirstName(firstName);
        }

        String email = dto.userEmail();
        if (email != null && !email.isEmpty()) {
            updUser.setEmail(email);
        }

        String password = dto.userPassword();
        if (password != null && !password.isEmpty()) {
            updUser.setPassword(passwordEncoder.encode(password));
        }

        Set<String> roleNames = dto.roleNames();
        if (roleNames != null) {
            getRolesFromDto(roleNames).forEach(updUser::setRoles);
        }
        return userToDtoMapper.toDto(updUser);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
