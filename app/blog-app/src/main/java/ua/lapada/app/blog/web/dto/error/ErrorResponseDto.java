package ua.lapada.app.blog.web.dto.error;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponseDto {
    private final String message;
    private final String description;

    public static ErrorResponseDto of(final String message, final String description) {
        return new ErrorResponseDto(message, description);
    }
}
