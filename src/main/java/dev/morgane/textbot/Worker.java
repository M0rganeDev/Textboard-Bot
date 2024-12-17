package dev.morgane.textbot;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import javax.net.ssl.SSLContext;

import dev.morgane.textbot.ITask;
import lombok.Getter;

public class Worker extends WebSocketClient
{
	private boolean is_logged = false;

	public boolean get_is_logged()
	{
		return (is_logged);
	}
	
	private final String token;

	private final ITask task;

	@Getter
	private int id;
	
	@Getter
	private static int size = 0;

	public Worker(String token, String url, ITask task) throws URISyntaxException
	{
		super(new URI(url));
		this.token = token;
		this.task = task;
		Main.getLogger().info("Trying to create websocket client");
		id = size;
		++size;
		try {
	        SSLContext sslContext = SSLContext.getDefault();
			setSocket(sslContext.getSocketFactory().createSocket());
		} catch (Exception e) {
			Main.getLogger().info(e.getMessage());
		}
		setConnectionLostTimeout(0);
		addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:133.0) Gecko/20100101 Firefox/133.0");
		try {
			Thread.sleep(250);
		} catch (Exception e) {
			Main.getLogger().info(e.getMessage());
		}
	}

	@Override
	public void onOpen(ServerHandshake handshake)
	{
		Main.getLogger().info("Trying to connect to websocket server");
		BotCommands.connect(this, token);
	}

	@Override
	public void onMessage(String message)
	{
		//Main.getLogger().info("message from server : {}", message);
		if (message.contains("CONNECTED"))
		{
			is_logged = true;
			long start = System.currentTimeMillis();
			Main.getLogger().info("[{}] Worker is waiting for all other workers to be ready", id);
			task.task();
			long finish = System.currentTimeMillis();
			Main.getLogger().info("Finished given task in {}", Main.msToHumanTime((finish - start)));
		}
	}

	@Override
	public void onClose(int code, String reason, boolean remote)
	{
		Main.getLogger().info("[{}] Connexion to server was closed {}[{}] : {}", id, remote ? "by the remote server " : "", code, reason);
		is_logged = false;
	}

	@Override
	public void onError(Exception error)
	{
		error.printStackTrace();
	}
}
