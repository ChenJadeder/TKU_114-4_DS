//課堂實作題三：課程標籤統計

//指定檔名：CourseTagReport.java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class CourseTagReport {

    private static String clean(String s) {
        if (s == null) return "";
        String t = s.toLowerCase();
        t = t.replace(".", "");
        t = t.replace(",", "");
        t = t.trim();
        return t;
    }

    public static void main(String[] args) {
        // 模擬輸入（可能重複、大小寫混雜、含標點與空白）
        String[] input = {
            "Java", "tree", "Graph", "java", "graph,", "List.", "Java", "  ", null, "LIST"
        };

        // 1) 保留原始順序
        List<String> history = new ArrayList<>();
        // 2) 不重複（小寫 + 去標點）
        Set<String> unique = new HashSet<>();
        // 3) 次數統計
        Map<String, Integer> counts = new HashMap<>();

        for (int i = 0; i < input.length; i++) {
            String raw = input[i];
            history.add(raw); // 原樣保存（即使是空白或 null 也會記錄在歷程）

            String key = clean(raw);
            if (key.length() == 0) {
                // 清理後為空的就不放進 Set/Map
                continue;
            }
            unique.add(key);

            int old;
            if (counts.containsKey(key)) {
                old = counts.get(key);
            } else {
                old = 0;
            }
            counts.put(key, old + 1);
        }

        System.out.println("原始順序(List)：" + history);
        System.out.println("不重複標籤(Set)：" + unique);
        System.out.println("次數統計(Map)：" + counts);

        System.out.println();

    }
}
