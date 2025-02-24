package ua.lapada.core.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.lapada.core.persistance.entity.TtsJob;

public interface TtsJobRepository extends JpaRepository<TtsJob, String> {
}
