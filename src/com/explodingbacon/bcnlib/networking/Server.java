package com.explodingbacon.bcnlib.networking;

import com.explodingbacon.bcnlib.framework.Log;
import com.explodingbacon.bcnlib.javascript.Javascript;
import com.explodingbacon.bcnlib.utils.CodeThread;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends CodeThread {

    private ServerSocket server;
    private Socket client;
    private PrintWriter out;
    private BufferedReader in;
    private boolean init = false;

    public static int PORT = 5800;

    public Server() {
        super();
        try {
            server = new ServerSocket(PORT);
            start();
        } catch (Exception e) {}
    }

    @Override
    public void code() {
        try {
            if (!init) {
                client = server.accept();

                out = new PrintWriter(client.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));

                init = true;
            }
            String input;

            while ((input = in.readLine()) != null) {
                Log.c("SERVER", input);
                if (input.contains("js=")) {
                    input = input.replaceFirst("js=", "");
                    Javascript.run(input);
                }
            }
        } catch (Exception e) {
        }
    }
}
