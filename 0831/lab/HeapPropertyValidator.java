import java.util.Arrays;
import java.util.Collections;
import java.util.List;
// 課堂實作題五：Heap Validator
// 指定檔名：HeapPropertyValidator.java
public class HeapPropertyValidator {

    public static boolean isMinHeap(List<Integer> data) {
        if (data == null) {
            return false;
        }

        // empty 和 single 都不會有違反 parent-child 的情況
        if (data.size() <= 1) {
            return true;
        }

        // 只需要檢查有 child 的 parent
        for (int i = 0; i < data.size() / 2; i++) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;

            if (left < data.size()
                    && data.get(i) > data.get(left)) {
                return false;
            }

            if (right < data.size()
                    && data.get(i) > data.get(right)) {
                return false;
            }
        }

        return true;
    }

    public static boolean isMaxHeap(List<Integer> data) {
        if (data == null) {
            return false;
        }

        if (data.size() <= 1) {
            return true;
        }

        for (int i = 0; i < data.size() / 2; i++) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;

            if (left < data.size()
                    && data.get(i) < data.get(left)) {
                return false;
            }

            if (right < data.size()
                    && data.get(i) < data.get(right)) {
                return false;
            }
        }

        return true;
    }

    private static void check(String name, boolean condition) {
        if (condition) {
            System.out.println("[PASS] " + name);
        } else {
            System.out.println("[FAIL] " + name);
        }
    }

    public static void main(String[] args) {
        List<Integer> minHeap =
                Arrays.asList(1, 5, 3, 8, 6, 7);

        List<Integer> maxHeap =
                Arrays.asList(50, 40, 45, 20, 30, 10);

        List<Integer> duplicate =
                Arrays.asList(10, 10, 10, 10);

        List<Integer> badMin =
                Arrays.asList(1, 5, 2, 3);

        List<Integer> badMax =
                Arrays.asList(50, 40, 60);

        check("valid min heap", isMinHeap(minHeap));
        check("valid max heap", isMaxHeap(maxHeap));

        check("invalid min heap", !isMinHeap(badMin));
        check("invalid max heap", !isMaxHeap(badMax));

        // 題目指定的邊界條件
        check("null min", !isMinHeap(null));
        check("null max", !isMaxHeap(null));

        check("empty min", isMinHeap(Collections.emptyList()));
        check("empty max", isMaxHeap(Collections.emptyList()));

        check("single min", isMinHeap(Arrays.asList(5)));
        check("single max", isMaxHeap(Arrays.asList(5)));

        // 相等也符合 <= 和 >=
        check("duplicate min", isMinHeap(duplicate));
        check("duplicate max", isMaxHeap(duplicate));
    }
}
