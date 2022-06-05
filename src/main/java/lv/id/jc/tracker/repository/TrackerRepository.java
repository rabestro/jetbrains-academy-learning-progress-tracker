package lv.id.jc.tracker.repository;

import lv.id.jc.tracker.model.Course;
import lv.id.jc.tracker.model.Student;
import lv.id.jc.tracker.model.Task;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface TrackerRepository {
    void save(Student student);

    boolean existsByEmail(String email);

    Optional<Student> findById(String id);

    Stream<Student> findAll();

    List<String> findAllIds();

    Stream<Task> findAllTasks();

    Stream<Student> findByCourse(Course course);
}