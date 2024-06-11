package ua.lapada.app.blog.web.dto.security;

import lombok.Getter;

public class JwtResponse {
    @Getter
    private final String token;

    public JwtResponse(String token) {
        this.token = token;
    }
}
