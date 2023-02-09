package lt.code.academy;

import lt.code.academy.data.Results;

import java.util.List;
import java.util.Set;

public interface Read {
    void readCorrectAnswerFiles(String folder, Set<String> fileNames);

    void readStudentAnswerFiles(String folder, Set<String> fileNames);

    List<Results> readResultsFile(String resultsFileName);
}
