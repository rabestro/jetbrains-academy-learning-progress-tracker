package lv.id.jc.tracker.command;

import lv.id.jc.tracker.model.Student;
import lv.id.jc.tracker.repository.TrackerRepository;

import java.util.Optional;

public record Find(TrackerRepository repository) implements Command {
    @Override
    public Optional<String> header() {
        return Optional.of("Enter an id or 'back' to return");
    }

    @Override
    public String getResponse(final String id) {
        return repository.findById(id)
                .map(Student::info)
                .orElse("No student is found for id=" + id);
    }
}
