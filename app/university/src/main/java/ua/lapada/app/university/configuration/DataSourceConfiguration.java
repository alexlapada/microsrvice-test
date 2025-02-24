package ua.lapada.app.university.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.lapada.app.university.configuration.properties.DataSourceProperties;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration {

    @Bean
    DataSource dataSource(DataSourceProperties properties) {
        HikariConfig configuration = new HikariConfig();
        configuration.setJdbcUrl(properties.getUrl());
        configuration.setUsername(properties.getUsername());
        configuration.setPassword(properties.getPassword());
        configuration.setMaximumPoolSize(3);
        return new HikariDataSource(configuration);
    }
}
