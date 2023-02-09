package lt.code.academy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lt.code.academy.generator.GenerateData;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {
    public static final String RESULTS_FILE_NAME = "results.json";
    public static final int NUMBER_OF_CORRECT_ANSWERS_FILES = 3;
    public static final int NUMBER_OF_STUDENTS = 50;
    public static final int NUMBER_OF_ANSVERS = 20;

    public static void main(String[] args) {
        Main main = new Main();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        main.selectAction(args, mapper);
    }

    void selectAction(String[] args, ObjectMapper mapper) {
        Scanner sc = new Scanner(System.in);
        String action;
        do {
            menu();
            action = sc.nextLine();
            switch (action) {
                case "1" -> startProcessData(args, mapper);
                case "2" -> startGenerateData(args, mapper);
                case "3" -> deleteFile();
                case "0" -> System.out.println("Finishing...");
                default -> System.out.println("There is no such action...");
            }
        } while (!action.equals("0"));
    }

    void startProcessData(String[] args, ObjectMapper mapper) {
        ReadFiles readFiles;
        try {
            readFiles = startReadFiles(args, mapper);
        } catch (NullPointerException npe) {
            System.out.println("Start -> Arguments must be set correct way...");
            return;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Start -> Program arguments must be set right - we need at least two of them...");
            return;
        }
        new ProcessingFiles(readFiles, mapper, RESULTS_FILE_NAME);
    }

    ReadFiles startReadFiles(String[] args, ObjectMapper mapper) {
        return new ReadFiles(args[0], args[1], mapper, RESULTS_FILE_NAME);
    }

    void startGenerateData(String[] args, ObjectMapper mapper) {
        try {
            new GenerateData(args[0], args[1], mapper);
            System.out.println("Data successfully generated...");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Program arguments must be set right - we need two of them...");
        }
    }

    void menu() {
        String text = """
                                
                1 -> process data
                2 -> generate data
                3 -> delete results file
                0 -> end
                """;
        System.out.println(text);
    }

    void deleteFile() {
        File file = new File(Main.RESULTS_FILE_NAME);
        if (file.exists()) {
            try {
                Files.delete(Path.of(String.valueOf(file)));
                System.out.println("File succesfully deleted...");
            } catch (IOException e) {
                System.out.println("Sorry, cannot delete " + file.getName());
            }
        } else {
            System.out.println("There is nothing to delete...");
        }
    }
}
