package lv.id.jc.tracker;

import lv.id.jc.tracker.command.AddStudents;
import lv.id.jc.tracker.command.Application;
import lv.id.jc.tracker.repository.InMemoryDatabase;

import java.util.Map;

/**
 * Application starting point
 */
public class Main {
    public static void main(String[] args) {
        var repository = new InMemoryDatabase();

        new Application(Map.of(
                "add students", new AddStudents(repository)
        )).run();
    }
}