import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

// 課後作業三：選課重複檢查
// 指定檔名：EnrollmentConflictSet.java

public class EnrollmentConflictSet {

    // studentId + courseId 組成複合 key
    private static class EnrollmentKey {
        private final String studentId;
        private final String courseId;

        public EnrollmentKey(String studentId, String courseId) {
            this.studentId = studentId;
            this.courseId = courseId;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            if (!(obj instanceof EnrollmentKey)) {
                return false;
            }

            EnrollmentKey other = (EnrollmentKey) obj;

            return Objects.equals(studentId, other.studentId)
                    && Objects.equals(courseId, other.courseId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(studentId, courseId);
        }

        @Override
        public String toString() {
            return studentId + "|" + courseId;
        }
    }

    // 用複合 key 判斷這筆選課是否已出現
    private Set<EnrollmentKey> enrollments;

    // studentId -> 該學生的課程集合
    private Map<String, Set<String>> studentCourses;

    // courseId -> 修課人數
    private Map<String, Integer> courseCounts;

    public EnrollmentConflictSet() {
        enrollments = new HashSet<>();
        studentCourses = new HashMap<>();
        courseCounts = new HashMap<>();
    }

    public boolean addEnrollment(String studentId, String courseId) {
        EnrollmentKey key = new EnrollmentKey(studentId, courseId);

        // HashSet.add 回傳 false 表示這組複合 key 已經存在
        boolean added = enrollments.add(key);

        if (!added) {
            return false;
        }

        // 先建立一個新的空 HashSet給新學號
        studentCourses.putIfAbsent(studentId, new HashSet<>());
        studentCourses.get(studentId).add(courseId);

        // 課程第一次出現就從 0 開始計算,若已經有人則將原人數加 1
        courseCounts.put(
                courseId,
                courseCounts.getOrDefault(courseId, 0) + 1
        );

        return true;
    }
}
