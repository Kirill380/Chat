package com.redkite;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Kirill Liubun on 09/07/2015.
 */
public class Server {
    private static final Logger logger = LogManager.getLogger(Server.class);
    public static void main(String[] args) {
        logger.info("Server waiting for client");
    }

}
