package dev.morgane.textbot;

import lombok.SneakyThrows;
import org.java_websocket.client.WebSocketClient;

import java.awt.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.LogManager;

public class BotCommands {
    public static void subscribe(Worker client, int x, int y, String token)
    {
    }

    public static void connect(Worker client, String token)
    {
        Main.getLogger().info("authentificating to server");
        String auth = "CONNECT\n";
        auth += "Authorization:" + token + "\n";
        auth += "accept-version:1.2,1.1,1.0\n";
        auth += "heart-beat:10000,10000\n\n\0";
        Main.getLogger().info("raw message : \n" + auth.replace(token, "[REDACTED]"));
        client.send(auth);
    }

    public static void edit(Worker client, int x, int y, char txt)
    {
        if(!client.get_is_logged())
            return;
        String to_send = "";

        to_send += txt;
        if (txt == '\\')
            to_send = "\\\\";

        String payload = String.format("{\"x\":%s,\"y\":%s,\"value\":\"%s\"}", x, y, to_send);
        int length = payload.length();

        String msg = "SEND\n";
        msg += "destination:/app/map/set\n";
        msg += "content-length:" + length + "\n\n";
        msg += payload + "\0";
//        Main.getLogger().info("PIXEL CHANGE \n---------------------\n" + msg + "\n---------------------");
        client.send(msg);
    }

    @SneakyThrows
    public static void send_str(Worker client, int x, int y, String msg)
    {
        for (char c : msg.toCharArray())
		{
        	edit(client, x, y, c);
            Thread.sleep(25);
            ++x;
        }
    }

	public static void send_str(Worker client, AtomicInteger x, AtomicInteger y, String msg)
	{
		send_str(client, x.get(), y.get(), msg);
	}

    @SneakyThrows
    public static void send_file(Worker client, int x, int y, String file_path)
    {
        BufferedReader reader;
		java.util.List<String> strs = new ArrayList<>();
		AtomicInteger x_a = new AtomicInteger(x);
		AtomicInteger index = new AtomicInteger(0);
		AtomicInteger y_a = new AtomicInteger(y);

        try {
            reader = new BufferedReader(new FileReader(file_path));
            String line = reader.readLine();

            while (line != null) {
				strs.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
		strs.forEach(str -> {
			long start = System.currentTimeMillis();
	        send_str(client, x_a, y_a, str);
			long finish = System.currentTimeMillis();
			Main.getLogger().info("printed line {}/{} ({} %) in {} ({} lines left, should be done in {})", 
					index.get(), 
					strs.size(), 
					((float)index.get() / (float)strs.size()) * 100, 
					Main.msToHumanTime((finish - start)), 
					strs.size() - index.get(), 
					Main.msToHumanTime((finish - start) * (strs.size() - index.get())));
			try {
            	Thread.sleep(25);
			} catch (Exception ignored) {}
        	y_a.getAndIncrement();
			index.getAndIncrement();
		});
    }
}
