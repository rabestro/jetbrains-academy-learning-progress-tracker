package lv.id.jc.tracker.command;

import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import static java.util.function.Predicate.not;

/**
 * Application main menu.
 *
 * This class represents the main menu of the application and executes sub-commands.
 */
public class Application extends AbstractCommand {
    private final Map<String, Command> commands;

    public Application(Map<String, Command> commands) {
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
        if (!commands.containsKey(request)) {
            return "Error: unknown command!";
        }

        commands.get(request).run();
        return "";
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
