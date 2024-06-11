package ua.lapada.app.blog.web.dto.security;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@SuperBuilder
@NoArgsConstructor
public class CredentialsDto {
    @NotBlank
    @Email
    private String username;
    @NotBlank
    private String password;
}
