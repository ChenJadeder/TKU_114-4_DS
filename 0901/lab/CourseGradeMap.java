import java.util.ArrayList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseGradeMap {

    private Map<String, List<Integer>> grades;

    public CourseGradeMap() {
        grades = new HashMap<>();
    }

    public void addGrade(String course, int grade) {
        if (!grades.containsKey(course)) {
            grades.put(course, new ArrayList<>());
        }

        grades.get(course).add(grade);
    }

    public static void main(String[] args) {
        CourseGradeMap courseMap = new CourseGradeMap();

        courseMap.addGrade("CS101", 80);
        courseMap.addGrade("CS101", 90);
        courseMap.addGrade("MA101", 70);
        courseMap.addGrade("CS101", 100);
        courseMap.addGrade("MA101", 90);
        courseMap.addGrade("DS201", 85);
    }
}
