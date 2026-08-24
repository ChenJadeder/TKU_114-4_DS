import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

// practice：身分由 studentId + courseCode 共同決定
class EnrollmentKey {
    private final String studentId;
    private final String courseCode;

    EnrollmentKey(String studentId, String courseCode) {
        this.studentId = studentId;
        this.courseCode = courseCode;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof EnrollmentKey k)) return false;
        return Objects.equals(studentId, k.studentId)
                && Objects.equals(courseCode, k.courseCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, courseCode);
    }

    @Override
    public String toString() {
        return studentId + " " + courseCode;
    }
}

public class HashSetEqualityDemo {
    public static void main(String[] args) {
        Set<EnrollmentKey> enrollments = new HashSet<>();

        // 同學可加入不同課程
        System.out.println(enrollments.add(new EnrollmentKey("S101", "CS101"))); // true
        System.out.println(enrollments.add(new EnrollmentKey("S101", "DS201"))); // true

        // 不可重複加入同一門課
        System.out.println(enrollments.add(new EnrollmentKey("S101", "CS101"))); // false

        // 不同同學同一課程 PK
        System.out.println(enrollments.add(new EnrollmentKey("S102", "CS101"))); // true

        System.out.println("size=" + enrollments.size()); // 3

        // test contains/remove
        System.out.println("contains S101-CS101=" +
                enrollments.contains(new EnrollmentKey("S101", "CS101")));
        System.out.println("remove S101-CS101=" +
                enrollments.remove(new EnrollmentKey("S101", "CS101")));
        System.out.println("size(after)=" + enrollments.size());
    }
}
