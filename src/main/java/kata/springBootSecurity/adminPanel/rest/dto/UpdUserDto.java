package kata.springBootSecurity.adminPanel.rest.dto;

import jakarta.validation.constraints.Pattern;
import java.util.Set;

public record UpdUserDto(Long ID, String userName,
                         @Pattern(
                                 regexp = "^[\\w._%+-]+@mail\\.com$",
                                 message = "Email должен быть вида user@mail.com"
                         ) String userEmail,
                         String userPassword, Set<String> roleNames) {
}
