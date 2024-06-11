package ua.lapada.app.blog.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ua.lapada.app.blog.security.JwtAuthenticationFilter;
import ua.lapada.app.blog.security.JwtTokenUtil;
import ua.lapada.app.blog.service.ArticleService;
import ua.lapada.app.blog.service.UserService;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
@MockBeans({
        @MockBean(ArticleService.class),
        @MockBean(JwtAuthenticationFilter.class),
        @MockBean(AuthenticationManager.class),
        @MockBean(JwtTokenUtil.class),
        @MockBean(UserService.class)
})
@ComponentScan({"ua.lapada.app.blog.mapper"})
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public abstract class AbstractControllerTest {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;

    public static final String PAGE_NUMBER_PARAM_NAME = "pageNumber";
    public static final String PAGE_SIZE_PARAM_NAME = "pageSize";
}
