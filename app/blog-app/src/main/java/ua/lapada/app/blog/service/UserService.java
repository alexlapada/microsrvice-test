package ua.lapada.app.blog.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.lapada.app.blog.persistance.entity.User;
import ua.lapada.app.blog.persistance.repository.UserRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository repository;

    @Transactional(readOnly = true)
    public Optional<User> getByNameOptional(String name) {
        return repository.findByName(name);
    }
}
