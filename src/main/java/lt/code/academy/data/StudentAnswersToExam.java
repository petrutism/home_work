package lt.code.academy.data;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public record StudentAnswersToExam(Student student, Exam exam, LocalDateTime dateTime, List<Answer> studentAnswers) implements Comparator<StudentResults> {
    @Override
    public int compare(StudentResults o1, StudentResults o2) {
        return 0;
    }
}

