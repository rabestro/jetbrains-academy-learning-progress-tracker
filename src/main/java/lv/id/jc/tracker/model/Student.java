package lv.id.jc.tracker.model;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toCollection;
import static lv.id.jc.tracker.validator.StudentValidator.EMAIL;
import static lv.id.jc.tracker.validator.StudentValidator.FIRSTNAME;
import static lv.id.jc.tracker.validator.StudentValidator.LASTNAME;

public record Student(
        String id,
        String firstName,
        String lastName,
        String email,
        List<Task> tasks,
        Set<Course> notified
) {
    public Student(Matcher matcher) {
        this(
                generateId(),
                matcher.group(FIRSTNAME.name()),
                matcher.group(LASTNAME.name()),
                matcher.group(EMAIL.name()),
                new ArrayList<>(), EnumSet.noneOf(Course.class)
        );
    }

    private static String generateId() {
        return UUID.randomUUID().toString();
    }

    public void add(Task task) {
        tasks().add(task);
    }

    public int points(Course course) {
        return tasks().stream().filter(course::isBelonging).mapToInt(Task::points).sum();
    }

    public String info() {
        return Course.stream()
                .map(course -> course.name() + "=" + points(course))
                .collect(joining("; ", id() + " points: ", ""));
    }

    public boolean isCompleted(Course course) {
        return points(course) >= course.getRequiredPoints();
    }

    public Set<Course> requireNotification() {
        return Course.stream()
                .filter(this::isCompleted)
                .filter(not(notified::contains))
                .collect(toCollection(() -> EnumSet.noneOf(Course.class)));
    }

    public void addNotification(Course course) {
        notified.add(course);
    }
}