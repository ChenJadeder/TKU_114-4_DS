import java.util.*;

// Q12: 校園服務派送系統
 // 提供路徑規劃 (BFS) 與最高優先權可派送 Request
 
public class Q12_CampusDispatchSystem {

    //==== Request 紀錄 ====//
    public record Request(String id, String location, int priority, long sequence) {}

    //==== 內部類別與變數 ====//
    private final Map<String, Set<String>> graph;        // 鄰接表：地點 → 連接地點集合
    private final Map<String, Request> activeRequests;   // 以 ID 索引 Request
    private final PriorityQueue<Request> pendingQueue;   // 高優先權請求排程

    public Q12_CampusDispatchSystem() {
        this.graph = new HashMap<>();
        this.activeRequests = new HashMap<>();
        this.pendingQueue = new PriorityQueue<>(this::compareRequests);
    }

    private int compareRequests(Request a, Request b) {
        if (a.priority() != b.priority()) {
            return Integer.compare(a.priority(), b.priority());
        }
        return Long.compare(a.sequence(), b.sequence());
    }

    // 1：新增可到達地點 //
    public boolean addLocation(String location) {
        if (location == null || location.trim().isEmpty()) {
            return false;
        }
        String loc = location.trim();
        graph.putIfAbsent(loc, new HashSet<>());
        return true;
    }

    //法 2：新增道路（無向邊） //
    public boolean addRoad(String first, String second) {
        if (first == null || second == null) return false;
        String f = first.trim();
        String s = second.trim();
        if (f.isEmpty() || s.isEmpty()) return false;
        if (f.equals(s)) return false; // 不允許自環

        // 確保兩個地點存在於圖中
        addLocation(f);
        addLocation(s);

        // 新增雙向邊
        graph.get(f).add(s);
        graph.get(s).add(f);
        return true;
    }

    // 方 3：提交新 Request //
    public boolean submit(Request request) {
        if (request == null) return false;
        String reqId = request.id().trim();
        String loc = request.location().trim();

        // 校閱
        if (reqId.isEmpty() || loc.isEmpty() ||
            request.priority() < 0 || request.sequence() < 0) {
            return false;
        }

        // 檢查 Request ID 是否已存在
        if (activeRequests.containsKey(reqId)) {
            return false;
        }

        // 新增至 HashMap
        activeRequests.put(reqId, request);

        // 加入優先權佇列
        pendingQueue.offer(request);
        return true;
    }

    //方法 4：取得當前可由指定中心到達的最高優先 Request //
    public Request nextReachable(String serviceCenter) {
        String center = (serviceCenter == null) ? null : serviceCenter.trim();
        if (center == null || !graph.containsKey(center)) {
            return null; // 無效中心 → 回傳 null
        }

        // 複製隊列（盡量不改原佇列）
        PriorityQueue<Request> tempQueue = new PriorityQueue<>(this.pendingQueue);

        while (!tempQueue.isEmpty()) {
            Request req = tempQueue.poll();
            if (!isLocationReachable(center, req.location())) {
                continue; // 無法到達，跳過此 request
            }
            return req; // 找到可達且最高優先的
        }

        // 若隊列已空仍未找到可達 Request → 回傳 null
        return null;
    }

    //以 BFS 檢查兩點之間是否可達
     
    private boolean isLocationReachable(String start, String target) {
        if (start == null || target == null || !start.equals(target) &&
            !graph.containsKey(start) || !graph.containsKey(target)) {
            return false;
        }

        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            if (current.equals(target)) {
                return true;
            }
            for (String neighbor : graph.get(current)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        return false;
    }

    //法 5：計算起點到終點的最短路徑 //
    public List<String> route(String start, String target) {
        String s = (start == null) ? null : start.trim();
        String t = (target == null) ? null : target.trim();

        // 無效輸入或無法到達 → 回傳空路徑
        if (s == null || t == null || !graph.containsKey(s) || !graph.containsKey(t)) {
            return new ArrayList<>();
        }

        // BFS 尋找最短路徑
        Queue<List<String>> paths = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        paths.offer(new ArrayList<>(List.of(s)));
        visited.add(s);

        while (!paths.isEmpty()) {
            List<String> currentPath = paths.poll();
            String last = currentPath.get(currentPath.size() - 1);

            if (last.equals(t)) {
                return currentPath; // find target
            }

            for (String neighbor : graph.get(last)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    List<String> newPath = new ArrayList<>(currentPath);
                    newPath.add(neighbor);
                    paths.offer(newPath);
                }
            }
        }

        // 無路徑
        return new ArrayList<>();
    }

    // 6：查詢等候處理 Request 數目 //
    public int pendingCount() {
        return activeRequests.size(); // 有效 requests 數
    }
}
