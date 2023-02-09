package lt.code.academy.generator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import lt.code.academy.Main;
import lt.code.academy.data.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.*;

public class GenerateData {
    private final Map<Integer, Exam> exams = new HashMap<>();
    private final Map<Integer, Student> students = new HashMap<>();
    private final Map<Integer, List<Answer>> studentAnswersToExam = new HashMap<>();
    private final Map<Integer, List<Answer>> correctAnswersToExam = new HashMap<>();
    private final String resultsFolderName;
    private final String correctAnswersFolderName;
    private final ObjectMapper mapper;

    public GenerateData(String resultsFolderName, String correctAnswersFolderName, ObjectMapper mapper) {
        this.resultsFolderName = resultsFolderName;
        this.correctAnswersFolderName = correctAnswersFolderName;
        this.mapper = mapper;
        checkFolders();
        generateFakeData();
        writeFakeStudentAnswersToFiles();
        writeFakeExamsCorectAnswersToFiles();
    }

    public void checkFolders(){
        File answersFolder = new File (resultsFolderName);
        if (!answersFolder.exists()){
            try {
                Files.createDirectory(Path.of(resultsFolderName));
            } catch (IOException e) {
                System.out.println("GenerateData -> Something went wrong when creating directory: " + resultsFolderName);
            }
        }
        File correctAnswersFolder = new File (correctAnswersFolderName);
        if (!correctAnswersFolder.exists()){
            try {
                Files.createDirectory(Path.of(correctAnswersFolderName));
            } catch (IOException e) {
                System.out.println("GenerateData -> Something went wrong when creating directory: " + correctAnswersFolderName);
            }
        }
    }

    public void generateFakeData() {
        Faker faker = new Faker();
        Random rnd = new Random();

        generateFakeExamsCorrectAnswers(rnd, faker);

        for (int i = 1; i < Main.NUMBER_OF_STUDENTS + 1; i++) {
            students.put(i, new Student(i, faker.name().firstName(), faker.name().lastName()));
            studentAnswersToExam.put(i, generateFakeAnswers(rnd));
        }
    }

    public void writeFakeStudentAnswersToFiles() {
        Random rnd = new Random();
        for (int i = 1; i < Main.NUMBER_OF_STUDENTS + 1; i++) {
            writeFiles(i, rnd);
        }
    }

    public void writeFakeExamsCorectAnswersToFiles() {
        for (int i = 1; i < Main.NUMBER_OF_CORRECT_ANSWERS_FILES + 1; i++) {
            writeFiles(i);
        }
    }

    public void generateFakeExamsCorrectAnswers(Random rnd, Faker faker) {
        for (int i = 1; i < Main.NUMBER_OF_CORRECT_ANSWERS_FILES + 1; i++) {
            LocalDateTime dt = LocalDateTime.now();
            Exam exam = new Exam(i, faker.programmingLanguage().name(), dt, ExamType.TEST);
            exams.put(i, exam);
            correctAnswersToExam.put(i, generateFakeAnswers(rnd));
        }
    }

    public List<Answer> generateFakeAnswers(Random rnd) {
        List<Answer> answers = new ArrayList<>();
        String answerVariants = "abcdef";
        for (int i = 0; i < Main.NUMBER_OF_ANSVERS; i++) {
            String answer = String.valueOf(answerVariants.charAt(rnd.nextInt(answerVariants.length())));
            answers.add(new Answer(i + 1, answer));
        }
        return answers;
    }

    void writeFiles(int studentsID, Random rnd) {
        Student student = students.get(studentsID);
        List<Answer> answers = studentAnswersToExam.get(studentsID);
        Exam exam = exams.get(rnd.nextInt(Main.NUMBER_OF_CORRECT_ANSWERS_FILES) + 1);
        LocalDateTime dt = LocalDateTime.now();
        long randomMinutes = rnd.nextInt(-120, 120);
        dt = dt.plusMinutes(randomMinutes);
        StudentAnswersToExam studentAnswersToExam = new StudentAnswersToExam(student, exam, dt, answers);
        try {
            mapper.writeValue(new File(resultsFolderName + "/" + studentsID + ".json"), studentAnswersToExam);
        } catch (IOException e) {
            System.out.println("GenerateData -> Cannot write student answers file...");
        }
    }

    void writeFiles(int examsID) {
        Exam exam = exams.get(examsID);
        List<Answer> correctAnswers = correctAnswersToExam.get(examsID);
        CorrectAnswers correctAnswersRecord = new CorrectAnswers(exam, correctAnswers);
        try {
            mapper.writeValue(new File(correctAnswersFolderName + "/correctAnswers" + examsID + ".json"), correctAnswersRecord);
        } catch (IOException e) {
            System.out.println("GenerateData -> Cannot write correct answers file...");
        }
    }

    Map<Integer, Student> getStudents() {
        return students;
    }
}

