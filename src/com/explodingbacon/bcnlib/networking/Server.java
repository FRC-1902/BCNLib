package com.explodingbacon.bcnlib.networking;

import com.explodingbacon.bcnlib.framework.Log;
import com.explodingbacon.bcnlib.javascript.Javascript;
import com.explodingbacon.bcnlib.utils.CodeThread;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server code (robot-side) for communication between the robot and a client.
 *
 * @author Ryan Shavell
 * @version 2016.2.15
 */

public class Server extends CodeThread {

    private ServerSocket server;
    private Socket client;
    private PrintWriter out;
    private BufferedReader in;
    private boolean init = false;

    public static int PORT = 5800;

    /**
     * Creates a Server.
     */
    public Server() {
        super();
        try {
            server = new ServerSocket(PORT);
            start();
        } catch (Exception e) {
            Log.e("Server initializer exception!");
            e.printStackTrace();
        }
    }

    @Override
    public void code() {
        try {
            if (!init) {
                Log.i("Server init!");
                client = server.accept();

                out = new PrintWriter(client.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));

                init = true;
            }
            String input;

            while ((input = in.readLine()) != null) {
                //Log.i(String.format("Server got message \"%s\"", input));
                if (input.contains("js=")) {
                    input = input.replaceFirst("js=", "");
                    Javascript.run(input);
                }
            }
        } catch (Exception e) {
            Log.e("Server exception");
            e.printStackTrace();
        }
    }
}
