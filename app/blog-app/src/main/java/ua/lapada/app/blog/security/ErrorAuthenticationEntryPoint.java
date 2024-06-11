package ua.lapada.app.blog.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import ua.lapada.app.blog.web.dto.error.ErrorResponseDto;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class ErrorAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static final String BEARER = "Bearer";
    private final ObjectMapper mapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.addHeader(HttpHeaders.WWW_AUTHENTICATE, BEARER);
        Optional.ofNullable(request.getAttribute(RequestDispatcher.ERROR_EXCEPTION))
                .map(Throwable.class::cast)
                .ifPresent(throwable -> writeAsJsonBody(response, throwable));
    }

    private void writeAsJsonBody(final HttpServletResponse response, final Throwable throwable) {
        response.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
        try {
            log.warn(throwable.getMessage());
            mapper.writeValue(response.getWriter(), ErrorResponseDto.of("Authentication error", throwable.getMessage()));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
