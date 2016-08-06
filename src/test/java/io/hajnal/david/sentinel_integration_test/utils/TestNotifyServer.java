package io.hajnal.david.sentinel_integration_test.utils;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.hajnal.david.sentinel.communication.SocketConnection;
import io.hajnal.david.sentinel.communication.message.Message;
import io.hajnal.david.sentinel.communication.socket.AbstractSocketFactory;
import io.hajnal.david.sentinel.config.CommunicationConfig;
import io.hajnal.david.sentinel_integration_test.utils.helpers.TcpServer;
import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CommunicationConfig.class)
public class TestNotifyServer {

	private static final String TEST_MESSAGE = "echo";
	private static final String TEST_MESSAGE2 = "echoöüóűúőéá";
	private static final String TEST_ID = "test.echo";

	@Autowired
	private ApplicationContext context;

	private SocketConnection underTest;

	private static TcpServer server;

	@BeforeClass
	public static void setUpOnce() throws IOException {
		server = new TcpServer();
		new Thread(server).start();
	}

	@AfterClass
	public static void tearDownOnce() throws Exception {
		server.close();
	}

	@Before
	public void setUp() {
		underTest = new SocketConnection(TcpServer.HOST, TcpServer.PORT);
		AbstractSocketFactory socketFactory = context.getBean(AbstractSocketFactory.class);
		underTest.setSocketFactory(socketFactory);
	}

	@Test
	public void serverShouldReceiveTheMessage() throws InterruptedException {
		// Given
		Message<String> message = new Message<>(TEST_ID, TEST_MESSAGE);
		// When
		underTest.sendMessage(message);
		// Then
		Thread.sleep(100);
		Assert.assertTrue(server.getMessageList().contains(message));
	}
	
	@Test
	public void serverShouldReceiveAnotherTheMessage() throws InterruptedException {
		// Given
		Message<String> message = new Message<>(TEST_ID, TEST_MESSAGE2);
		// When
		underTest.sendMessage(message);
		// Then
		Thread.sleep(100);
		Assert.assertTrue(server.getMessageList().contains(message));
	}
}
