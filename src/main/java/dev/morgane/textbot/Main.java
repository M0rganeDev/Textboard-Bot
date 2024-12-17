package dev.morgane.textbot;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;
import lombok.SneakyThrows;

public class Main {
	@Getter
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
	
	@Getter
    public static boolean IS_LOGGED = false;

	private static Worker client = null;

    private static void print_usage()
    {
        logger.info("Textboard Bot usage : ");
        logger.info(" ");
        logger.info("    > image_path : path to a .txt file with your ascii art");
        logger.info("    > x : origin point on the X axis");
        logger.info("    > y : origin point on the Y axis");
        logger.info("    > access_token : access token to send web socket requests");
        logger.info(" ");
    }

	public static String msToHumanTime(long duration)
	{
		long ms, seconds, minutes, hours;
		String _final = "";
		ms = duration % 1000;
		duration /= 1000;
		seconds = duration % 60;
		duration /= 60;
		minutes = duration % 60;
		duration /= 60;
		hours = duration % 24;

		if (hours != 0)
			_final += hours + "H:";
		if (minutes != 0)
			_final += minutes + "M:";
		if (seconds != 0)
			_final += seconds + "s:";
		if (ms != 0)
			_final += ms + "ms";
		return (_final);
	} 

	@SneakyThrows
    public static void main(String[] args)
    {
        try
        {
			AtomicInteger i = new AtomicInteger(0);
			getTokens().forEach(token -> {
				new Thread(() -> {
					try {
					client = new Worker(token, "wss://aywenito.textboard.fr:25555/ws", () -> {
						BotCommands.send_file(client, Integer.parseInt(args[1]), Integer.parseInt(args[2]), args[0], i.getAndIncrement());
						client.close();
					});
					client.connect();
					} catch (Exception e) {
						// TODO: handle exception
					}
				}).start();
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					// TODO: handle exception
				}
			});
        } catch (Exception e) {
            e.printStackTrace();
        }
		IS_LOGGED = true;
    }

	private static List<String> getTokens()
	{
		List<String> tokens = new ArrayList<>();
		BufferedReader reader;

		try {
			reader = new BufferedReader(new FileReader("tokens.txt"));
            String line = reader.readLine();

            while (line != null) {
				tokens.add(line);
                line = reader.readLine();
            }
            reader.close();
		} catch (Exception e) {
		}

		return (tokens);
	}
}
