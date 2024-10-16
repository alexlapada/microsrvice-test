package ua.lapada.app.university;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ua.lapada.app.university.configuration.properties.DataSourceProperties;
import ua.lapada.app.university.persistance.TestRepository;

import javax.annotation.PostConstruct;

@SpringBootApplication(scanBasePackages = "ua.lapada")
@EnableConfigurationProperties({
        DataSourceProperties.class
})
@EnableTransactionManagement
@EnableRetry
public class UniversityApplication {
    @Autowired
    private TestRepository repository;
//    @PostConstruct
//    void test() throws InterruptedException {
//        long start = System.currentTimeMillis();
//        Thread thread1 = new Thread(() -> {
//            repository.test();
//        });
//        Thread thread2 = new Thread(() -> {
//            repository.test();
//        });
//        Thread thread3 = new Thread(() -> {
//            repository.test();
//        });
//        Thread thread4 = new Thread(() -> {
//            repository.test();
//        });
//        thread1.start();
//        thread2.start();
//        thread3.start();
//        thread4.start();
//        thread1.join();
//        thread2.join();
//        thread3.join();
//        thread4.join();
//        long result = System.currentTimeMillis() - start;
//        System.out.println("Duration " + result);
//    }
    public static void main(String[] args) {
        SpringApplication.run(UniversityApplication.class, args);
    }
}
