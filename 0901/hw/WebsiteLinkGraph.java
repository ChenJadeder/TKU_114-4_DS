import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WebsiteLinkGraph {

    // page -> outgoing pages
    private Map<String, Set<String>> links;

    public WebsiteLinkGraph() {
        links = new HashMap<>();
    }

    public void addPage(String page) {
        links.putIfAbsent(page, new HashSet<>());
    }

    public boolean addLink(String from, String to) {
        // 兩個頁面都必須先存在
        if (!links.containsKey(from) || !links.containsKey(to)) {
            return false;
        }

        // Directed Graph，只建立 from -> to
        return links.get(from).add(to);
    }

    public Set<String> getOutgoing(String page) {
        if (!links.containsKey(page)) {
            return new HashSet<>();
        }

        // 回傳 copy，避免外面直接修改 adjacency list
        return new HashSet<>(links.get(page));
    }

    public int getIncomingCount(String page) {
        if (!links.containsKey(page)) {
            return 0;
        }

        int count = 0;

        // 檢查有哪些 vertex 指向 page
        for (Set<String> targets : links.values()) {
            if (targets.contains(page)) {
                count++;
            }
        }

        return count;
    }

    public Set<String> getNoIncomingPages() {
        Set<String> result = new HashSet<>();

        for (String page : links.keySet()) {
            if (getIncomingCount(page) == 0) {
                result.add(page);
            }
        }

        return result;
    }

    public Set<String> getNoOutgoingPages() {
        Set<String> result = new HashSet<>();

        for (Map.Entry<String, Set<String>> entry : links.entrySet()) {
            if (entry.getValue().isEmpty()) {
                result.add(entry.getKey());
            }
        }

        return result;
    }

    public static void main(String[] args) {
        WebsiteLinkGraph graph = new WebsiteLinkGraph();

        graph.addPage("Home");
        graph.addPage("About");
        graph.addPage("Products");
        graph.addPage("Contact");
        graph.addPage("OrphanPage");

        graph.addLink("Home", "About");
        graph.addLink("Home", "Products");
        graph.addLink("About", "Contact");
        graph.addLink("Products", "Contact");

        // Set 不允許相同 edge 重複加入
        boolean duplicate =
                graph.addLink("Home", "About");

        System.out.println(
                "Home 的 Outgoing Links: "
                + graph.getOutgoing("Home")
        );

        System.out.println(
                "Contact 的 Incoming Count: "
                + graph.getIncomingCount("Contact")
        );

        System.out.println(
                "無 Incoming 的頁面: "
                + graph.getNoIncomingPages()
        );

        System.out.println(
                "無 Outgoing 的頁面: "
                + graph.getNoOutgoingPages()
        );

        System.out.println(
                "重複 link 新增成功: " + duplicate
        );
    }
}
