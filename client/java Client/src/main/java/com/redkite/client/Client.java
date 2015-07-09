package com.redkite.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Kirill Liubun on 09/07/2015.
 */
public class Client {
    private static final Logger logger = LogManager.getLogger(Client.class);


    public static void main(String[] args) {
        DataOutputStream out;
        DataInputStream in;
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

        try {
            InetAddress ipAddress = InetAddress.getByName("127.0.0.1");
            Socket socket = new Socket(ipAddress, 3333);

            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());


      

            while (true) {
                System.out.print("you>");
                String line = keyboard.readLine();
                out.writeUTF(line); 
                out.flush(); 
                line = in.readUTF();
                if(line.equals(Commands.STOP)) {
                    System.out.println("server> Session was ended, bye");
                    break;
                }
                System.out.println("server> " + line);
            }


        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
