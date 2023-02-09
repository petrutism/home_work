package lt.code.academy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lt.code.academy.generator.GenerateData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ReadFoldersTest {
    ObjectMapper mapper;
    private ReadFolders readFolders;
    private GenerateData generateData;

    @BeforeEach
    void setUp() {
        SetupCleanup.setUp();
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        generateData = new GenerateData(SetupCleanup.answersFolderTest, SetupCleanup.correctAnswersFolderTest, mapper);
        readFolders = new ReadFolders(SetupCleanup.answersFolderTest, SetupCleanup.correctAnswersFolderTest);
    }
    @AfterEach
    void cleanUp(){
        SetupCleanup.cleanUp();
    }

    @Test
    void testForCorrectNumberOfFilesInAnswersFolder() {
        int answersFiles = readFolders.getStudentAnswerFileNames().size();
        assertEquals(50, answersFiles);
    }

    @Test
    void testForCorrectNumberOfFilesInCorrectAnswersFolder() {
        int correctAnswersFiles = readFolders.getCorrectAnswerFileNames().size();
        assertEquals(3, correctAnswersFiles);
    }

    @Test
    void testForCorrectBehaviorIfFolderExist() {
        Set<String> names;
        names = readFolders.readFileNamesInFolder("AnswersFolder");
        assertNotNull(names);
    }

    @Test
    void testForCorrectBehaviorIfFolderDoesNotExist() {
        Set<String> names;
        names = readFolders.readFileNamesInFolder("Mama");
        assertNull(names);
    }
}