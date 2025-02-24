package ua.lapada.core.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.lapada.core.persistance.entity.Job;

import java.util.Optional;

public interface JobRepository extends JpaRepository<Job, String> {
    Optional<Job> findByTtsJobId(String ttsJobId);
}
