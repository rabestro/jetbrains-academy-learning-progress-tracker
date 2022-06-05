package lv.id.jc.tracker.command;

import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;

/**
 * The base, abstract class that contains common logic for all commands.
 */
abstract class AbstractCommand implements Command {
    static final Scanner scanner = new Scanner(System.in);

    /**
     * The abstract process of executing application commands.
     *
     * All concrete commands must implement the getResponse method and
     * redefine header(), footer() and runningCondition() is needed.
     */
    @Override
    public void run() {
        header().ifPresent(System.out::println);

        Stream.generate(scanner::nextLine)
                .map(String::trim)
                .takeWhile(runningCondition())
                .map(this::getResponse)
                .forEach(System.out::println);

        footer().ifPresent(System.out::println);
    }

    /**
     * Exit condition for basic application commands
     *
     * @return - a string predicate that returns
     * - false if the user enters 'back'
     * - true for other commands
     */
    @Override
    public Predicate<String> runningCondition() {
        return not("back"::equalsIgnoreCase);
    }

}
