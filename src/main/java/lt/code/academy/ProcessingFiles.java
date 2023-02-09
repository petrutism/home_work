package lt.code.academy;

import com.fasterxml.jackson.databind.ObjectMapper;
import lt.code.academy.data.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;

class ProcessingFiles {
    Map<Integer, Results> results = new HashMap<>();
    List<Results> newlyAddedResults = new ArrayList<>();

    ProcessingFiles(ReadFiles readFiles, ObjectMapper mapper, String resultsFilename) {
        System.out.println("Processing files -> Begin processing data...");
        fillResults(readFiles);

        if (!readFiles.studentAnswers.isEmpty() && readFiles.correctAnswers.size() == Main.NUMBER_OF_CORRECT_ANSWERS_FILES) {
            System.out.println("Processing files -> Both lists are good, processing answers...");
            processStudentAnswers(mapper, readFiles, resultsFilename);
        }
    }

    void fillResults(ReadFiles readFiles) {
        try {
            for (Results result : readFiles.results) {
                results.put(result.examId(), result);
            }
        } catch (NullPointerException npe) {
            System.out.println("Processing files -> Results file does not exist for now...");
        }
    }

    void processStudentAnswers(ObjectMapper objectMapper, ReadFiles readFiles, String resultsFileName) {
        StudentResultsComparator src = new StudentResultsComparator();
        int points = 0;
        boolean isNew = false;
        String srStudentsName;
        String srStudentsSurname;
        int srStudentsID;
        int srExamID;
        String examName;
        Predicate<Integer> predicateAnswers;
        List<StudentResults> studentResults;
        Results results;
        LocalDateTime studentsDateTime;
        LocalDateTime examsDateTime;

        for (StudentAnswersToExam sr : readFiles.studentAnswers) {

            srStudentsName = sr.student().studentsName();
            srStudentsSurname = sr.student().studentsSurname();
            srStudentsID = sr.student().studentId();
            srExamID = sr.exam().examID();
            examName = sr.exam().examName();
            studentsDateTime = sr.dateTime();
            examsDateTime = readFiles.correctAnswers.get(srExamID).exam().dateTime();

            List<Answer> studentAnswers = sr.studentAnswers();
            List<Answer> correctAnswers = readFiles.correctAnswers.get(srExamID).correctAnswers();

            results = this.results.get(srExamID);

            predicateAnswers = i -> studentAnswers.get(i).answer().equals(correctAnswers.get(i).answer());

            if (!resultsAlreadyContainsAnswer(srStudentsID, results)) {
                isNew = true;

                for (int i = 0; i < studentAnswers.size(); i++) {
                    if (predicateAnswers.test(i)) {
                        points++;
                    }
                }

                if (studentsDateTime.isBefore(examsDateTime)) {
                    points++;
                }

                studentResults = (results == null) ? new ArrayList<>() : results.studentResults();

                studentResults.add(new StudentResults(srStudentsID, srStudentsName, srStudentsSurname, points));

                studentResults.sort(src);

                this.results.put(srExamID, new Results(srExamID, examName, studentResults));
                points = 0;
            }
        }

        if (isNew) {
            writeNewDataToFile(objectMapper, resultsFileName);
        } else {
            System.out.println("Processing files -> There is nothing new to write to file...");
        }
    }

    void writeNewDataToFile(ObjectMapper objectMapper, String resultsFilename) {
        for (int i = 0; i < results.size(); i++) {
            newlyAddedResults.add(results.get(i + 1));
        }
        Exams exams = new Exams(newlyAddedResults);
        try {
            objectMapper.writeValue(new File(resultsFilename), exams);
            System.out.println("Processing files -> Writing results file with new lines added...");
        } catch (IOException e) {
            System.out.println("Processing files -> Cannot write new results file...");
        }
    }

    Boolean resultsAlreadyContainsAnswer(int srStudentsID, Results results) {
        if (results == null) {
            return false;
        }
        for (StudentResults studentResult : results.studentResults()) {
            if (studentResult.studentID() == srStudentsID) {
                return true;
            }
        }

        return false;
    }

}




