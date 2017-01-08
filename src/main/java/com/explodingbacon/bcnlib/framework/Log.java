package com.explodingbacon.bcnlib.framework;

/**
 * A Logging class for logging different types of messages.
 *
 * @author Ryan Shavell
 * @version 2016.2.9
 */

public class Log {

    /**
     * Logs a WTF message. Should be used for situations that should never happen.
     * @param s The message to be logged.
     */
    public static void wtf(String s) {
        log("WTF", s);
    }

    /**
     * Logs an error message.
     * @param s The message to be logged.
     */
    public static void e(String s) {
        log("ERROR", s);
    }

    /**
     * Logs a warning message.
     * @param s The message to be logged.
     */
    public static void w(String s) {
        log("WARNING", s);
    }

    /**
     * Logs a tuning message. Will be encased in curly brackets {} instead of normal brackets [].
     * @param s The message to be logged.
     */
    public static void t(String s) {
        log("{", "TUNING", "}", s);
    }

    /**
     * Logs a vision message.
     * @param s The message to be logged.
     */
    public static void v(String s) {
        log("VISION", s);
    }

    /**
     * Logs a debug message.
     * @param s The message to be logged.
     */
    public static void d(String s) {
        log("DEBUG", s);
    }

    /**
     * Logs an info message.
     * @param s The message to be logged.
     */
    public static void i(String s) {
        log("INFO", s);
    }

    /**
     * Logs a standard message.
     * @param s The message to be logged.
     */
    public static void l(String s) {
        log("LOG", s);
    }

    /**
     * Logs a message with a custom tag.
     * @param tag The tag the message will have. Will appear as [TAG]
     * @param message The message to be logged.
     */
    public static void c(String tag, String message) {
        log(tag, message);
    }

    /**
     * Logs a message that has a tag.
     * @param tag The tag the message will have. Will appear as [TAG]
     * @param message The message to be logged.
     */
    private static void log(String tag, String message) {
        log("[", tag, "]", message);
    }

    private static void log(String start, String tag, String end, String message) {
        System.out.println(start + tag + end + " " + message);
    }
}
