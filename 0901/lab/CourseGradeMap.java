import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 課堂實作題二：課程成績統計
// 指定檔名：CourseGradeMap.java
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

        //  邊界處理
        if (courseGrades == null || courseGrades.isEmpty()) {
            return 0.0;
        }

        int sum = 0;
        for (int grade : courseGrades) {
            sum += grade;
        }

        return (double) sum / courseGrades.size();
    }

    public int highest(String course) {
        List<Integer> courseGrades = grades.get(course);

        // 邊界處理在 get(0) 之前檢查
        if (courseGrades == null || courseGrades.isEmpty()) {
            return 0;
        }

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
        
        // 額外測試：Duplicate Score（相同課程、相同分數，確保 List 正常保留不被去重）
        courseMap.addGrade("CS101", 80); 

        // 預期輸出正常報表
        courseMap.printReport();
    }
}
