package ua.lapada.app.blog.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.lapada.app.blog.persistance.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByName(String name);
}
