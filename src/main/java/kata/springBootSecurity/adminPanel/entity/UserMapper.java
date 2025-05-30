package kata.springBootSecurity.adminPanel.entity;

import kata.springBootSecurity.adminPanel.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toDto(User user) {
        return new UserDto(
                user.getID(),
                user.getFirstName(),
                user.getEmail(),
                user.getUsername(),
                null, // не возвращать пароль
                user.getRoles()
        );
    }

    public User toEntity(UserDto dto, Role roleEntities) {
        User user = new User();
        user.setEmail(dto.userEmail());
        user.setFirstName(dto.userName());
        user.setPassword(dto.userPassword());
        user.setRoles(roleEntities);
        return user;
    }
}
