package hu.gurubib.ht.elovizsga.task3.server.controllers;

import hu.gurubib.ht.elovizsga.task3.server.modells.LogRequest;
import hu.gurubib.ht.elovizsga.task3.server.modells.LogResponse;
import hu.gurubib.ht.elovizsga.task3.server.services.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class LogController {

    private final Logger logger = LoggerFactory.getLogger(LogController.class);

    private final LogService service;

    @Autowired
    public LogController(LogService service) {
        this.service = service;
    }

    @MessageMapping("/log")
    @SendTo("/topic/results")
    public LogResponse log(final LogRequest request, final SimpMessageHeaderAccessor ha) {
        logger.info("Message received");
        final String clientIp = (String) ha.getSessionAttributes().get("ip");
        final boolean logged = service.log(request.getMessage(), clientIp);

        if (logged) {
            return new LogResponse("Logged successfully for client with ip: " + clientIp);
        } else {

            return new LogResponse("Failed logging for client with ip: " + clientIp);
        }
    }
}
