package com.explodingbacon.bcnlib.networking;

import com.explodingbacon.bcnlib.framework.Log;
import com.explodingbacon.bcnlib.utils.CodeThread;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Client code (non-robot side) for communication between the robot and a client.
 *
 * @author Ryan Shavell
 * @version 2016.3.2
 */

public class Client extends CodeThread {

    private String ip = "10.19.2.2";
    private int port = Server.PORT;

    private Socket server;
    private PrintWriter out;
    private BufferedReader in;
    private boolean init = false;

    private List<String> outQueue = new ArrayList<>();
    private final Object MESSAGE_QUEUE_EDIT = new Object();

    /**
     * Creates a new Client.
     */
    public Client() {
        super();
        start();
    }

    /**
     * Creates a new Client that will connect to a specific IP on a specific port.
     * @param ip The IP.
     * @param port The port.
     */
    public Client(String ip, int port) {
        super();
        this.ip = ip;
        this.port = port;
        start();
    }

    @Override
    public void code() {
        try {
            if (!init) {
                Log.i("Client init!");
                server = new Socket(ip, port);

                out = new PrintWriter(server.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(server.getInputStream()));

                init = true;
            }
            /*
            String input;

            while ((input = in.readLine()) != null) {
                Log.c("CLIENT", input);
            }*/
            synchronized (MESSAGE_QUEUE_EDIT) {
                for (String m : outQueue) {
                    out.println(m);
                    Log.d(String.format("Sent message \"%s\"", m));
                }
                outQueue.clear();
            }

        } catch (Exception e) {
            Log.e("Client error!");
            e.printStackTrace();
        }
    }

    /**
     * Sends a message to the server.
     * @param s The message to send.
     */
    public void sendMessage(String s) {
        synchronized (MESSAGE_QUEUE_EDIT) {
            outQueue.add(s);
        }
    }
}
