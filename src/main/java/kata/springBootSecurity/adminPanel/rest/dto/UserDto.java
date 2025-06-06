package kata.springBootSecurity.adminPanel.rest.dto;

import java.util.Set;

public record UserDto(Long ID, String userName,
                      String userEmail, String userLogin,
                      Set<String> roleNames) {
}
