package ee.taltech.iti0203backend.security;

import ee.taltech.iti0203backend.utils.Role;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class SessionUser extends User {

    private Long id;
    private Role role;

    public SessionUser(String username, String password, Collection<? extends GrantedAuthority> authorities,
                       Long id, Role role) {
        super(username, password, authorities);
        this.id = id;
        this.role = role;
    }

    public SessionUser(String username, String password, boolean enabled, boolean accountNonExpired,
                       boolean credentialsNonExpired,
                       boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }
}
