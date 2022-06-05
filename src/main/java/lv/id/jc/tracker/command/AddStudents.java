package lv.id.jc.tracker.command;


import lv.id.jc.tracker.model.Student;
import lv.id.jc.tracker.repository.TrackerRepository;
import lv.id.jc.tracker.validator.RequestValidator;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.regex.Matcher;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static lv.id.jc.tracker.validator.StudentValidator.*;

public class AddStudents implements Command {
    private final TrackerRepository repository;
    private int studentsAdded;

    public AddStudents(final TrackerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<String> header() {
        return of("Enter student credentials or 'back' to return");
    }

    @Override
    public Optional<String> footer() {
        return of("Total " + studentsAdded + " students have been added.");
    }

    @Override
    public void run() {
        studentsAdded = 0;
        Command.super.run();
    }

    @Override
    public String getResponse(String request) {
        var matcher = RequestValidator.CREDENTIALS.getMatcher(request);

        return RequestValidator.CREDENTIALS.validate(matcher)
                .or(() -> FIRSTNAME.validate(matcher))
                .or(() -> LASTNAME.validate(matcher))
                .or(() -> EMAIL.validate(matcher))
                .or(duplicateEmailValidate(matcher))
                .orElseGet(addStudent(matcher));
    }

    private Supplier<Optional<String>> duplicateEmailValidate(Matcher matcher) {
        return () -> repository.existsByEmail(matcher.group(EMAIL.name()))
                ? of("This email is already taken.")
                : empty();
    }

    private Supplier<String> addStudent(Matcher matcher) {
        return () -> {
            studentsAdded++;
            repository.save(
                    new Student(
                            UUID.randomUUID().toString(),
                            matcher.group(FIRSTNAME.name()),
                            matcher.group(LASTNAME.name()),
                            matcher.group(EMAIL.name())
                    )
            );
            return "The student has been added.";
        };
    }
}