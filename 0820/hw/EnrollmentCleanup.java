import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

// 課後作業三：安全清理名單
public class EnrollmentCleanup {
    private static void printList(String label, List<String> list) {
        System.out.println(label + "=" + list);
    }

    private static boolean isBlank(String s) {
        if (s == null) return true;
        return s.trim().length() == 0;
    }

    public static void main(String[] args) {
        List<String> names = new ArrayList<>();
        // genrate some tag list
        names.add("Amy");
        names.add("Ben");
        names.add("Amy");
        names.add("  ");
        names.add(null);
        names.add("Cara");
        names.add("Ben");

        printList("before", names);

        // remove（null/空白）
        Iterator<String> it = names.iterator();
        while (it.hasNext()) {
            String s = it.next();
            if (isBlank(s)) {
                it.remove();
            }
        }

        printList("after", names);

        // 用 Set 找出重複名單
        Set<String> seen = new HashSet<>();
        Set<String> dup = new HashSet<>();
        for (int i = 0; i < names.size(); i++) {
            String s = names.get(i);
            if (!seen.add(s)) {
                dup.add(s);
            }
        }
        System.out.println("duplicates=" + dup);
    }
}