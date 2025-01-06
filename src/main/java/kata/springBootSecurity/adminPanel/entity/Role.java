package kata.springBootSecurity.adminPanel.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Entity
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

//    @ManyToMany(mappedBy = "roles")
//    private Collection<User> users;

//    public Role() {
//    }

    @Override
    public String getAuthority() {
        return name;
    }

    public Long getId() {
        return id;
    }
}
