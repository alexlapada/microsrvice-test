package ua.lapada.core.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.StaleObjectStateException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetrySynchronizationManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.lapada.core.exception.RetryException;
import ua.lapada.core.persistance.repository.TtsJobRepository;

import java.util.concurrent.ThreadLocalRandom;

@Service
@AllArgsConstructor
@Slf4j
public class TtsService {
    private final JobHandler jobHandler;
    private final TtsJobRepository ttsJobRepository;

    @Retryable(value = {ObjectOptimisticLockingFailureException.class}, backoff = @Backoff(500), maxAttempts = 5)
    public void updateAudio(String slideMetadataId, String jobId, String serverName) {
        ttsJobRepository.findById(jobId)
                        .ifPresent(ttsJob -> processAudio(slideMetadataId, ttsJob.getId(), serverName));
    }

    //    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void processAudio(String slideMetadataId, String ttsJobId, String serverName) {
//        sleep(1000);
        try {
            log.info("{}, Processing audio", serverName);
            jobHandler.updateMediaDuration(slideMetadataId, getDurationFromMetadata(slideMetadataId));
//            sleep(5500);
            log.info("{} Verification whether Job is Converted", slideMetadataId);
            if (jobHandler.isJobConverted(ttsJobId)) {
                log.info("{}, All related audio of {} job have been converted. slide {}", serverName, ttsJobId, slideMetadataId);
                jobHandler.handleConvertedJob(ttsJobId, slideMetadataId);
            }
            log.info("{}, Processing audio done", serverName);
        } catch (ObjectOptimisticLockingFailureException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (Exception e) {
            log.error("{}", e.getMessage(), e);
        }
    }

    long getDurationFromMetadata(String slideMetadataId) {
        long duration = 0;
        try {
            Thread.sleep(100);
            duration = ThreadLocalRandom.current().nextLong(1, 100);
        } catch (Exception e) {
            log.error("Can't get duration for file ID {}", slideMetadataId);
        }
        return duration;
    }

}
