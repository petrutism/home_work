package lt.code.academy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lt.code.academy.data.Results;
import lt.code.academy.generator.GenerateData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class ProcessingFilesTest {
    ObjectMapper mapper;
    @BeforeEach
    void setUp() {
        SetupCleanup.setUp();
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        new GenerateData(SetupCleanup.answersFolderTest, SetupCleanup.correctAnswersFolderTest, mapper);
    }

    @AfterEach
    void cleanUp() {
        SetupCleanup.cleanUp();
    }

    @Test
    void testProcessingFilesForNonExistingResultsFileException() {
        boolean thrown = false;
        ReadFiles readFiles = new ReadFiles(SetupCleanup.answersFolderTest, SetupCleanup.correctAnswersFolderTest, mapper, SetupCleanup.nonExistingFile);
        ProcessingFiles processingFiles = new ProcessingFiles(readFiles, mapper, SetupCleanup.nonExistingFile);

        try {
            for (Results result : readFiles.results) {
                processingFiles.results.put(result.examId(), result);
            }
        } catch (NullPointerException npe) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    void testProcessingFilesResultsFileExistAfterRun() {
        File file = new File(SetupCleanup.resultsFileNameTest);
        ReadFiles readFiles = new ReadFiles(SetupCleanup.answersFolderTest, SetupCleanup.correctAnswersFolderTest, mapper, SetupCleanup.resultsFileNameTest);
        new ProcessingFiles(readFiles, mapper, SetupCleanup.resultsFileNameTest);
        assertTrue(file.exists());
    }
}