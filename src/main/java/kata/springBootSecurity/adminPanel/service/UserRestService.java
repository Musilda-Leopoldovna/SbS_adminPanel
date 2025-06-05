package kata.springBootSecurity.adminPanel.service;

import kata.springBootSecurity.adminPanel.entity.User;
import kata.springBootSecurity.adminPanel.entity.Role;
import kata.springBootSecurity.adminPanel.entity.UserMapper;
import kata.springBootSecurity.adminPanel.dto.UserDto;
import kata.springBootSecurity.adminPanel.repository.UserRepository;
import kata.springBootSecurity.adminPanel.repository.RoleRepository;

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
    private final UserMapper userMapper;

    public UserRestService(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Transactional(readOnly = true)
    public List<UserDto> getAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserDto getByUsername(String username) {
        return userMapper.toDto(userRepository.findByUsername(username));
    }

    public Set<Role> resolveRolesFromDto(Set<String> roleNames) {
        return roleNames.stream()
                .map(roleRepository::findByName)
                .collect(Collectors.toSet());
    }

    public UserDto addNewUser(UserDto dto) {
        User user = userMapper.toEntity(dto);
        resolveRolesFromDto(dto.roleNames()).forEach(user::setRoles);
        user.setUsername();
        String password = dto.userPassword();
        if (password != null && !password.isEmpty()) {
            user.setPassword(passwordEncoder.encode(password));
        } else {
            user.setPassword("пароль по умолчанию");
        }
        User newUser = userRepository.save(user);
        return userMapper.toDto(newUser);
    }

    public UserDto changeUser(UserDto dto, Long id) {
        User updUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("При попытке изменить данные пользователь не найден"));
        if (dto.userPassword() != null && !dto.userPassword().isEmpty()) {
            updUser.setPassword(passwordEncoder.encode(dto.userPassword()));
        }
        updUser.setFirstName(dto.userName());
        updUser.setUsername();
        updUser.setEmail(dto.userEmail());
        resolveRolesFromDto(dto.roleNames()).forEach(updUser::setRoles);
        User finalUpdUser = userRepository.save(updUser);
        return userMapper.toDto(finalUpdUser);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
