package ua.lapada.core.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.lapada.core.notification.CommandSender;
import ua.lapada.core.persistance.entity.SlideMetadata;
import ua.lapada.core.persistance.entity.Job;
import ua.lapada.core.persistance.repository.SlideMetadataRepository;
import ua.lapada.core.persistance.repository.JobRepository;
import ua.lapada.core.persistance.repository.SlideMetadataRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@AllArgsConstructor
@Slf4j
@Transactional(isolation = Isolation.READ_COMMITTED)
public class JobHandler {
    private final SlideMetadataRepository slideMetadataRepository;
    private final JobRepository jobRepository;
    private final CommandSender commandSender;

//    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void updateStatus(String jobId, String jobStatus) {
        try {
            jobRepository.findById(jobId).ifPresent(slidesPresentationJob -> {
                log.info("Updating status for job {}", jobId);
                slidesPresentationJob.setStatus(jobStatus);
                jobRepository.save(slidesPresentationJob);
                log.info("Status updated for job {}", jobId);
            });
        } catch (Exception e) {
            log.error("Error during updating status for job {}. Reason {}", jobId, e.getMessage(), e);
        }
    }

//    @Transactional(isolation = Isolation.READ_COMMITTED)
    public boolean isJobConverted(String ttsJobId) {
        boolean converted = jobRepository
                .findByTtsJobId(ttsJobId)
                .map(Job::isConverted)
                .orElse(false);
        log.info("Converted {}", converted);
        return converted;
    }

//    @Transactional(isolation = Isolation.READ_COMMITTED)
    public boolean updateDurationAndCheckResult(String jobId, String slideId, long duration) {
        try {
            Thread.sleep(500);
            updateMediaDuration(slideId, duration);
            Thread.sleep(500);
            return isJobConverted(jobId);
        } catch (Exception e) {
            log.error("{}", e.getMessage(), e);
            return false;
        }

    }

//    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void updateMediaDuration(String slideId, long duration) {
        log.info("Updating duration");
        slideMetadataRepository.findById(slideId).ifPresent(sm -> {
            sm.setDuration(duration);
            slideMetadataRepository.save(sm);
        });
        log.info("Duration updated");
    }

//    @Transactional
//    public void handle(String jobId, String slideId) {
//        updateMediaDuration(slideId, getDurationFromMetadata(slideId));
//        try {
//            Thread.sleep(1500);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        log.info("Verification whether Job is Converted");
//        if (isJobConverted(jobId)) {
//            log.info("All related audio of {} job have been converted", jobId);
//            handleConvertedJob(jobId);
//        }
//    }

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

//
    public void handleConvertedJob(String ttsJobId, String slideMetadataId) {
        jobRepository.findByTtsJobId(ttsJobId).ifPresent(slidesPresentationJob -> {
            if (slidesPresentationJob.getStatus().equals("CONVERSION_SUCCEEDED")) {
                log.warn("Job already processed. slide {}", slideMetadataId);
                return;
            }
            log.info("Updating status for job {} by slide {}. existing status {}", ttsJobId, slideMetadataId, slidesPresentationJob.getStatus());
            slidesPresentationJob.setStatus("CONVERSION_SUCCEEDED");
            jobRepository.saveAndFlush(slidesPresentationJob);
            log.info("Status updated for job {} slide {}", ttsJobId, slideMetadataId);

            List<String> commands = new ArrayList<>();
            commands.add("SavePresentationEditingV1");
            commands.add("SavePresentation");

            commands.forEach(commandSender::send);
        });
    }
}
