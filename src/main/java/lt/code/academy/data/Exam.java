package lt.code.academy.data;

import java.time.LocalDateTime;

public record Exam(int examID, String examName, LocalDateTime dateTime, ExamType examType) {
}
