package lt.code.academy;

import java.io.File;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class ReadFolders {
    final Set<String> studentAnswerFileNames;
    final Set<String> correctAnswerFileNames;

    ReadFolders(String answersFolder, String correctAnswersFolder) {
        studentAnswerFileNames = readFileNamesInFolder(answersFolder);

        if (studentAnswerFileNames != null && studentAnswerFileNames.isEmpty()) {
            System.out.println("Read Folders -> Folder " + answersFolder + " is empty...");
        }

        correctAnswerFileNames = readFileNamesInFolder(correctAnswersFolder);

        if (correctAnswerFileNames != null && correctAnswerFileNames.isEmpty()) {
            System.out.println("Read Folders -> Folder " + correctAnswersFolder + " is empty...");
        }
    }

    Set<String> readFileNamesInFolder(String foldersName) {
        File fileFolder = new File(foldersName);
        if (!fileFolder.exists()) {
            System.out.println("Read Folders -> Folder " + fileFolder + " does not exist...");
            return null;
        }

        return Stream.of((Objects.requireNonNull(fileFolder.listFiles())))
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .filter(name -> name.endsWith(".json"))
                .collect(Collectors.toSet());
    }

    Set<String> getStudentAnswerFileNames() {
        return studentAnswerFileNames;
    }

    Set<String> getCorrectAnswerFileNames() {
        return correctAnswerFileNames;
    }
}
