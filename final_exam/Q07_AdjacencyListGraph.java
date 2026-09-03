import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
//
public class Q07_AdjacencyListGraph {

    // 每個 vertex 對應它可以連出去的 vertex
    // 將使用 LinkedHashSet 平等的保留順序 
    private final Map<String, Set<String>> graph =
            new HashMap<String, Set<String>>();

    // 記錄目前 edge 的數量
    private int edgeTotal = 0;

    //  vertex 創社
    public boolean addVertex(String vertex) {
        // null 不當成有效 vertex
        if (vertex == null) {
            return false;
        }

        // 已經存在不要再回來
        if (graph.containsKey(vertex)) {
            return false;
        }

        graph.put(vertex, new LinkedHashSet<String>());
        return true;
    }

    //  directed edge：from -> to not go
    public boolean addEdge(String from, String to) {
        // 兩個 vertex 都必須存在
        if (!graph.containsKey(from) || !graph.containsKey(to)) {
            return false;
        }

        // 不允許盪鞦韆
        if (from.equals(to)) {
            return false;
        }

        Set<String> edges = graph.get(from);

        // Set 的 add 如果遇到重複會說false
        if (!edges.add(to)) {
            return false;
        }

        edgeTotal++;
        return true;
    }

    // 移除 directed edge
    public boolean removeEdge(String from, String to) {
        if (!graph.containsKey(from) || !graph.containsKey(to)) {
            return false;
        }

        Set<String> edges = graph.get(from);

        // Edge 移除  ,IE
        if (!edges.remove(to)) {
            return false;
        }

        edgeTotal--;
        return true;
    }

    // 取得某個 vertex 的 outgoing vertices
    public List<String> outgoing(String vertex) {
        // 每次都建立新的 List
        if (!graph.containsKey(vertex)) {
            return new ArrayList<String>();
        }

        // LinkedHashSet 即是 edge 的加入順序
        return new ArrayList<String>(graph.get(vertex));
    }

    //  edge 指向這個 vertex 全加總
    public int inDegree(String vertex) {
        if (!graph.containsKey(vertex)) {
            return 0;
        }

        int count = 0;

        // 查看每個 vertex 的 outgoing edges
        for (Set<String> edges : graph.values()) {
            if (edges.contains(vertex)) {
                count++;
            }
        }

        return count;
    }

    // 回傳目前全部 directed edges 的數量
    public int edgeCount() {
        return edgeTotal;
    }
}
