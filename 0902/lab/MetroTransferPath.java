import java.util.*;

public class MetroTransferPath {

    private final Map<String, List<String>> graph =
            new LinkedHashMap<>();

    // 加入捷運站
    public void addStation(String station) {
        graph.putIfAbsent(station, new ArrayList<>());
    }

    // 兩站之間建立雙向連接
    public void addConnection(String a, String b) {
        addStation(a);
        addStation(b);

        graph.get(a).add(b);
        graph.get(b).add(a);
    }

    // BFS 找出 edge 數最少的路徑
    public List<String> shortestPath(
            String start,
            String target) {

        List<String> empty = new ArrayList<>();

        // missing station
        if (!graph.containsKey(start)
                || !graph.containsKey(target)) {
            return empty;
        }

        // 起點與終點相同，路徑包含一站、0 條 edge
        if (start.equals(target)) {
            return new ArrayList<>(List.of(start));
        }

        Queue<String> queue = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();
        Map<String, String> previous = new HashMap<>();

        queue.offer(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            String current = queue.poll();

            for (String next : graph.get(current)) {

                if (!visited.contains(next)) {
                    visited.add(next);
                    previous.put(next, current);

                    if (next.equals(target)) {
                        return buildPath(
                                start, target, previous);
                    }

                    queue.offer(next);
                }
            }
        }

        // unreachable
        return empty;
    }

    // 從 target 沿 predecessor 回到 start
    private List<String> buildPath(
            String start,
            String target,
            Map<String, String> previous) {

        List<String> path = new ArrayList<>();
        String current = target;

        while (current != null) {
            path.add(current);

            if (current.equals(start)) {
                break;
            }

            current = previous.get(current);
        }

        Collections.reverse(path);
        return path;
    }

    // 輸出路徑、站數與 edge count
    public void printPath(String start, String target) {

        if (!graph.containsKey(start)
                || !graph.containsKey(target)) {
            System.out.println(
                    start + " -> " + target
                    + ": missing station");
            return;
        }

        List<String> path =
                shortestPath(start, target);

        if (path.isEmpty()) {
            System.out.println(
                    start + " -> " + target
                    + ": unreachable");
            return;
        }

        System.out.println(
                "Path: " + path);

        System.out.println(
                "Station count: " + path.size());

        System.out.println(
                "Edge count: " + (path.size() - 1));
    }

    public static void main(String[] args) {

        MetroTransferPath metro =
                new MetroTransferPath();

        metro.addConnection("Taipei", "Zhongshan");
        metro.addConnection("Zhongshan", "Shuanglian");
        metro.addConnection("Shuanglian", "Minquan");

        metro.addConnection("Taipei", "Ximen");
        metro.addConnection("Ximen", "Longshan");
        metro.addConnection("Longshan", "Banqiao");

        // isolated station
        metro.addStation("Airport");

        System.out.println("=== Normal Case ===");
        metro.printPath("Taipei", "Minquan");

        System.out.println("\n=== Same Station ===");
        metro.printPath("Taipei", "Taipei");

        System.out.println("\n=== Unreachable ===");
        metro.printPath("Taipei", "Airport");

        System.out.println("\n=== Missing Station ===");
        metro.printPath("Taipei", "Unknown");

        System.out.println("\n=== Empty Graph ===");

        MetroTransferPath empty =
                new MetroTransferPath();

        empty.printPath("A", "B");
    }
}
