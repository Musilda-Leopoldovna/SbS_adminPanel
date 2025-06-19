package kata.springBootSecurity.adminPanel.additionalConfigs;

import kata.springBootSecurity.adminPanel.mvc.services.UserService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
<<<<<<< HEAD:src/main/java/kata/springBootSecurity/adminPanel/service/MyUserDetailsService.java
import org.springframework.stereotype.Service;
=======
import kata.springBootSecurity.adminPanel.database.entity.User;
import org.springframework.stereotype.Component;
>>>>>>> rest_controllers:src/main/java/kata/springBootSecurity/adminPanel/additionalConfigs/CustomUserDetails.java

@Component
public class CustomUserDetails implements UserDetailsService {

    private final UserService userService;

    public CustomUserDetails(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.getUserByUsername(username);
    }
}
