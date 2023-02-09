package lt.code.academy.data;

import java.util.List;

public record CorrectAnswers(Exam exam, List<Answer> correctAnswers) {
}
