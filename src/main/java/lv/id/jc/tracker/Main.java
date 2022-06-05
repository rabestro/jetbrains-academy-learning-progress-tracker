package lv.id.jc.tracker;

import lv.id.jc.tracker.command.Application;

import java.util.Map;

/**
 * Application starting point
 */
public class Main {
    public static void main(String[] args) {
        new Application(Map.of())
                .run();
    }
}