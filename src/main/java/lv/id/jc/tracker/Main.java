package lv.id.jc.tracker;

import lv.id.jc.tracker.command.AddPoints;
import lv.id.jc.tracker.command.AddStudents;
import lv.id.jc.tracker.command.Application;
import lv.id.jc.tracker.command.Find;
import lv.id.jc.tracker.repository.InMemoryDatabase;

import java.util.ArrayList;
import java.util.Map;

/**
 * Application starting point
 */
public class Main {
    public static void main(String[] args) {
        var repository = new InMemoryDatabase(new ArrayList<>());

        new Application(repository, Map.of(
                "add students", new AddStudents(repository),
                "add points", new AddPoints(repository),
                "find", new Find(repository)
        )).run();
    }
}