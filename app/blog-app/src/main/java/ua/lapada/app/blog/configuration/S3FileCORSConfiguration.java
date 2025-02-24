//package ua.lapada.app.blog.configuration;
//
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import software.amazon.awssdk.regions.Region;
//import software.amazon.awssdk.services.s3.S3Client;
//import software.amazon.awssdk.services.s3.model.CORSConfiguration;
//import software.amazon.awssdk.services.s3.model.CORSRule;
//import software.amazon.awssdk.services.s3.model.GetBucketCorsRequest;
//import software.amazon.awssdk.services.s3.model.GetBucketCorsResponse;
//import software.amazon.awssdk.services.s3.model.PutBucketCorsRequest;
//import ua.lapada.app.blog.configuration.properties.S3Properties;
//
//import javax.annotation.PostConstruct;
//import java.util.ArrayList;
//import java.util.List;
//
//@Slf4j
//@AllArgsConstructor
//@Component
//public class S3FileCORSConfiguration {
//    private final S3Client s3;
//
//    @PostConstruct
//    void setUp() {
//        log.info("********* ADD CORS To S3");
//        getBucketCorsInformation();
//        addCors();
//        log.info("********* ADDED CORS To S3");
//    }
//
//    private void addCors() {
//        Region region = Region.of(S3Properties.region);
//        String bucketName = S3Properties.bucket;
//        List<String> allowMethods = new ArrayList<>();
//        allowMethods.add("GET");
//        allowMethods.add("PUT");
//        allowMethods.add("POST");
//        allowMethods.add("HEAD");
//
//        List<String> allowOrigins = new ArrayList<>();
//        allowOrigins.add("*.slidepresenter.de");
//        try {
//            // Define CORS rules.
//            CORSRule corsRule = CORSRule.builder()
//                                        .allowedMethods(allowMethods)
//                                        .allowedOrigins(allowOrigins)
//                                        .allowedHeaders(List.of("*"))
//                    .build();
//
//            List<CORSRule> corsRules = new ArrayList<>();
//            corsRules.add(corsRule);
//            CORSConfiguration configuration =
//                    CORSConfiguration.builder().corsRules(corsRules).build();
//
//            PutBucketCorsRequest putBucketCorsRequest = PutBucketCorsRequest.builder()
//                    .bucket(bucketName)
//                    .corsConfiguration(configuration)
//                    .build();
//
//            s3.putBucketCors(putBucketCorsRequest);
//        } catch (Exception e) {
//            // Amazon S3 couldn't be contacted for a response, or the client
//            // couldn't parse the response from Amazon S3.
//            log.error("Configuration CORS Error {}", e.getMessage(), e);
//        }
//    }
//
//    public void getBucketCorsInformation() {
//        try {
//            Region region = Region.of(S3Properties.region);
//            String bucketName = S3Properties.bucket;
//            GetBucketCorsRequest bucketCorsRequest = GetBucketCorsRequest.builder()
//                    .bucket(bucketName)
//                    //
//                    // .expectedBucketOwner(accountId)
//                    .build();
//
//            GetBucketCorsResponse corsResponse = s3.getBucketCors(bucketCorsRequest);
//            List<CORSRule> corsRules = corsResponse.corsRules();
//            for (CORSRule rule : corsRules) {
//                log.info("allowOrigins: " + rule.allowedOrigins());
//                log.info("AllowedMethod: " + rule.allowedMethods());
//            }
//
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//        }
//    }
//}
