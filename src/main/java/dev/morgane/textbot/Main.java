package dev.morgane.textbot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;

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

    public static void main(String[] args)
    {
        if ((System.getenv("textboard_token") == null && args.length <= 1) || args.length <= 2)
        {
            print_usage();
            return;
        }
        try
        {
			client = new Worker(System.getenv("textboard_token") == null ? args[3] : System.getenv("textboard_token"), "wss://aywenito.textboard.fr:25555/ws", () -> {
				BotCommands.send_file(client, Integer.parseInt(args[1]), Integer.parseInt(args[2]), args[0]);
				client.close(0);
			});
            client.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
