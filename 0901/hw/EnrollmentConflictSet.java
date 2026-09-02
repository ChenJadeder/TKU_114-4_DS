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

    // 所有不重複的選課紀錄
    private Set<EnrollmentKey> enrollments;

    // 曾經出現重複的選課紀錄
    private Set<EnrollmentKey> duplicates;

    // studentId -> 該學生的課程集合
    private Map<String, Set<String>> studentCourses;

    // courseId -> 修課人數
    private Map<String, Integer> courseCounts;

    public EnrollmentConflictSet() {
        enrollments = new HashSet<>();
        duplicates = new HashSet<>();
        studentCourses = new HashMap<>();
        courseCounts = new HashMap<>();
    }

    public boolean addEnrollment(String studentId, String courseId) {
        EnrollmentKey key = new EnrollmentKey(studentId, courseId);

        // false 代表已經存在
        boolean added = enrollments.add(key);

        if (!added) {
            duplicates.add(key);
            return false;
        }

        // 新學生先建立空的課程 Set,
        studentCourses.putIfAbsent(studentId, new HashSet<>());
        studentCourses.get(studentId).add(courseId);

        // 新的有效選課才增加該課程人數
        courseCounts.put(
                courseId,
                courseCounts.getOrDefault(courseId, 0) + 1
        );

        return true;
    }

    public Set<String> getCourses(String studentId) {
        if (studentId == null) {
            return new HashSet<>();
        }

        return studentCourses.getOrDefault(
                studentId,
                new HashSet<>()
        );
    }

    public int getCourseCount(String courseId) {
        if (courseId == null) {
            return 0;
        }

        return courseCounts.getOrDefault(courseId, 0);
    }

    public Set<String> getDuplicates() {
        Set<String> result = new HashSet<>();

        for (EnrollmentKey key : duplicates) {
            result.add(key.toString());
        }

        return result;
    }

    public static void main(String[] args) {
        EnrollmentConflictSet data = new EnrollmentConflictSet();

        System.out.println(data.addEnrollment("S001", "CS101"));
        System.out.println(data.addEnrollment("S001", "MA101"));
        System.out.println(data.addEnrollment("S002", "CS101"));
        System.out.println(data.addEnrollment("S001", "CS101"));

        System.out.println(
                "S001 courses = " + data.getCourses("S001")
        );

        System.out.println(
                "CS101 count = " + data.getCourseCount("CS101")
        );

        System.out.println(
                "MA101 count = " + data.getCourseCount("MA101")
        );

        System.out.println(
                "duplicates = " + data.getDuplicates()
        );
    }
}
