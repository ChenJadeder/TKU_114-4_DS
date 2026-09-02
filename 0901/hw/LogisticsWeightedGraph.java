import java.util.HashMap;
import java.util.Map;

public class LogisticsWeightedGraph {
    private Map<String, Map<String, Integer>> graph;

    public LogisticsWeightedGraph() {
        this.graph = new HashMap<>();
    }

    public void addVertex(String vertex) {
        graph.putIfAbsent(vertex, new HashMap<>());
    }

    public boolean addOrUpdateEdge(String from, String to, int weight) {
        // 負權重不加入
        if (weight < 0) {
            return false;
        }

        // 起點和終點必須先存在
        if (!graph.containsKey(from) || !graph.containsKey(to)) {
            return false;
        }

        // Directed Graph，只修改 from -> to
        // 同一條 edge 再 put 會更新 weight
        graph.get(from).put(to, weight);

        return true;
    }

    public Integer getWeight(String from, String to) {
        if (!graph.containsKey(from) || !graph.containsKey(to)) {
            return null;
        }

        return graph.get(from).get(to);
    }

    public boolean removeEdge(String from, String to) {
        if (!graph.containsKey(from) || !graph.get(from).containsKey(to)) {
            return false;
        }

        graph.get(from).remove(to);
        return true;
    }

    public void printOutgoing(String from) {
        if (!graph.containsKey(from)) {
            System.out.println("站點不存在：" + from);
            return;
        }

        Map<String, Integer> outgoing = graph.get(from);
        if (outgoing.isEmpty()) {
            System.out.println(from + " 沒有任何出發的物流路線。");
            return;
        }

        System.out.println(from + " 的出發路線與物流成本:");
        for (Map.Entry<String, Integer> entry : outgoing.entrySet()) {
            System.out.println("  -> " + entry.getKey() + " [成本: " + entry.getValue() + "]");
        }
    }

    public static void main(String[] args) {
        LogisticsWeightedGraph logistics = new LogisticsWeightedGraph();

        // 1. 新增站點 (物流據點)
        logistics.addVertex("台北倉");
        logistics.addVertex("台中倉");
        logistics.addVertex("高雄倉");
        logistics.addVertex("桃園倉");

        // 2. 新增與更新邊
        logistics.addOrUpdateEdge("台北倉", "台中倉", 150);
        logistics.addOrUpdateEdge("台北倉", "桃園倉", 200);
        
        // 測試更新 weight (從 150 改為 120)
        logistics.addOrUpdateEdge("台北倉", "台中倉", 120);

        // 3. 測試邊緣防護
        System.out.println("新增負權重 : " + logistics.addOrUpdateEdge("台北倉", "高雄倉", -50));
        System.out.println("起點或終點不存在: " + logistics.addOrUpdateEdge("台北倉", "台南倉", 100));

        // 4. 印出出發路線
        System.out.println();
        logistics.printOutgoing("台北倉");

        // 5. 查詢 weight
        System.out.println("\n台北倉 -> 台中倉 成本: " + logistics.getWeight("台北倉", "台中倉"));
        System.out.println("台北倉 -> 高雄倉 成本 (不存在): " + logistics.getWeight("台北倉", "高雄倉"));
        System.out.println("台北倉 -> 台南倉 成本 (站點不存在): " + logistics.getWeight("台北倉", "台南倉"));

        // 6. 移除邊
        logistics.removeEdge("台北倉", "桃園倉");
        System.out.println("\n移除 台北倉 -> 桃園倉 後的出發路線：");
        logistics.printOutgoing("台北倉");
    }
}
