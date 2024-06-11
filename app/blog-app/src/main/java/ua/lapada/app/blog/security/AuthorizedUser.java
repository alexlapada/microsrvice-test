package ua.lapada.app.blog.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import ua.lapada.app.blog.persistance.entity.Role;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toSet;

@Data
@AllArgsConstructor
public class AuthorizedUser implements UserDetails {
    private String userName;
    private String password;
    private User userDetails;
    private ua.lapada.app.blog.persistance.entity.User user;

    public static AuthorizedUser from(ua.lapada.app.blog.persistance.entity.User user) {
        String userName = user.getName();
        String password = Optional.ofNullable(user.getPassword()).orElse("");
        User userDetails = new User(userName, password, true, true,
                true, true, getAuthorities(user.getRoles()));

        return new AuthorizedUser(userName, password, userDetails, user);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean isActive() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userDetails.getAuthorities();
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        return Optional.ofNullable(roles)
                .orElse(Collections.emptyList())
                .stream()
                .map(Role::name)
                .map(SimpleGrantedAuthority::new)
                .collect(toSet());
    }

    public String getUserId() {
        return user.getId();
    }
}
