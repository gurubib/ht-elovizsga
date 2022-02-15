package hu.gurubib.ht.elovizsga.task3.server.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Service
public class LogServiceImpl implements LogService {

    private final Logger logger = LoggerFactory.getLogger(LogServiceImpl.class);

    @Autowired
    ServletContext context;

    @Override
    public boolean log(String message, String ip) {
        try {
            final String userDirectory = System.getProperty("user.dir");
            final Path logPath = Paths.get(
                    userDirectory, "server", "src", "main", "resources", "logs", "requests.log"
            );
            final String log = ip + " - " + message + System.lineSeparator();
            Files.write(logPath, log.getBytes(), StandardOpenOption.APPEND);
            return true;
        } catch (IOException e) {
            logger.error("Error while writing logs!");
            return false;
        }
    }

}
