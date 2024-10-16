package ua.lapada.app.blog.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ChecksumMode;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import ua.lapada.app.blog.configuration.properties.S3Properties;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

@AllArgsConstructor
@Slf4j
@Service
public class S3TestService {
    private final S3Client s3client;


    public String getPresignedUrl(String key) {
        log.info("Getting presigned URL for key {}", key);
        try (S3Presigner presigner = getS3Presigner()) {
            GetObjectRequest.Builder requestBuilder = GetObjectRequest.builder()
                                                                      .bucket(S3Properties.bucket)
                                                                      .key(key)
                                                                      .responseContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
//                                                                      .checksumMode(ChecksumMode.ENABLED);
            log.info("Getting presigned URL. Content Range: [{}]", "0-");
//            requestBuilder.range("0-");

            GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest.builder()
                                                                                     .signatureDuration(Duration.ofDays(1))
                                                                                     .getObjectRequest(requestBuilder.build())
                                                                                     .build();
            PresignedGetObjectRequest presignedGetObjectRequest = presigner.presignGetObject(getObjectPresignRequest);
            String url = presignedGetObjectRequest.url().toString();

            log.info("Returning S3 presigned URL with: fileId [{}]. URL {}", key, url);

            return url;
        } catch (NoSuchKeyException e) {
            log.warn("Object with key [{}] is not found in S3 file storage.", key, e);
            throw new RuntimeException(e.getMessage());
        } catch (S3Exception e) {
            log.warn("Failed to get S3 object [{}]", key, e);
            throw new RuntimeException(e.getMessage());
        }
    }

    private S3Presigner getS3Presigner() {
        try {
            return S3Presigner.builder()
                              .credentialsProvider(
                                      () -> AwsBasicCredentials.create(S3Properties.accessKey, S3Properties.secretKey))
                              .endpointOverride(new URI(S3Properties.endpoint))
                              .region(Region.of(S3Properties.region))
                              .build();
        } catch (URISyntaxException e) {
            log.error("S3Presigner creating error. {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
