package ua.lapada.app.university.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Data
@Validated
@ConfigurationProperties("app.datasource")
public class DataSourceProperties {
    @NotBlank
    private String url;
    @NotBlank
    private String password;
    @NotBlank
    private String username;
}
