package com.redkite.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

/**
 * Created by Kirill Liubun on 10/07/2015.
 */
public class UserConnection implements Runnable {
    private static final Logger logger = LogManager.getLogger(UserConnection.class);

    private String name;
    private Socket socket;
    private OutputStream out;
    private InputStream in;

    public UserConnection(Socket socket) throws IOException {
        this.socket = socket;
        in = socket.getInputStream();
        out = socket.getOutputStream();
    }

    @Override
    public void run() {
        try {

            while (true) {

                String message = messageParser(in);


                logger.info("received message from " + name + ": " + message);
                if (message.equals(Constants.STOP)) {
                    logger.info("Session was ended.");
                    break;
                }


                out.write(message.getBytes());
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


    private String messageParser(InputStream in) throws IOException {
        int size;
        byte[] buffer = new byte[Constants.MESSAGE_SIZE];
//                StringBuilder sb = new StringBuilder();
//
//                while ((size = in.read(buffer)) > -1) {
//                    //TODO thing about performance
//                    sb.append(new String(Arrays.copyOf(buffer, size), "UTF-8"));
//                    logger.info(sb.toString());
//                }
//                String message = sb.toString();


        size = in.read(buffer);
        String receivedData = new String(Arrays.copyOf(buffer, size), "UTF-8");
        String[] clientInfo = receivedData.split(Constants.SEPARATOR);
        name = clientInfo[0];
        return (clientInfo.length > 1) ? clientInfo[1] : "empty string";
    }
}
