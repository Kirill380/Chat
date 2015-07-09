package com.redkite;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;

/**
 * Created by Kirill Liubun on 10/07/2015.
 */
public class ConnectionHandler implements Runnable {
    private static final Logger logger = LogManager.getLogger(ConnectionHandler.class);

    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;

    public ConnectionHandler(Socket socket) throws IOException {
        this.socket = socket;
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        String s = null;

        try {

            while (true) {
                s = in.readUTF().trim();
                logger.info("received message: " + s);
                if (s.equals(Commands.STOP)) {
                    out.writeUTF(s);
                    out.flush();
                    logger.info("Session was ended.");
                    break;
                }
                out.writeUTF(s);
                out.flush();
            }

        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {

            }
        }


    }

}
