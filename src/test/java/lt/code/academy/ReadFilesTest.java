package lt.code.academy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lt.code.academy.generator.GenerateData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ReadFilesTest {
    private ReadFiles readfiles;
    private GenerateData generateData;

    @BeforeEach
    void setUp() {
        SetupCleanup.setUp();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        generateData = new GenerateData(SetupCleanup.answersFolderTest, SetupCleanup.correctAnswersFolderTest, mapper);
        readfiles = new ReadFiles(SetupCleanup.answersFolderTest, SetupCleanup.correctAnswersFolderTest, mapper, SetupCleanup.resultsFileNameTest);
    }

    @AfterEach
    void cleanUp() {
        SetupCleanup.cleanUp();
    }

    @Test
    void testForReadCorrectNumberOfJsonFilesFromAnswers() {
        assertEquals(Main.NUMBER_OF_STUDENTS, readfiles.studentAnswers.size());
    }

    @Test
    void testForReadCorrectNumberOfJsonFilesFromCorrectAnswers() {
        assertEquals(Main.NUMBER_OF_CORRECT_ANSWERS_FILES, readfiles.correctAnswers.size());
    }
}