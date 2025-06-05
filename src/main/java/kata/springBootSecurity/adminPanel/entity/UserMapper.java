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
                null,
                user.getRoles()
        );
    }

    public User toEntity(UserDto dto) {
        User user = new User();
        user.setFirstName(dto.userName());
        user.setEmail(dto.userEmail());
        user.setPassword(dto.userPassword());
        return user;
    }
}
