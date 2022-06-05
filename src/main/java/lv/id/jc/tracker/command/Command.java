package lv.id.jc.tracker.command;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * Common methods for application commands.
 */
public interface Command extends Runnable {

    /**
     * Response to a user request.
     *
     * @param request - a command entered by the user
     * @return command result or error message.
     */
    String getResponse(String request);

    /**
     * Condition for executing commands.
     *
     * @return - a string predicate that tests the user's request and returns:
     * - true to continue processing user requests,
     * - false to finish processing user requests and exit the command.
     */
    Predicate<String> runningCondition();

    /**
     * Command header.
     *
     * Optional text that can be printed before starting this command.
     *
     * @return a message that is printed before the command is started.
     */
    default Optional<String> header() {
        return Optional.empty();
    }

    /**
     * Command footer.
     *
     * Optional text that can be printed after finishing this command.
     *
     * @return a message that is printed after the command is finished.
     */
    default Optional<String> footer() {
        return Optional.empty();
    }


}
