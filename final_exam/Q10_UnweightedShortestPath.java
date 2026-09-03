import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class Q10_UnweightedShortestPath {

    public static List<String> shortestPath(
            Map<String, List<String>> graph,
            String start,
            String target) {

        List<String> result = new ArrayList<String>();

        // graph、start 或 target 無效
        if (graph == null
                || start == null
                || target == null
                || !graph.containsKey(start)
                || !graph.containsKey(target)) {
            return result;
        }

        // 起點和終點相同
        if (start.equals(target)) {
            result.add(start);
            return result;
        }

        Queue<String> queue = new LinkedList<String>();
        Set<String> visited = new HashSet<String>();

        // 記錄每個 vertex 是從哪一個 vertex 走過來的
        Map<String, String> predecessor =
                new HashMap<String, String>();

        queue.offer(start);
        visited.add(start);

        boolean found = false;

        // 使用 BFS 找最短路徑
        while (!queue.isEmpty() && !found) {
            String current = queue.poll();

            List<String> neighbors = graph.get(current);

            // 沒有鄰居就繼續處理 Queue
            if (neighbors == null) {
                continue;
            }

            // 按照 adjacency List 原本的順序
            for (String next : neighbors) {
                // 只處理 graph 中存在且還沒走過的 vertex
                if (next != null
                        && graph.containsKey(next)
                        && !visited.contains(next)) {

                    visited.add(next);
                    predecessor.put(next, current);
                    queue.offer(next);

                    // BFS 第一次找到 target 就是最短距離
                    if (next.equals(target)) {
                        found = true;
                        break;
                    }
                }
            }
        }

        // 找不到 target
        if (!found) {
            return result;
        }

        // 從 target 使用 predecessor 往回建立路徑
        String current = target;

        while (current != null) {
            result.add(current);

            if (current.equals(start)) {
                break;
            }

            current = predecessor.get(current);
        }

        // 現在是 target -> start，所以反轉
        Collections.reverse(result);

        return result;
    }
}
