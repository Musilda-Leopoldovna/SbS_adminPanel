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

import jakarta.persistence.EntityNotFoundException;
import java.util.List;

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
    public UserDto getById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));
    }

    public UserDto addNewUser(UserDto dto, Long roleId) {
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new IllegalArgumentException("Роль не найдена"));
        User user = userMapper.toEntity(dto, role);
//        user.setRoles(role);
        if (role.getName().contains("ADMIN")) {
            user.setRoles(roleRepository.findByName("USER"));
        }
        String password = dto.userPassword();
        if (password != null && !password.isEmpty()) {
            user.setPassword(passwordEncoder.encode(password));
        }
        return userMapper.toDto(userRepository.save(user));
    }

    public UserDto changeUser(UserDto dto, Long id, Long roleId) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("При попытке изменить данные пользователь не найден"));
        if (dto.userPassword() != null && !dto.userPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(dto.userPassword()));
        }
        user.setRoles(roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Роль не найдена")));
        return userMapper.toDto(userRepository.save(user));
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
