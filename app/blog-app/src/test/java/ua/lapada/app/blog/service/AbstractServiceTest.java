package ua.lapada.app.blog.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ua.lapada.app.blog.BlogAppApplication;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {BlogAppApplication.class})
@MockBeans({
})
public abstract class AbstractServiceTest {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    protected static final class Dmls {
        public static final String ADD_ARTICLES = "classpath:sql/addArticles.sql";
    }
}
