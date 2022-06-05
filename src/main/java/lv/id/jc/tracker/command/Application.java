package lv.id.jc.tracker.command;

import lv.id.jc.tracker.repository.TrackerRepository;

import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.joining;

/**
 * Application main menu.
 * <p>
 * This class represents the main menu of the application and executes sub-commands.
 */
public class Application implements Command {
    private final TrackerRepository repository;

    private final Map<String, Command> commands;

    public Application(TrackerRepository repository, Map<String, Command> commands) {
        this.repository = repository;
        this.commands = commands;
    }

    @Override
    public String getResponse(String request) {
        if (request.isBlank()) {
            return "No input.";
        }
        if ("back".equalsIgnoreCase(request)) {
            return "Enter 'exit' to exit the program";
        }
        if ("list".equalsIgnoreCase(request)) {
            return getAllIds();
        }
        if (!commands.containsKey(request)) {
            return "Error: unknown command!";
        }

        commands.get(request).run();
        return "";
    }

    private String getAllIds() {
        var ids = repository.findAllIds();
        return ids.isEmpty() ? "No students found" : ids.stream()
                .collect(joining("\n", "Students:\n", ""));
    }

    @Override
    public Predicate<String> runningCondition() {
        return not("exit"::equalsIgnoreCase);
    }

    @Override
    public Optional<String> header() {
        return Optional.of("Learning Progress Tracker");
    }

    @Override
    public Optional<String> footer() {
        return Optional.of("Bye!");
    }

}
