package lt.code.academy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lt.code.academy.data.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

class ReadFiles extends ReadFolders implements Read {
    final ObjectMapper mapper;
    final Set<StudentAnswersToExam> studentAnswers = new HashSet<>();
    final Map<Integer, CorrectAnswers> correctAnswers = new HashMap<>();
    final List<Results> results;

    ReadFiles(String answersFolder, String correctAnswersFolder, ObjectMapper mapper, String resultsFileName) {
        super(answersFolder, correctAnswersFolder);
        this.mapper = mapper;
        readCorrectAnswerFiles(correctAnswersFolder, super.getCorrectAnswerFileNames());
        readStudentAnswerFiles(answersFolder, super.getStudentAnswerFileNames());
        results = readResultsFile(resultsFileName);
    }

    @Override
    public void readCorrectAnswerFiles(String folder, Set<String> fileNames) {
        for (String file : fileNames) {
            File f = new File((folder + "/" + file));
            try {
                CorrectAnswers correctAnswers = mapper.readValue(f, CorrectAnswers.class);
                this.correctAnswers.put(correctAnswers.exam().examID(), correctAnswers);
            } catch (JsonProcessingException e) {
                System.out.println("ReadFiles -> Answer from file: " + file + " cannot be parsed... Format error.");
            } catch (IOException e) {
                System.out.println("ReadFiles -> Sorry, cannot read answers file: " + f);
            }
        }
    }

    @Override
    public void readStudentAnswerFiles(String folder, Set<String> fileNames) {
        for (String file : fileNames) {
            File f = new File((folder + "/" + file));
            try {
                studentAnswers.add(mapper.readValue(f, StudentAnswersToExam.class));
            } catch (JsonProcessingException e) {
                System.out.println("ReadFiles -> Answer from file: " + file + " cannot be parsed... Format error.");
            } catch (IOException e) {
                System.out.println("ReadFiles -> Sorry, cannot read answers file: " + f);
            }
        }
    }

    @Override
    public List<Results> readResultsFile(String resultsFileName) {
        File resultsFile = new File(resultsFileName);
        if (!resultsFile.exists()) {
            System.out.println("ReadFiles -> Results file " + resultsFileName + " does not exist at start... Will create new one if need.");
            return null;
        }
        try {
            List<Results> results = mapper.readValue(resultsFile, Exams.class).results();
            System.out.println("ReadFiles -> Results file exists at start, read...");
            return results;
        } catch (JsonProcessingException e) {
            System.out.println("ReadFiles -> Results from file " + resultsFileName + " cannot be parsed... Format error.");
        } catch (IOException io) {
            System.out.println("ReadFiles -> Houston, we have a problem when read results file " + resultsFileName + "at start...");
        }


//        if (resultsFile.exists()) {
//            try {
//                List<Results> results = mapper.readValue(resultsFile, Exams.class).results();
//                System.out.println("ReadFiles -> Results file exists at start, read...");
//                return results;
//            } catch (JsonProcessingException e) {
//                System.out.println("ReadFiles -> Results from file " + resultsFileName + " cannot be parsed... Format error.");
//            } catch (IOException io) {
//                System.out.println("ReadFiles -> Houston, we have a problem when read results file " + resultsFileName + "at start...");
//
//            }
//        } else {
//            System.out.println("ReadFiles -> Results file " + resultsFileName + " does not exist at start... Will create new one if need.");
//        }

        return null;
    }
}
