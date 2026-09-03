import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Q05_StudentHashIndex {

    // student 針 他選的課程
    private final Map<String, Set<String>> studentCourses;

    // course 給選這門課的學生
    private final Map<String, Set<String>> courseStudents;

    // 記錄總共有幾筆選課資料
    private int count;

    public Q05_StudentHashIndex() {
        studentCourses = new HashMap<String, Set<String>>();
        courseStudents = new HashMap<String, Set<String>>();
        count = 0;
    }

    // 將輸入整理成 trim 後的大寫,無意義內容回傳 null
    private String normalize(String text) {
        if (text == null) {
            return null;
        }

        String result = text.trim().toUpperCase();

        if (result.isEmpty()) {
            return null;
        }

        return result;
    }

    // 新增選課
    public boolean enroll(String studentId, String courseId) {
        String student = normalize(studentId);
        String course = normalize(courseId);

        // 無效輸入
        if (student == null || course == null) {
            return false;
        }

        // 取得學生目前的課程
        Set<String> courses = studentCourses.get(student);

        // 已經選過這門課
        if (courses != null && courses.contains(course)) {
            return false;
        }

        // 首次見面
        if (courses == null) {
            courses = new HashSet<String>();
            studentCourses.put(student, courses);
        }

        courses.add(course);

        // 更新另一邊的索引
        Set<String> students = courseStudents.get(course);

        if (students == null) {
            students = new HashSet<String>();
            courseStudents.put(course, students);
        }

        students.add(student);

        count++;
        return true;
    }

    // 退選
    public boolean drop(String studentId, String courseId) {
        String student = normalize(studentId);
        String course = normalize(courseId);

        if (student == null || course == null) {
            return false;
        }

        Set<String> courses = studentCourses.get(student);

        // 學生invisible或沒有選這門課
        if (courses == null || !courses.contains(course)) {
            return false;
        }

        // 從student index移除
        courses.remove(course);

        // 如果已經沒有課程，就把空的 key 移除
        if (courses.isEmpty()) {
            studentCourses.remove(student);
        }

        // 從課程索引也移除
        Set<String> students = courseStudents.get(course);

        if (students != null) {
            students.remove(student);

            // 課程學生跑光後也移除空的 key
            if (students.isEmpty()) {
                courseStudents.remove(course);
            }
        }

        count--;
        return true;
    }

    // 查詢學生選了哪些課
    public Set<String> coursesOf(String studentId) {
        String student = normalize(studentId);

        if (student == null) {
            return Set.of();
        }

        Set<String> courses = studentCourses.get(student);

        if (courses == null) {
            return Set.of();
        }

        // copy後，再回傳readonly Set
        return Set.copyOf(courses);
    }

    // 查詢特定課程有哪些學生
    public Set<String> studentsIn(String courseId) {
        String course = normalize(courseId);

        if (course == null) {
            return Set.of();
        }

        Set<String> students = courseStudents.get(course);

        if (students == null) {
            return Set.of();
        }

        //  HashSet 只給場內看
        return Set.copyOf(students);
    }

    // 回傳總筆數
    public int enrollmentCount() {
        return count;
    }
}
