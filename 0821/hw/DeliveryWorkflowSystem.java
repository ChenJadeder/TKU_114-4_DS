import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// 課後作業三：物流工作流程
//指定檔名：DeliveryWorkflowSystem.java

class Delivery {
    private final String id;
    private final String address;
    private boolean done;

    Delivery(String id, String address) {
        this.id = (id == null) ? "UNKNOWN" : id.trim();
        this.address = (address == null) ? "Unknown" : address.trim();
        this.done = false;
    }

    String getId() { return id; }
    void complete() { this.done = true; }
    void reopen()   { this.done = false; }

    @Override
    public String toString() {
        return id + " " + address + " done=" + done;
    }
}

public class DeliveryWorkflowSystem {
    private final Map<String, Delivery> byId = new LinkedHashMap<>();
    private final Deque<Delivery> waiting = new ArrayDeque<>();
    private final Deque<Delivery> completed = new ArrayDeque<>();
    private final List<String> logs = new ArrayList<>();

    boolean add(String id, String address) {
        if (id == null || id.trim().isEmpty()) return false;
        if (byId.containsKey(id)) {
            logs.add("dup id " + id);
            return false;
        }
        Delivery d = new Delivery(id, address);
        byId.put(id, d);
        waiting.offerLast(d);
        logs.add("add " + id);
        return true;
    }

    //  Queue保存配送
    String processNext() {
        Delivery d = waiting.pollFirst();
        if (d == null) {
            logs.add("processNext EMPTY");
            return "EMPTY";
        }
        d.complete();
        completed.push(d);
        logs.add("process " + d.getId());
        return d.toString();
    }

    // 撤銷最後完成，放回 waiting 前端
    String undoLastCompletion() {
        Delivery d = completed.pollFirst();
        if (d == null) {
            logs.add("undo EMPTY");
            return "EMPTY";
        }
        d.reopen();
        waiting.offerFirst(d);
        logs.add("undo " + d.getId());
        return d.toString();
    }

    Delivery findById(String id) {
        return byId.get(id);
    }

    void printSummary() {
        System.out.println("waiting=" + waiting);
        System.out.println("completed=" + completed);
        System.out.println("logs=" + logs);
    }

    public static void main(String[] args) {
        DeliveryWorkflowSystem sys = new DeliveryWorkflowSystem();
        sys.add("D101", "A St.");
        sys.add("D102", "B Rd.");
        sys.add("D101", "C Ave."); // dup
        System.out.println(sys.processNext());
        System.out.println(sys.processNext());
        System.out.println(sys.processNext());
        System.out.println(sys.undoLastCompletion());
        System.out.println("find D102=" + sys.findById("D102"));
        sys.printSummary();
    }
}