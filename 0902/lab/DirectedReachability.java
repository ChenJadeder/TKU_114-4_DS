import java.util.*;

public class DirectedReachability {

    private final Map<String, List<String>> graph =
            new LinkedHashMap<>();

    public void addVertex(String vertex) {
        graph.putIfAbsent(vertex, new ArrayList<>());
    }

    // 加入 directed edge: from -> to
    public void addEdge(String from, String to) {
        addVertex(from);
        addVertex(to);

        graph.get(from).add(to);
    }

    // 使用 BFS 判斷 from 是否可以到達 to
    public boolean isReachable(String from, String to) {

        // missing vertex
        if (!graph.containsKey(from)
                || !graph.containsKey(to)) {
            return false;
        }

        // vertex 存在，而且起終點相同
        if (from.equals(to)) {
            return true;
        }

        Queue<String> queue = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();

        queue.offer(from);
        visited.add(from);

        while (!queue.isEmpty()) {
            String current = queue.poll();

            for (String neighbor : graph.get(current)) {

                if (neighbor.equals(to)) {
                    return true;
                }

                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.offer(neighbor);
                }
            }
        }

        return false;
    }

    // 執行多組 from-to query
    public void runQueries(String[][] queries) {

        if (queries == null || queries.length == 0) {
            System.out.println("No queries");
            return;
        }

        for (String[] query : queries) {

            if (query == null || query.length != 2) {
                System.out.println("Invalid query");
                continue;
            }

            String from = query[0];
            String to = query[1];

            System.out.println(
                    from + " -> " + to
                    + " : "
                    + isReachable(from, to)
            );
        }
    }

    public static void main(String[] args) {
        DirectedReachability graph =
                new DirectedReachability();

        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "D");

        // Another way
        graph.addEdge("E", "F");

        String[][] queries = {
            {"A", "D"},
            {"D", "A"},
            {"A", "A"},
            {"E", "F"},
            {"F", "E"},

            // missing vertex
            {"A", "Z"}
        };

        System.out.println("=== Queries ===");
        graph.runQueries(queries);

        System.out.println("\n=== Empty Queries ===");
        graph.runQueries(new String[0][0]);

        System.out.println("\n=== Empty Graph ===");
        DirectedReachability empty =
                new DirectedReachability();

        System.out.println(
                "A -> B : "
                + empty.isReachable("A", "B")
        );
    }
}
