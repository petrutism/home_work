package lt.code.academy.data;

import java.util.List;

public record Results(int examId, String examName, List<StudentResults> studentResults) {
}

