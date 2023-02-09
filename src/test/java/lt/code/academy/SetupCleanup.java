package lt.code.academy;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class SetupCleanup {
    public final static String answersFolderTest = "AnswersFolderTest";
    public final static String correctAnswersFolderTest = "CorrectAnswersFolderTest";
    final static String resultsFileNameTest = "resultsTest.json";
    final static String nonExistingFile = "NonExisting.json";
    final static String tSout = "testsOutput.txt";
    static FileOutputStream f;

    public static void setUp() {
        try {
            f = new FileOutputStream(tSout);
            System.setOut(new PrintStream(f));
        } catch (FileNotFoundException e) {
            System.out.println("Cannot create testsOutput.txt..." + e.getMessage());
        }
    }

    public static void cleanUp() {
        File folderOne = new File(answersFolderTest);
        File folderTwo = new File(correctAnswersFolderTest);
        File resultsFileTest = new File(resultsFileNameTest);
        File nonExisting = new File(nonExistingFile);
        File tSoutDelete = new File(tSout);
        if (folderOne.exists()) {
            deleteDirOrFile(folderOne);
        }
        if (folderTwo.exists()) {
            deleteDirOrFile(folderTwo);
        }
        if (resultsFileTest.exists()) {
            deleteDirOrFile(resultsFileTest);
        }
        if (nonExisting.exists()) {
            deleteDirOrFile(nonExisting);
        }
        try {
            f.close();
            if (tSoutDelete.exists()) {
                deleteDirOrFile(tSoutDelete);
            }
        } catch (IOException e) {
            System.out.println("Cannot close file output stream..." + e.getMessage());
        }
    }

    static void deleteDirOrFile(File file) {
        if (file.isDirectory()) {
            for (File fileForDelete : Objects.requireNonNull(file.listFiles())) {
                deleteDirOrFile(fileForDelete);
            }
        }
        try {
            Files.delete(Path.of(String.valueOf(file)));
        } catch (IOException e) {
            System.out.println("Sorry, cannot delete test file/folder...");
        }
    }
}
