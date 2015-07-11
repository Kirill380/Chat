package com.redkite.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Kirill Liubun on 09/07/2015.
 */
public class Server {

    private static final Logger logger = LogManager.getLogger(Server.class);
    private static final int PORT = 3333;

    public static void main(String[] args)  {
        ServerSocket ss = null;
        Socket s;
        try {
            ss = new ServerSocket(PORT);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        while(true) {
            logger.info("Server waiting for client");
            try {
                s = ss.accept();
                logger.info("New client connected to server");
                new Thread(new UserConnection(s)).start();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }


        }

    }

}
