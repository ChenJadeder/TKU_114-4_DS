import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Q06_AdjacencyMatrixGraph {

    // 保存頂點原本的順序
    private final List<String> vertices;

    // 用名稱快速找到頂點的 index
    private final Map<String, Integer> indexMap;

    // adjacency matrix
    private final boolean[][] matrix;

    public Q06_AdjacencyMatrixGraph(List<String> vertices) {
        this.vertices = new ArrayList<String>();
        this.indexMap = new HashMap<String, Integer>();

        // 創聖頂點
        if (vertices != null) {
            for (String vertex : vertices) {
                // 避免 null 和重複頂點讓index找不到回頭鹿
                if (vertex != null && !indexMap.containsKey(vertex)) {
                    indexMap.put(vertex, this.vertices.size());
                    this.vertices.add(vertex);
                }
            }
        }

        matrix = new boolean[this.vertices.size()][this.vertices.size()];
    }

    // 無向 edge
    public boolean addEdge(String first, String second) {
        Integer firstIndex = indexMap.get(first);
        Integer secondIndex = indexMap.get(second);

        // 頂點不存在
        if (firstIndex == null || secondIndex == null) {
            return false;
        }

        // 不允許 self-loop
        if (firstIndex.equals(secondIndex)) {
            return false;
        }

        // 不允許重複 edge
        if (matrix[firstIndex][secondIndex]) {
            return false;
        }

        // undirected graph，所以兩個方向都要設定
        matrix[firstIndex][secondIndex] = true;
        matrix[secondIndex][firstIndex] = true;

        return true;
    }

    // 移除 edge
    public boolean removeEdge(String first, String second) {
        Integer firstIndex = indexMap.get(first);
        Integer secondIndex = indexMap.get(second);

        if (firstIndex == null || secondIndex == null) {
            return false;
        }

        // 若且無edge 清除
        if (!matrix[firstIndex][secondIndex]) {
            return false;
        }

        // 無向圖的兩個方向都要移除
        matrix[firstIndex][secondIndex] = false;
        matrix[secondIndex][firstIndex] = false;

        return true;
    }

    // 檢查兩個頂點之間是否有 edge
    public boolean hasEdge(String first, String second) {
        Integer firstIndex = indexMap.get(first);
        Integer secondIndex = indexMap.get(second);

        // missing vertex 回傳安全結果
        if (firstIndex == null || secondIndex == null) {
            return false;
        }

        return matrix[firstIndex][secondIndex];
    }

    // 計算一個頂點的 degree
    public int degree(String vertex) {
        Integer index = indexMap.get(vertex);

        if (index == null) {
            return 0;
        }

        int count = 0;

        for (int i = 0; i < vertices.size(); i++) {
            if (matrix[index][i]) {
                count++;
            }
        }

        return count;
    }

    // get target vertice all neighbors
    public List<String> neighbors(String vertex) {
        List<String> result = new ArrayList<String>();

        Integer index = indexMap.get(vertex);

        if (index == null) {
            return result;
        }

        //  constructor 
        for (int i = 0; i < vertices.size(); i++) {
            if (matrix[index][i]) {
                result.add(vertices.get(i));
            }
        }

        // 新result 不會暴露
        return result;
    }
}
