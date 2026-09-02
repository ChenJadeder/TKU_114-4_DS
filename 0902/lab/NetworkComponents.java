import java.util.*;
// 課堂實作 指定檔案: NetworkComponents.java
public class NetworkComponents {

    private final Map<String, List<String>> graph;

    public NetworkComponents() {
        graph = new LinkedHashMap<>();
    }

    // 加入 vertex；已存在時不覆蓋原本的 adjacency list
    public void addVertex(String vertex) {
        graph.putIfAbsent(vertex, new ArrayList<>());
    }

    // 加入 undirected edge，讓兩個 vertex 互為 neighbor
    public void addEdge(String from, String to) {
        addVertex(from);
        addVertex(to);

        graph.get(from).add(to);
        graph.get(to).add(from);
    }

    // 使用 BFS 收集 start 所在的 connected component
    private List<String> collectComponent(
            String start,
            Set<String> visited) {

        List<String> component = new ArrayList<>();
        Queue<String> queue = new ArrayDeque<>();

        queue.offer(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            component.add(current);

            for (String neighbor : graph.get(current)) {
                if (!visited.contains(neighbor)) {
                    // enqueue 時立即標記
                    visited.add(neighbor);
                    queue.offer(neighbor);
                }
            }
        }

        return component;
    }

    // 掃描所有 vertex，找出所有 connected components
    public List<List<String>> findComponents() {
        List<List<String>> components = new ArrayList<>();
        Set<String> visited = new HashSet<>();

        for (String start : graph.keySet()) {
            if (visited.contains(start)) {
                continue;
            }

            List<String> component =
                    collectComponent(start, visited);

            components.add(component);
        }

        return components;
    }

    // 回傳 connected component 數量
    public int getComponentCount() {
        return findComponents().size();
    }

    // 回傳包含 vertex 數量最多的 component
    public List<String> largestComponent() {
        List<List<String>> components = findComponents();
        List<String> largest = new ArrayList<>();

        for (List<String> component : components) {
            if (component.size() > largest.size()) {
                largest = component;
            }
        }

        return new ArrayList<>(largest);
    }

    // 檢查指定 vertex 是否存在，用於 missing vertex case
    public boolean containsVertex(String vertex) {
        return graph.containsKey(vertex);
    }

    // 顯示 adjacency list 
    public void printGraph() {
        System.out.println(graph);
    }

    public static void main(String[] args) {

        // ---------- 一般案例 ----------
        NetworkComponents network = new NetworkComponents();

        network.addEdge("A", "B");
        network.addEdge("B", "C");

        network.addEdge("D", "E");

        // isolated vertex
        network.addVertex("F");

        System.out.println("=== Normal Case ===");
        System.out.println("Graph: ");
        network.printGraph();

        System.out.println(
                "Components: " + network.findComponents());

        System.out.println(
                "Component count: " + network.getComponentCount());

        System.out.println(
                "Largest component: " + network.largestComponent());

        // ---------- Missing vertex ----------
        System.out.println("\n=== Missing Vertex Case ===");

        System.out.println(
                "Contains Z: " + network.containsVertex("Z"));

        // ---------- Empty Graph ----------
        NetworkComponents empty = new NetworkComponents();

        System.out.println("\n=== Empty Graph Case ===");

        System.out.println(
                "Components: " + empty.findComponents());

        System.out.println(
                "Component count: " + empty.getComponentCount());

        System.out.println(
                "Largest component: " + empty.largestComponent());
    }
}
