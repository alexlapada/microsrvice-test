package ua.lapada.app.blog.web.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ua.lapada.app.blog.web.dto.error.ErrorResponseDto;

import javax.persistence.EntityNotFoundException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
@Slf4j
public class ErrorHandler {
    static final String ENTITY_WAS_NOT_FOUND_MSG = "Entity was not found";
    static final String LOG_EXCEPTION_MSG_FORMAT = "{} has occurred: {}";

    @ResponseBody
    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ErrorResponseDto handleEntityNotFoundException(final EntityNotFoundException exception) {
        log.info(LOG_EXCEPTION_MSG_FORMAT, exception.getClass().getName(), exception.getMessage());
        return ErrorResponseDto.of(ENTITY_WAS_NOT_FOUND_MSG, exception.getMessage());
    }
}
