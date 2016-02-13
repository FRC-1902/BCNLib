package com.explodingbacon.bcnlib.networking;

import com.explodingbacon.bcnlib.utils.CodeThread;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client extends CodeThread {

    private Socket server;
    private PrintWriter out;
    private BufferedReader in;
    private boolean init = false;

    private List<String> outQueue = new ArrayList<>();
    private Object MESSAGE_QUEUE_EDIT = new Object();

    public Client() {
        super();
        try {
            start();
        } catch (Exception e) {}
    }

    @Override
    public void code() {
        try {
            if (!init) {
                server = new Socket("10.19.2.2", Server.PORT);

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
                }
                outQueue.clear();
            }

        } catch (Exception e) {
        }
    }

    public void sendMessage(String s) {
        synchronized (MESSAGE_QUEUE_EDIT) {
            outQueue.add(s);
        }
    }
}
