package ua.lapada.core.notification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CommandSender {

    public void send(String command) {
        try {
            Thread.sleep(100);
            log.info("Send command {}", command);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
