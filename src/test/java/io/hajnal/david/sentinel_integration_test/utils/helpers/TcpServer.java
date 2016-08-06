package io.hajnal.david.sentinel_integration_test.utils.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.hajnal.david.sentinel.communication.message.Message;

public class TcpServer implements Runnable, AutoCloseable {

	private static final Logger LOGGER = LoggerFactory.getLogger(TcpServer.class);

	public static final int PORT = 9999;
	public static final String HOST = "127.0.0.1";

	private volatile boolean isRun = true;
	private ServerSocket serverSocket;
	private List<Message<String>> messageList;

	public TcpServer() throws IOException {
		serverSocket = new ServerSocket(PORT);
		messageList = new ArrayList<>();
	}

	public List<Message<String>> getMessageList() {
		return messageList;
	}

	@Override
	public void run() {
		LOGGER.info("TcpServer start");
		while (isRun) {
			try {
				Socket connectionSocket = serverSocket.accept();
				BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
				String dataFromClient = inFromClient.readLine();
				LOGGER.debug("Got data: " + dataFromClient);
				Message<String> result = Message.of(dataFromClient);
				messageList.add(result);
			} catch (SocketException e) {
				LOGGER.info("TcpServer socket closed");
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
	}

	@Override
	public void close() throws Exception {
		isRun = false;
		serverSocket.close();
	}
}
