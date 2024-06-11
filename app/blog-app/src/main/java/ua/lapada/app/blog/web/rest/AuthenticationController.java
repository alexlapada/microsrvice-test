package ua.lapada.app.blog.web.rest;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.lapada.app.blog.security.AuthorizedUser;
import ua.lapada.app.blog.security.JwtTokenUtil;
import ua.lapada.app.blog.service.UserService;
import ua.lapada.app.blog.web.dto.security.CredentialsDto;
import ua.lapada.app.blog.web.dto.security.JwtResponse;

@RestController
@RequestMapping(value = AuthenticationController.ENDPOINT)
@AllArgsConstructor
@Slf4j
public class AuthenticationController {
    public static final String ENDPOINT = "/authentication";
    public static final String LOGIN_ENDPOINT = "/login";

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

    @PostMapping(value = LOGIN_ENDPOINT)
    public JwtResponse login(@RequestBody CredentialsDto credentials) throws Exception {
        String name = credentials.getUsername();
        authenticate(name, credentials.getPassword());
        AuthorizedUser authorizedUser = userService.getByNameOptional(name).map(AuthorizedUser::from)
                                                    .orElseThrow(() -> new UsernameNotFoundException(String.format("User not found with email:%s", name)));
        String token = jwtTokenUtil.generateToken(authorizedUser);

        return new JwtResponse(token);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
