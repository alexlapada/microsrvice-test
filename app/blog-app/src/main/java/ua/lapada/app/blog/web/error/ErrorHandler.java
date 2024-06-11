package ua.lapada.app.blog.web.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;
import ua.lapada.app.blog.web.dto.error.ErrorResponseDto;

import javax.persistence.EntityNotFoundException;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ControllerAdvice
@Slf4j
public class ErrorHandler extends DefaultHandlerExceptionResolver {
    static final String ENTITY_WAS_NOT_FOUND_MSG = "Entity was not found";
    static final String LOG_EXCEPTION_MSG_FORMAT = "{} has occurred: {}";
    static final String ACCESS_DENIED_MSG = "Access denied!";
    static final String BAD_CREDENTIAL_MSG = "Bad credentials";

    @ResponseBody
    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ErrorResponseDto handleEntityNotFoundException(final EntityNotFoundException exception) {
        log.info(LOG_EXCEPTION_MSG_FORMAT, exception.getClass().getName(), exception.getMessage());
        return ErrorResponseDto.of(ENTITY_WAS_NOT_FOUND_MSG, exception.getMessage());
    }

    @ResponseBody
    @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    public ErrorResponseDto handleEntityNotFoundException(final BadCredentialsException exception) {
        log.info(LOG_EXCEPTION_MSG_FORMAT, exception.getClass().getName(), exception.getMessage());
        return ErrorResponseDto.of(BAD_CREDENTIAL_MSG, exception.getMessage());
    }
    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ErrorResponseDto handleAccessDeniedException(final AccessDeniedException exception) {
        log.info(LOG_EXCEPTION_MSG_FORMAT, exception, exception.getMessage());
        return ErrorResponseDto.of(ACCESS_DENIED_MSG, exception.getMessage());
    }
}
