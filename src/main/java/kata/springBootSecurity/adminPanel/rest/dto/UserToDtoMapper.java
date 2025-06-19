package kata.springBootSecurity.adminPanel.rest.dto;

import kata.springBootSecurity.adminPanel.database.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserToDtoMapper {

    public UserDto toDto(User user) {
        return new UserDto(
                user.getID(),
                user.getFirstName(),
                user.getEmail(),
                user.getUsername(),
                user.getRoles()
        );
    }
}
