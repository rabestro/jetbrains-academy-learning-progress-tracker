package lv.id.jc.tracker.command;


import lv.id.jc.tracker.model.Student;
import lv.id.jc.tracker.repository.TrackerRepository;
import lv.id.jc.tracker.validator.RequestValidator;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.regex.Matcher;

import static lv.id.jc.tracker.validator.StudentValidator.*;

public class AddStudents extends AbstractCommand {
    private final TrackerRepository trackerRepository;
    private int studentsAdded;

    public AddStudents(final TrackerRepository trackerRepository) {
        this.trackerRepository = trackerRepository;
    }

    @Override
    public Optional<String> header() {
        return Optional.of("Enter student credentials or 'back' to return");
    }

    @Override
    public Optional<String> footer() {
        return Optional.of("Total " + studentsAdded + " students have been added.");
    }

    @Override
    public void run() {
        studentsAdded = 0;
        super.run();
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
        return () -> trackerRepository.existsByEmail(matcher.group(EMAIL.name()))
                ? Optional.of("This email is already taken.")
                : Optional.empty();
    }

    private Supplier<String> addStudent(Matcher matcher) {
        return () -> {
            studentsAdded++;
            trackerRepository.save(
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