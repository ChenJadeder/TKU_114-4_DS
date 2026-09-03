// 第 8 題：BFS Traversal
// 使用 Queue 和 visited 進行 BFS‚並且要依照 adjacency List 原本的順序。
 

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class Q08_BfsTraversal {

    // BFS 
    public static List<String> bfs(
            Map<String, List<String>> graph, String start) {

        List<String> result = new ArrayList<String>();

        // 這裡不准使用 graph 或 start
        if (graph == null || start == null
                || !graph.containsKey(start)) {
            return result;
        }

        Queue<String> queue = new LinkedList<String>();
        Set<String> visited = new HashSet<String>();

        // 從 start 開始
        queue.offer(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            result.add(current);

            List<String> neighbors = graph.get(current);

            // adjacency List 可能是 null，避免發生錯誤
            if (neighbors == null) {
                continue;
            }

            // 按照 adjacency List 原本的順序走訪
            for (String next : neighbors) {
                // 只走訪 graph 中存在的 vertex
                if (next != null
                        && graph.containsKey(next)
                        && !visited.contains(next)) {

                    visited.add(next);
                    queue.offer(next);
                }
            }
        }

        return result;
    }

    // 計算 start 到每個可到達 vertex 的距離
    public static Map<String, Integer> distanceFrom(
            Map<String, List<String>> graph, String start) {

        Map<String, Integer> distance =
                new HashMap<String, Integer>();

        // 無效輸入回傳新的 empty Map
        if (graph == null || start == null
                || !graph.containsKey(start)) {
            return distance;
        }

        Queue<String> queue = new LinkedList<String>();
        Set<String> visited = new HashSet<String>();

        // start 與0的距離
        queue.offer(start);
        visited.add(start);
        distance.put(start, 0);

        while (!queue.isEmpty()) {
            String current = queue.poll();

            List<String> neighbors = graph.get(current);

            if (neighbors == null) {
                continue;
            }

            for (String next : neighbors) {
                if (next != null
                        && graph.containsKey(next)
                        && !visited.contains(next)) {

                    visited.add(next);

                    // 到下一層是目前距離加 1
                    distance.put(
                            next, distance.get(current) + 1);

                    queue.offer(next);
                }
            }
        }

        return distance;
    }
}
