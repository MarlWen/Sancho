package com.github.marwen;

import lombok.extern.java.Log;

/**
 * Hello world!
 *
 */
@Log(topic = "App")
public class App {

    public static void main(String[] args) {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] [%4$-7s] [%3$s - %2$s] %5$s %n");
        if (args.length == 2) {
            System.setProperty("server.host", args[0]);
            System.setProperty("server.port", args[1]);
        }
        log.info("App started.");

        try {
            new AppClient().start();
        } catch (InterruptedException e) {
            log.warning(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
