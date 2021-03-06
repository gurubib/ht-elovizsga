package hu.gurubib.ht.elovizsga.task3.client.handlers;

import hu.gurubib.ht.elovizsga.task3.client.models.LogResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;

public class MyStompSessionHandler extends StompSessionHandlerAdapter {

    private final Logger logger = LoggerFactory.getLogger(MyStompSessionHandler.class);

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        logger.info("New session established : " + session.getSessionId());
        session.subscribe("/topic/results", this);
        logger.info("Subscribed to /topic/results");
        logger.info("Type in message:");
    }

    @Override
    public void handleException(
            StompSession session,
            StompCommand command,
            StompHeaders headers,
            byte[] payload,
            Throwable exception
    ) {
        logger.error("Got an exception", exception);
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return LogResponse.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        final LogResponse response = (LogResponse) payload;
        logger.info("Received: " + response.getResult());
    }
}