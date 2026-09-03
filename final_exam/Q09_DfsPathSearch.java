// 第 9 題：DFS 與 Reachability
//dfs() 使用 recursion 進行深度優先搜尋，DFS 依照 adjacency List 。


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Q09_DfsPathSearch {

    // 執行 DFS，回傳走訪順序
    public static List<String> dfs(
            Map<String, List<String>> graph, String start) {

        List<String> result = new ArrayList<String>();

        // graph 或 start 無效時回傳 empty List
        if (graph == null || start == null
                || !graph.containsKey(start)) {
            return result;
        }

        Set<String> visited = new HashSet<String>();

        // 使用遞迴開始 DFS
        dfsVisit(graph, start, visited, result);

        return result;
    }

    // DFS 的遞迴 helper method
    private static void dfsVisit(
            Map<String, List<String>> graph,
            String current,
            Set<String> visited,
            List<String> result) {

        visited.add(current);
        result.add(current);

        List<String> neighbors = graph.get(current);

        // adjacency List 是 null 時就沒有下一個 vertex
        if (neighbors == null) {
            return;
        }

        // 按照 adjacency List 原本的順序走訪
        for (String next : neighbors) {
            if (next != null
                    && graph.containsKey(next)
                    && !visited.contains(next)) {

                dfsVisit(graph, next, visited, result);
            }
        }
    }

    // 判斷 target 是否可以從 start 到達
    public static boolean reachable(
            Map<String, List<String>> graph,
            String start,
            String target) {

        // start 和 target 都必須存在
        if (graph == null
                || start == null
                || target == null
                || !graph.containsKey(start)
                || !graph.containsKey(target)) {
            return false;
        }

        // 題目規定：兩個存在而且相同時回傳 true
        if (start.equals(target)) {
            return true;
        }

        Set<String> visited = new HashSet<String>();

        // 使用遞迴 DFS 尋找 target
        return searchTarget(graph, start, target, visited);
    }

    // reachable 使用的遞迴 helper method
    private static boolean searchTarget(
            Map<String, List<String>> graph,
            String current,
            String target,
            Set<String> visited) {

        // 目前 vertex 就是目標
        if (current.equals(target)) {
            return true;
        }

        visited.add(current);

        List<String> neighbors = graph.get(current);

        if (neighbors == null) {
            return false;
        }

        for (String next : neighbors) {
            if (next != null
                    && graph.containsKey(next)
                    && !visited.contains(next)) {

                if (searchTarget(
                        graph, next, target, visited)) {
                    return true;
                }
            }
        }

        return false;
    }
}
