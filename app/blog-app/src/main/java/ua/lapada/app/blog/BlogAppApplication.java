package ua.lapada.app.blog;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ua.lapada.app.blog.configuration.properties.JwtProperties;

@SpringBootApplication(scanBasePackages = "ua.lapada")
@EnableConfigurationProperties({
        JwtProperties.class
})
@AllArgsConstructor
@EnableTransactionManagement
@EnableRetry
public class BlogAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogAppApplication.class, args);
    }

}
