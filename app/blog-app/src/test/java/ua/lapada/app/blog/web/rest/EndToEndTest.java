package ua.lapada.app.blog.web.rest;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.retry.listener.RetryListenerSupport;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import ua.lapada.app.blog.BlogAppApplication;
import ua.lapada.app.blog.persistance.entity.Article;
import ua.lapada.core.notification.CommandSender;
import ua.lapada.core.persistance.entity.Job;
import ua.lapada.core.persistance.entity.SlideMetadata;
import ua.lapada.core.persistance.repository.JobRepository;
import ua.lapada.core.persistance.repository.SlideMetadataRepository;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = BlogAppApplication.class)
@AutoConfigureMockMvc
@Slf4j
public class EndToEndTest {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    private SlideMetadataRepository slideMetadataRepository;
    @Autowired
    private JobRepository jobRepository;

    @SpyBean
    private CommandSender commandSender;

    @Captor
    private ArgumentCaptor<String> eventCaptor;

    private static final String jobId = "e2ec1980-35a8-4c88-b0ea-8b1a7e4a88c4";
    private static final String slideId1 = "e3890777-8c05-45d7-b696-64abb289bd53";
    private static final String slideId2 = "8e89ab87-54e5-4986-935d-d72ce98a59cd";
    private static final String slideId3 = "e3890777-8c05-45d7-b696-64abb289bd63";
    private static final String slideId4 = "e3890777-8c05-45d7-b696-64abb289bd73";

    @BeforeEach
    void setUp() {
        Job job = jobRepository.findById(jobId).orElseThrow();
        job.setStatus("TEST");
        jobRepository.save(job);
        slideMetadataRepository.findAllByJobId(jobId).forEach(slide -> {
            slide.setDuration(0L);
            slideMetadataRepository.save(slide);
        });
    }

    @Test
    public void testConcurrent() throws Exception {
        Job job = jobRepository.findById(jobId).orElseThrow();
        assertThat(job).isNotNull().hasFieldOrPropertyWithValue(Job.Fields.status, "TEST");
        SlideMetadata slide1 = slideMetadataRepository.findById(slideId1).orElseThrow();
        SlideMetadata slide2 = slideMetadataRepository.findById(slideId2).orElseThrow();
        SlideMetadata slide3 = slideMetadataRepository.findById(slideId3).orElseThrow();
        SlideMetadata slide4 = slideMetadataRepository.findById(slideId4).orElseThrow();
        assertThat(slide1).isNotNull().hasFieldOrPropertyWithValue(SlideMetadata.Fields.duration, 0L);
        assertThat(slide2).isNotNull().hasFieldOrPropertyWithValue(SlideMetadata.Fields.duration, 0L);
        assertThat(slide3).isNotNull().hasFieldOrPropertyWithValue(SlideMetadata.Fields.duration, 0L);
        assertThat(slide4).isNotNull().hasFieldOrPropertyWithValue(SlideMetadata.Fields.duration, 0L);

        Thread thread1 = restCall(slideId1);
        Thread thread2 = restCall(slideId2);
        Thread thread3 = restCall(slideId3);
        Thread thread4 = restCall(slideId4);

        try {
            thread1.start();
            thread2.start();
            thread3.start();
            thread4.start();

            thread1.join();
            thread2.join();
            thread4.join();
            thread3.join();
        } catch (Exception e) {
            System.out.println();
        }

        verify(commandSender, times(2)).send(eventCaptor.capture());
        List<String> commands = eventCaptor.getAllValues();
        System.out.println();
    }

    private Thread restCall(String slideId) {
        return new Thread(() -> {
            ResultActions resultActions = null;
            try {
                resultActions = mockMvc.perform(get(ArticleController.ENDPOINT + "/event-test/{test}", slideId));
                resultActions.andExpect(status().isOk());
            } catch (Exception e) {
                System.err.println("For slide " + slideId + " we have error " + e.getMessage());
            }
        });

    }

}
