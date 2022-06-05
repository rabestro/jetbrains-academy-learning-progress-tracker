package lv.id.jc.tracker.command;

import java.util.Optional;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;

/**
 * Common methods for application commands.
 */
@FunctionalInterface
public interface Command extends Runnable {
    Scanner scanner = new Scanner(System.in);

    /**
     * Response to a user request.
     *
     * @param request - a command entered by the user
     * @return command result or error message.
     */
    String getResponse(String request);

    /**
     * The default process of executing application commands.
     * <p>
     * All concrete commands must implement the getResponse method and
     * optionally redefine header(), footer() and runningCondition().
     */
    @Override
    @SuppressWarnings("squid:S106")
    default void run() {
        header().ifPresent(System.out::println);

        Stream.generate(scanner::nextLine)
                .map(String::trim)
                .takeWhile(runningCondition())
                .map(this::getResponse)
                .forEach(System.out::println);

        footer().ifPresent(System.out::println);
    }

    /**
     * Condition for executing commands.
     *
     * @return - a string predicate that tests the user's request and returns:
     * - true to continue processing user requests,
     * - false to finish processing user requests and exit the command.
     */
    default Predicate<String> runningCondition() {
        return not("back"::equalsIgnoreCase);
    }

    /**
     * Command header.
     * <p>
     * Optional text that can be printed before starting this command.
     *
     * @return a message that is printed before the command is started.
     */
    default Optional<String> header() {
        return Optional.empty();
    }

    /**
     * Command footer.
     * <p>
     * Optional text that can be printed after finishing this command.
     *
     * @return a message that is printed after the command is finished.
     */
    default Optional<String> footer() {
        return Optional.empty();
    }

}
