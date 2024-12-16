package dev.morgane.textbot;

import lombok.SneakyThrows;
import org.java_websocket.client.WebSocketClient;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BotCommands {
    public static void subscribe(WebSocketClient client, int x, int y, String token)
    {
    }

    public static void connect(WebSocketClient client, String token)
    {
        System.out.println("authentificating to server");
        String auth = "CONNECT\n";
        auth += "Authorization:" + token + "\n";
        auth += "accept-version:1.2,1.1,1.0\n";
        auth += "heart-beat:10000,10000\n\n\0";
        System.out.println("raw message : \n" + auth.replace(token, "[REDACTED]"));
        client.send(auth);
    }

    public static void edit(WebSocketClient client, int x, int y, char txt)
    {
        if(!Main.IS_LOGGED)
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
        System.out.println("PIXEL CHANGE \n---------------------\n" + msg + "\n---------------------");
        client.send(msg);
    }

    @SneakyThrows
    public static void send_str(WebSocketClient client, int x, int y, String msg)
    {
        for (char c : msg.toCharArray()) {
            if (c != ' ')
            {
                edit(client, x, y, c);
                Thread.sleep(25);
            }
            ++x;
        }
    }

    @SneakyThrows
    public static void send_file(WebSocketClient client, int x, int y, String file_path)
    {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(file_path));
            String line = reader.readLine();

            while (line != null) {
                send_str(client, x, y, line);
                Thread.sleep(25);
                ++y;
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
