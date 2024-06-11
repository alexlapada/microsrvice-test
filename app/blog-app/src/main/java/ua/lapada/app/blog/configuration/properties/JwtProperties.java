package ua.lapada.app.blog.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Validated
@ConfigurationProperties("app.jwt")
public class JwtProperties {
    @NotBlank
    private String secret;
    @NotNull
    private Long validityTime;
}
