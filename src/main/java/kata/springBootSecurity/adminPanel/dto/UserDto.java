package kata.springBootSecurity.adminPanel.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record UserDto(Long ID, @NotBlank(message = "Имя не может быть пустым") String userName,
                      @Email(message = "Некорректный email") @NotBlank(message = "Адрес не может быть пустым") String userEmail,
                      String userLogin, String userPassword, Set<String> roleNames) {
}
