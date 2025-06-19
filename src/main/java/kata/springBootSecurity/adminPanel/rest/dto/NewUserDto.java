package kata.springBootSecurity.adminPanel.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.Set;

public record NewUserDto(@NotBlank(message = "Имя не может быть пустым") String userName,
                         @Pattern(
                                 regexp = "^[\\w._%+-]+@mail\\.com$",
                                 message = "Email должен быть вида user@mail.com"
                         )@NotBlank(message = "Адрес не может быть пустым") String userEmail,
                         @NotBlank(message = "Задайте пароль") String userPassword, Set<String> roleNames) {
}
