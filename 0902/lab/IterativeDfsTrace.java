import java.util.*;

public class IterativeDfsTrace {

    private final Map<String, List<String>> graph =
            new LinkedHashMap<>();

    public void addVertex(String vertex) {
        graph.putIfAbsent(vertex, new ArrayList<>());
    }

    // Undirected Graph
    public void addEdge(String a, String b) {
        addVertex(a);
        addVertex(b);

        graph.get(a).add(b);
        graph.get(b).add(a);
    }

    // 輸出每次 push/pop 後的 Stack 與 visited
    public void dfsTrace(String start) {

        if (!graph.containsKey(start)) {
            System.out.println("Missing start: " + start);
            return;
        }

        Deque<String> stack = new ArrayDeque<>();
        Set<String> visited = new LinkedHashSet<>();

        stack.push(start);

        System.out.println(
                "PUSH " + start
                + " | Stack=" + stack
                + " | visited=" + visited
        );

        while (!stack.isEmpty()) {

            String current = stack.pop();

            System.out.println(
                    "POP  " + current
                    + " | Stack=" + stack
                    + " | visited=" + visited
            );

            // 有處理就  跳
            if (visited.contains(current)) {
                continue;
            }

            visited.add(current);

            // 反向 push，讓走訪順序接近 recursive DFS
            List<String> neighbors = graph.get(current);

            for (int i = neighbors.size() - 1; i >= 0; i--) {
                String neighbor = neighbors.get(i);

                if (!visited.contains(neighbor)) {
                    stack.push(neighbor);

                    System.out.println(
                            "PUSH " + neighbor
                            + " | Stack=" + stack
                            + " | visited=" + visited
                    );
                }
            }
        }

        System.out.println("DFS order: " + visited);
    }

    public static void main(String[] args) {
        IterativeDfsTrace graph = new IterativeDfsTrace();

        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("B", "D");
        graph.addEdge("C", "E");

        System.out.println("=== Normal Case ===");
        graph.dfsTrace("A");

        System.out.println("\n=== Missing Start ===");
        graph.dfsTrace("Z");

        System.out.println("\n=== Empty Graph ===");
        IterativeDfsTrace empty = new IterativeDfsTrace();
        empty.dfsTrace("A");
    }
}
