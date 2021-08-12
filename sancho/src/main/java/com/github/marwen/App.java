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
        log.info("App started.");
    }
}
