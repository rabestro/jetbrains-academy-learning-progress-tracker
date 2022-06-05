package lv.id.jc.tracker.repository;


import lv.id.jc.tracker.model.Course;
import lv.id.jc.tracker.model.Student;
import lv.id.jc.tracker.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class InMemoryDatabase implements TrackerRepository {
    private final List<Student> students = new ArrayList<>();

    @Override
    public void save(Student student) {
        students.add(student);
    }

    @Override
    public boolean existsByEmail(final String email) {
        return students.stream().map(Student::email).anyMatch(email::equalsIgnoreCase);
    }

    @Override
    public Optional<Student> findById(final String id) {
        return students.stream().filter(student -> student.id().equals(id)).findAny();
    }

    @Override
    public Stream<Student> findAll() {
        return students.stream();
    }

    @Override
    public List<String> findAllIds() {
        return students.stream().map(Student::id).toList();
    }

    @Override
    public Stream<Task> findAllTasks() {
        return students.stream().map(Student::tasks).flatMap(List::stream);
    }

    @Override
    public Stream<Student> findByCourse(final Course course) {
        return students.stream().filter(course::isEnrolled);
    }

}