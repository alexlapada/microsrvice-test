package ua.lapada.app.university.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ua.lapada.core.service.TtsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping(TestController.ENDPOINT)
@AllArgsConstructor
public class TestController {
    static final String ENDPOINT = "/test";

    private final RestTemplate restTemplate;
    private final TtsService ttsService;

    @GetMapping("/event-test/{test}")
    public String redirectTest(@PathVariable String test) throws IOException, URISyntaxException {
        System.out.println();
        String jobId = "a2ec1980-35a8-4c98-b0ea-8b1a7e4a88c4";
        String slideId = "8e89ab87-54e5-4986-935d-d72ce98a59cd";
        ttsService.updateAudio(slideId, jobId, test);
        return "Ok";
    }

    public <T> ResponseEntity<T> exchange(String url, Object requestBody, Class<T> responseClass, HttpHeaders headers, Object... uriParams) {
        final URI uri = getURI(url, uriParams);
        final RequestEntity<?> entity = RequestEntity
                .method(HttpMethod.GET, uri)
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody);
        return restTemplate.exchange(entity, responseClass);
    }

    private URI getURI(final String url, final Object... uriParams) {
        final String host = "http://localhost:2929";
        return restTemplate.getUriTemplateHandler().expand(host + url, uriParams);
    }

}
