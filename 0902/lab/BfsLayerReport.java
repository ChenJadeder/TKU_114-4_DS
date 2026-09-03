import java.util.*;

public class BfsLayerReport {

    private final Map<String, List<String>> graph =
            new LinkedHashMap<>();

    // 加入 vertex
    public void addVertex(String vertex) {
        graph.putIfAbsent(vertex, new ArrayList<>());
    }

    // 加入 undirected edge
    public void addEdge(String a, String b) {
        addVertex(a);
        addVertex(b);

        graph.get(a).add(b);
        graph.get(b).add(a);
    }

    // 使用 BFS 計算 start 到每個可達 vertex 的最少 edge 數
    public Map<String, Integer> bfsDistance(String start) {
        Map<String, Integer> distance = new LinkedHashMap<>();

        // missing start
        if (!graph.containsKey(start)) {
            return distance;
        }

        Queue<String> queue = new ArrayDeque<>();

        queue.offer(start);
        distance.put(start, 0);

        while (!queue.isEmpty()) {
            String current = queue.poll();

            for (String neighbor : graph.get(current)) {
                if (!distance.containsKey(neighbor)) {
                    distance.put(
                            neighbor,
                            distance.get(current) + 1
                    );

                    queue.offer(neighbor);
                }
            }
        }

        return distance;
    }

    // 輸出 BFS layer report
    public void printReport(String start) {
        Map<String, Integer> distance = bfsDistance(start);

        if (!graph.containsKey(start)) {
            System.out.println("Missing start: " + start);
            return;
        }

        System.out.println("Start: " + start);

        for (String vertex : graph.keySet()) {
            if (distance.containsKey(vertex)) {
                System.out.println(
                        vertex + " -> " + distance.get(vertex)
                );
            } else {
                System.out.println(
                        vertex + " -> unreachable"
                );
            }
        }
    }

    public static void main(String[] args) {
        BfsLayerReport network = new BfsLayerReport();

        network.addEdge("A", "B");
        network.addEdge("A", "C");
        network.addEdge("B", "D");
        network.addEdge("C", "E");
        network.addEdge("E", "F");

        // isolated vertex
        network.addVertex("G");

        System.out.println("=== Normal Case ===");
        network.printReport("A");

        System.out.println("\n=== Missing Start ===");
        network.printReport("Z");

        System.out.println("\n=== Empty Graph ===");
        BfsLayerReport empty = new BfsLayerReport();
        empty.printReport("A");
    }
}
