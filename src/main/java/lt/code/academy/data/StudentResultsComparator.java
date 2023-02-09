package lt.code.academy.data;

import java.util.Comparator;

public class StudentResultsComparator implements Comparator<StudentResults> {
    public int compare(StudentResults s1, StudentResults s2) {
        return Integer.compare(s1.studentID(), s2.studentID());
    }
}
