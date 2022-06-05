package lv.id.jc.tracker.command;


import lv.id.jc.tracker.model.Course;
import lv.id.jc.tracker.model.Student;
import lv.id.jc.tracker.model.Task;
import lv.id.jc.tracker.repository.TrackerRepository;
import lv.id.jc.tracker.validator.RequestValidator;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Matcher;

public record AddPoints(TrackerRepository repository) implements Command {

    @Override
    public Optional<String> header() {
        return Optional.of("Enter an id and points or 'back' to return");
    }

    @Override
    public String getResponse(String request) {
        var matcher = RequestValidator.POINTS.getMatcher(request);
        return RequestValidator.POINTS.validate(matcher)
                .orElseGet(() -> processRequest(matcher));
    }

    private String processRequest(Matcher matcher) {
        var id = matcher.group("ID");

        Function<Student, Consumer<Course>> courseConsumer = student -> course -> {
            var value = Integer.parseInt(matcher.group(course.name()));
            if (value > 0) {
                student.add(new Task(course, value));
            }
        };

        return repository.findById(id)
                .map(student -> {
                    Course.stream().forEach(courseConsumer.apply(student));
                    return "Points updated";
                }).orElseGet(() -> "No student is found for id=" + id);
    }

}