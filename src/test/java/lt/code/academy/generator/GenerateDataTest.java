package lt.code.academy.generator;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lt.code.academy.SetupCleanup;
import lt.code.academy.data.Answer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class GenerateDataTest {
    private Random random;

    private GenerateData generateData;

    @BeforeEach
    void setUp() {
        SetupCleanup.setUp();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        generateData = new GenerateData(SetupCleanup.answersFolderTest, SetupCleanup.correctAnswersFolderTest, mapper);
        random = new Random();
    }
    @AfterEach
    void cleanUp(){
        SetupCleanup.cleanUp();
    }

    @Test
    void testForCorrectGeneratedStudentsNumber() {
        generateData.generateFakeData();
        int studentsNumber = generateData.getStudents().size();
        assertEquals(50, studentsNumber);
    }

    @Test
    void testForCorrectNumberOfGeneratedFakeAnswers() {
        int answersNumb = generateData.generateFakeAnswers(random).size();
        assertEquals(20, answersNumb);
    }

    @Test
    void testForCorrectAnswerFitsInCorrectAnswersRange() {
        boolean wrong = false;
        String answerVariants = "abcdef";
        List<Answer> answers = generateData.generateFakeAnswers(random);
        for (Answer value : answers) {
            String answer = value.answer();
            if (!answerVariants.contains(answer)) {
                wrong = true;
                break;
            }
        }
        assertFalse(wrong);
    }
}
