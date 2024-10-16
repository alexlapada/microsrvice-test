package ua.lapada.core.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.lapada.core.persistance.entity.SlideMetadata;

import java.util.List;

public interface SlideMetadataRepository extends JpaRepository<SlideMetadata, String> {
    List<SlideMetadata> findAllByJobId(String jobId);
}
