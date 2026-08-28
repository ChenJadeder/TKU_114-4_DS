import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Q06_EnrollmentIndex {

    // To Hash or To Tree , That's is a Mapping
    private final Map<String, Set<String>> enrollmentMap;

    public Q06_EnrollmentIndex() {
        this.enrollmentMap = new HashMap<>();
    }

    // 選課
    public boolean enroll(String courseCode, String studentId) {
        if (courseCode == null || courseCode.isBlank()) return false;
        if (studentId == null || studentId.isBlank()) return false;

        courseCode = courseCode.trim();
        studentId = studentId.trim();

        Set<String> students = enrollmentMap.get(courseCode);
        if (students == null) {
            students = new HashSet<>(); 
            enrollmentMap.put(courseCode, students);
        }

        // 重複選課回傳 false
        if (students.contains(studentId)) {
            return false;
        }

        return students.add(studentId);
    }

    // 退選
    public boolean drop(String courseCode, String studentId) {
        if (courseCode == null || courseCode.isBlank()) return false;
        if (studentId == null || studentId.isBlank()) return false;

        courseCode = courseCode.trim();
        studentId = studentId.trim();

        Set<String> students = enrollmentMap.get(courseCode);
        if (students == null) return false;

        boolean removed = students.remove(studentId);
        if (!removed) return false;

        // 若無人選課，移除 courseCode
        if (students.isEmpty()) {
            enrollmentMap.remove(courseCode);
        }
        return true;
    }

    // 查詢課程人數
    public int courseSize(String courseCode) {
        if (courseCode == null) return 0;
        courseCode = courseCode.trim();
        Set<String> students = enrollmentMap.get(courseCode);
        return students == null ? 0 : students.size();
    }

    // 查詢某課程
    public List<String> studentsOf(String courseCode) {
        if (courseCode == null) return Collections.emptyList();
        courseCode = courseCode.trim();
        Set<String> set = enrollmentMap.get(courseCode);
        if (set == null) return Collections.emptyList();

        List<String> list = new ArrayList<>(set);
        Collections.sort(list); // turn to  List 後依字典序排序
        return Collections.unmodifiableList(list);
    }

    // 查詢某學生
    public List<String> coursesOf(String studentId) {
        if (studentId == null) return Collections.emptyList();
        studentId = studentId.trim();
        List<String> courses = new ArrayList<>();

        for (Map.Entry<String, Set<String>> e : enrollmentMap.entrySet()) {
            if (e.getValue().contains(studentId)) {
                courses.add(e.getKey());
            }
        }
        Collections.sort(courses); 
        return Collections.unmodifiableList(courses);
    }

    // 總結清單容器
    public Map<String, Integer> summary() {
        Map<String, Integer> snap = new TreeMap<>();
        for (Map.Entry<String, Set<String>> e : enrollmentMap.entrySet()) {
            snap.put(e.getKey(), e.getValue().size());
        }
        return Collections.unmodifiableMap(snap);
    }

}