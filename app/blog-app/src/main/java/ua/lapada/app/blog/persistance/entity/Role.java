package ua.lapada.app.blog.persistance.entity;

import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toMap;

@RequiredArgsConstructor
public enum Role {
    ADMIN(1), USER(2);

    private final Integer id;

    private static final Map<Integer, Role> VALUES = Stream.of(values())
                                                           .collect(collectingAndThen(toMap(Role::id, Function.identity()), Collections::unmodifiableMap));

    public Integer id() {
        return id;
    }

    public static Role byId(final Integer id) {
        return Optional.ofNullable(VALUES.get(id))
                       .orElseThrow(() -> new IllegalArgumentException(format("Role for %d has not presented!", id)));
    }
}
