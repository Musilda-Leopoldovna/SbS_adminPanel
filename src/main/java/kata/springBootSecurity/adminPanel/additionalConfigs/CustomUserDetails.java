package kata.springBootSecurity.adminPanel.additionalConfigs;

import kata.springBootSecurity.adminPanel.database.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetails implements UserDetailsService {

    private final UserRepository user;

    public CustomUserDetails(UserRepository userRepository) {
        this.user = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return user.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
