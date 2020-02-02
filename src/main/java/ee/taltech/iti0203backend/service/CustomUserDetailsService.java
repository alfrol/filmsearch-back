package ee.taltech.iti0203backend.service;

import ee.taltech.iti0203backend.dao.UserRepository;
import ee.taltech.iti0203backend.model.User;
import ee.taltech.iti0203backend.security.SessionUser;
import ee.taltech.iti0203backend.utils.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> users = repository.findByUsername(username);
        if (username == null || username.isBlank() || users.size() == 0) {
            throw new UsernameNotFoundException(String.format("Username %s not found", username));
        }

        User user = users.get(0);
        return new SessionUser(
                user.getUsername(), user.getPassword(), getUserAuthorities(user), user.getId(), user.getRole()
        );
    }

    private List<SimpleGrantedAuthority> getUserAuthorities(User user) {
        Stream<Role> roles;

        if (user.getRole().isAdmin()) {
            roles = Arrays.stream(Role.values());
        } else {
            roles = Stream.of(user.getRole());
        }

        return roles.map(Role::toSpringRole)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
