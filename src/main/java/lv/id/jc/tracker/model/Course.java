package lv.id.jc.tracker.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.stream.Stream;

public enum Course {
    JAVA(600, "Java"),
    DSA(400, "DSA"),
    DATABASES(480, "Databases"),
    SPRING(550, "Spring");

    private static final int SCALE = 1;
    private static final BigDecimal HUNDRED = BigDecimal.valueOf(100);
    private final BigDecimal required;
    private final String name;

    Course(final int required, final String name) {
        this.required = BigDecimal.valueOf(required);
        this.name = name;
    }

    public static Stream<Course> stream() {
        return Arrays.stream(Course.values());
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean isEnrolled(Student student) {
        return student.tasks().stream().map(Task::course).anyMatch(this::equals);
    }

    public boolean isBelonging(Task task) {
        return task.course().equals(this);
    }


    public BigDecimal getProgress(Student student) {
        return BigDecimal.valueOf(student.points(this))
                .multiply(HUNDRED)
                .divide(required, SCALE, RoundingMode.HALF_UP);
    }

    public int getRequiredPoints() {
        return required.intValue();
    }
}