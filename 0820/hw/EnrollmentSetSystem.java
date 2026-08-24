import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

//課後作業四：課程報名身分集合
class Enrollment {
    private final String studentId;
    private final String courseCode;

    Enrollment(String studentId, String courseCode) {
        this.studentId = (studentId == null) ? "UNKNOWN" : studentId.trim();
        this.courseCode = (courseCode == null) ? "UNKNOWN" : courseCode.trim();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Enrollment e)) return false;
        return Objects.equals(this.studentId, e.studentId)
            && Objects.equals(this.courseCode, e.courseCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, courseCode);
    }

    @Override
    public String toString() {
        return studentId + "-" + courseCode;
    }
}

public class EnrollmentSetSystem {
    public static void main(String[] args) {
        Set<Enrollment> set = new HashSet<>();

        // 同一人可加入不同課
        System.out.println("add S101-CS101=" + set.add(new Enrollment("S101", "CS101"))); // true
        System.out.println("add S101-DS201=" + set.add(new Enrollment("S101", "DS201"))); // true

        // 同一人不可重複加入同一課
        System.out.println("add S101-CS101 again=" + set.add(new Enrollment("S101", "CS101"))); // false

        // 不同人同課 PK
        System.out.println("add S102-CS101=" + set.add(new Enrollment("S102", "CS101"))); // true

        System.out.println("contains S101-CS101=" + set.contains(new Enrollment("S101", "CS101")));
        System.out.println("remove S101-CS101=" + set.remove(new Enrollment("S101", "CS101")));
        System.out.println("contains S101-CS101(after)=" + set.contains(new Enrollment("S101", "CS101")));
        System.out.println("size=" + set.size());
        System.out.println("set=" + set);
    }
}