// 概念 2：Load Factor、Rehash 與 Key Equality
// 作為 key 的欄位應在放入 Map 後保持不變，若是hashcode改變就像地址換了 找不到路
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class StudentKeyMapDemo {
    record StudentKey(String department, String studentId) {
        StudentKey {
            department = normalize(department, "department");
            studentId = normalize(studentId, "studentId");
        }

        private static String normalize(String value, String field) {
            if (value == null || value.isBlank()) {
                throw new IllegalArgumentException(field);
            }
            return value.trim().toUpperCase();
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) return true;
            if (!(other instanceof StudentKey key)) return false;
            return department.equals(key.department)
                    && studentId.equals(key.studentId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(department, studentId);
        }
    }

    public static void main(String[] args) {
        Map<StudentKey, String> names = new HashMap<>();
        names.put(new StudentKey(" im ", "412001"), "Amy");
        names.put(new StudentKey("CS", "412001"), "Ben");

        System.out.println(names.get(new StudentKey("IM", "412001")));
        System.out.println(names.containsKey(new StudentKey(" cs ", "412001")));
        System.out.println("size=" + names.size());
    }
}
