package com.redkite.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kirill Liubun on 09/07/2015.
 */
public class Client {
    private static final Logger logger = LogManager.getLogger(Client.class);
    private static String name = "unknown";

    public static void main(String[] args) {
        OutputStream out;
        InputStream in;
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        String s;
        try {

            Map<String, String> answers = learnClientData(keyboard);
            name = answers.get("name");
            InetAddress ipAddress = InetAddress.getByName(answers.get("ip"));
            Socket socket = new Socket(ipAddress, 3333);

            in = socket.getInputStream();
            out = socket.getOutputStream();


            while (true) {
                System.out.print(name+">");
                s = keyboard.readLine();
                out.write((name + Constants.SEPARATOR + s).getBytes());
                out.flush();

                String receivedMessage = messageParser(in);

                if(receivedMessage != null) {
                    if (s.equals(Constants.STOP)) {
                        System.out.println("server> Session was ended, bye");
                        break;
                    }
                    if(receivedMessage.equals(""))
                        System.out.println("server> empty string");
                    else
                        System.out.println("server>" + receivedMessage);
                }
            }


        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    private static Map<String, String> learnClientData(BufferedReader keyboard) throws IOException {
        Map<String, String> answers = new HashMap<String, String>();
        System.out.println("Enter the ip address of server you want to connect: ");
        answers.put("ip", keyboard.readLine());
        System.out.println("Enter your name: ");
        answers.put("name", keyboard.readLine());
        return answers;
    }


    private static String messageParser(InputStream in) throws IOException {
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
        return new String(Arrays.copyOf(buffer, size), "UTF-8");
    }




}
