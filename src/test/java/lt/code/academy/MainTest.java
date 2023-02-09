package lt.code.academy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }
    @AfterEach
    void cleanUp(){
        SetupCleanup.cleanUp();
    }

    @Test
    void testForExceptionWhenArgsIsNull() {
        String[] args = null;
        Main main = new Main();
        assertThrows(NullPointerException.class, () -> main.startReadFiles(args, mapper));
    }

    @Test
    void testForExceptionWhenArgsIsEmpty() {
        String[] args = {};
        Main main = new Main();
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> main.startReadFiles(args, mapper));
    }

    @Test
    void testForExceptionWhenOneOfArgsIsEmpty() {
        String[] args = {"AnswersFolder"};
        Main main = new Main();
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> main.startReadFiles(args, mapper));
    }
}