import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//課堂實作題二：課程成績統計
//指定檔名：CourseGradeMap.java
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

    public double average(String course) {
        List<Integer> courseGrades = grades.get(course);

        int sum = 0;

        for (int grade : courseGrades) {
            sum += grade;
        }

        return (double) sum / courseGrades.size();
    }

    public int highest(String course) {
        List<Integer> courseGrades = grades.get(course);

        int highest = courseGrades.get(0);

        for (int grade : courseGrades) {
            if (grade > highest) {
                highest = grade;
            }
        }

        return highest;
    }

    public void printReport() {
    // HashMap 沒有排序保證，所以先把 key 複製出來
        List<String> courses = new ArrayList<>(grades.keySet());

        Collections.sort(courses);

        for (String course : courses) {
            double avg = average(course);
            int high = highest(course);

            System.out.printf(
                    "%s average=%.2f highest=%d%n",
                    course, avg, high
        );
    }
}

    public static void main(String[] args) {
        CourseGradeMap courseMap = new CourseGradeMap();

        courseMap.addGrade("CS101", 80);
        courseMap.addGrade("CS101", 90);
        courseMap.addGrade("MA101", 70);
        courseMap.addGrade("CS101", 100);
        courseMap.addGrade("MA101", 90);
        courseMap.addGrade("DS201", 85);

        courseMap.printReport();
    }// 注意若不存在課號,grades.get() 會因為得到 null 而出問題
}
