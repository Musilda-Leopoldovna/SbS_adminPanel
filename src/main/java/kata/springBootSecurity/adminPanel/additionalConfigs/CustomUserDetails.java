package kata.springBootSecurity.adminPanel.additionalConfigs;

import kata.springBootSecurity.adminPanel.mvc.services.UserService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import kata.springBootSecurity.adminPanel.database.entity.User;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetails implements UserDetailsService {

    private final UserService userService;

    public CustomUserDetails(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userService.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Пользователь '" + username + "' не найден");
        }
        return user;
    }
}
