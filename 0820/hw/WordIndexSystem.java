import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// 課後作業二：文字索引系統
public class WordIndexSystem {
    private static String cleanToken(String s) {
        if (s == null) return "";
        String t = s.toLowerCase();
        // remove symbols
        t = t.replace(".", "");
        t = t.replace(",", "");
        return t.trim();
    }

    public static void main(String[] args) {
        String[] sentences = {
            "Java, OOP and Generics.",
            "Generics and Collections, in Java.",
            "Map, Set, and List are core."
        };

        Map<String, Integer> counts = new HashMap<>();
        Set<String> unique = new HashSet<>();

        for (int i = 0; i < sentences.length; i++) {
            String line = sentences[i];
            if (line == null) continue;
            String[] tokens = line.split("\\s+");
            for (int j = 0; j < tokens.length; j++) {
                String key = cleanToken(tokens[j]);
                if (key.length() == 0) continue;
                unique.add(key);
                int old = counts.containsKey(key) ? counts.get(key) : 0;
                counts.put(key, old + 1);
            }
        }

        System.out.println("unique=" + unique);
        System.out.println("counts=" + counts);

        System.out.print("freq>=2: ");
        boolean first = true;
        for (Map.Entry<String, Integer> e : counts.entrySet()) {
            if (e.getValue() >= 2) {
                if (!first) System.out.print(", ");
                System.out.print(e.getKey() + "=" + e.getValue());
                first = false;
            }
        }
        System.out.println();
    }
}