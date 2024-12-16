package dev.morgane.textbot;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.net.URI;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static boolean IS_LOGGED = false;

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

    public static void main(String[] args)
    {
        if ((System.getenv("textboard_token") == null && args.length <= 1) || args.length <= 2)
        {
            print_usage();
            return;
        }

        try
        {
            String serverUrl = "wss://aywenito.textboard.fr:25555/ws";
            WebSocketClient client = new WebSocketClient(new URI(serverUrl)) {

                @Override
                public void onOpen(ServerHandshake handshake) {
                    System.out.println("Connected to server");
                    BotCommands.connect(this, System.getenv("textboard_token") == null ? args[3] : System.getenv("textboard_token"));
                }

                @Override
                public void onMessage(String message) {
                    System.out.println("Received: " + message);
                    if (message.contains("CONNECTED"))
                    {
                        IS_LOGGED = true;
                        long now = System.currentTimeMillis();
                        BotCommands.send_file(this, Integer.parseInt(args[1]), Integer.parseInt(args[2]), args[0]);
                        this.close();
                        long now2 = System.currentTimeMillis();
                        logger.info("\nPrinted {} in {} seconds !", args[0], (now2 - now) / 1000);
                        System.exit(0);
                    }
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    System.out.println("Connection closed: " + reason);
                    IS_LOGGED = false;
                }

                @Override
                public void onError(Exception ex) {
                    ex.printStackTrace();
                }
            };
            SSLContext sslContext = SSLContext.getDefault();
            client.setSocket(sslContext.getSocketFactory().createSocket());
            client.setConnectionLostTimeout(0);
            client.addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:133.0) Gecko/20100101 Firefox/133.0");
            client.connect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}