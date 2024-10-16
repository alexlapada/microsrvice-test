package ua.lapada.app.blog.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import ua.lapada.app.blog.configuration.properties.S3Properties;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

@Configuration
@Slf4j
public class S3Configuration {

    @Bean
    S3Client s3client() throws URISyntaxException {
        return S3Client.builder()
                       .endpointOverride(new URI(S3Properties.endpoint))
                       .region(Region.of(S3Properties.region))
                       .httpClientBuilder(ApacheHttpClient.builder()
                                                          .connectionTimeout(Duration.ofSeconds(2))
                                                          .maxConnections(100))
                       .credentialsProvider(
                               () -> AwsBasicCredentials.create(S3Properties.accessKey, S3Properties.secretKey))
                       .build();
    }
}
