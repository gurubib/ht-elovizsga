package hu.gurubib.ht.elovizsga.task3.client;

import hu.gurubib.ht.elovizsga.task3.client.handlers.MyStompSessionHandler;
import hu.gurubib.ht.elovizsga.task3.client.models.LogRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;
import org.springframework.web.socket.sockjs.frame.Jackson2SockJsMessageCodec;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class ClientApplication {

	private final static Logger logger = LoggerFactory.getLogger(ClientApplication.class);

	private final static WebSocketHttpHeaders headers = new WebSocketHttpHeaders();

	public ListenableFuture<StompSession> connect() {
		final Transport webSocketTransport = new WebSocketTransport(new StandardWebSocketClient());
		final List<Transport> transports = Collections.singletonList(webSocketTransport);

		final SockJsClient sockJsClient = new SockJsClient(transports);
		sockJsClient.setMessageCodec(new Jackson2SockJsMessageCodec());

		final WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
		stompClient.setMessageConverter(new MappingJackson2MessageConverter());

		final String url = "ws://{host}:{port}/log";
		return stompClient.connect(url, headers, new MyStompSessionHandler(), "localhost", 8080);
	}

	public void send(StompSession stompSession, String message) {
		final LogRequest request = new LogRequest(message);
		stompSession.send("/app/log", request);
	}

	public static void main(String[] args) throws Exception {
		final ClientApplication client = new ClientApplication();

		logger.info("Starting client... (press enter to exit after sending and receiving)");
		final ListenableFuture<StompSession> f = client.connect();
		final StompSession stompSession = f.get();

		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		final String message = reader.readLine();

		logger.info("Sending message in session: " + stompSession);
		client.send(stompSession, message);

		new Scanner(System.in).nextLine();
	}

}
