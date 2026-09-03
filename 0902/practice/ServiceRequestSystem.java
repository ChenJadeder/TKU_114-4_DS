import java.util.*;

public class ServiceRequestSystem {

    static class Request {
        int id;
        String description;
        int priority;

        Request(int id, String description, int priority) {
            this.id = id;
            this.description = description;
            this.priority = priority;
        }

        public String toString() {
            return "#" + id + " " + description
                    + " (priority=" + priority + ")";
        }
    }

    private final Map<Integer, Request> requests =
            new HashMap<>();

    private final PriorityQueue<Request> priorityQueue =
            new PriorityQueue<>(
                    Comparator.comparingInt(
                            (Request r) -> r.priority)
                            .reversed()
                            .thenComparingInt(r -> r.id)
            );

    public boolean addRequest(
            int id,
            String description,
            int priority) {

        if (requests.containsKey(id)) {
            return false;
        }

        Request request =
                new Request(id, description, priority);

        requests.put(id, request);
        priorityQueue.offer(request);

        return true;
    }

    public Request findById(int id) {
        return requests.get(id);
    }

    // 取出最高 priority，並同步移除 HashMap 資料
    public Request takeNext() {
        Request next = priorityQueue.poll();

        if (next != null) {
            requests.remove(next.id);
        }

        return next;
    }

    // 取消時必須同步更新兩種資料結構
    public boolean cancel(int id) {
        Request request = requests.remove(id);

        if (request == null) {
            return false;
        }

        priorityQueue.remove(request);
        return true;
    }

    public int size() {
        return requests.size();
    }

    public boolean isConsistent() {
        if (requests.size() != priorityQueue.size()) {
            return false;
        }

        for (Request request : priorityQueue) {
            if (requests.get(request.id) != request) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        ServiceRequestSystem system =
                new ServiceRequestSystem();

        system.addRequest(101, "Reset password", 2);
        system.addRequest(102, "Server offline", 10);
        system.addRequest(103, "Printer problem", 3);
        system.addRequest(104, "Network failure", 8);

        System.out.println("=== ID Lookup ===");
        System.out.println(system.findById(103));

        System.out.println("\n=== Next Request ===");
        System.out.println(system.takeNext());

        System.out.println("\n=== Cancel ===");
        System.out.println("Cancel 103: "
                + system.cancel(103));

        System.out.println("Find 103: "
                + system.findById(103));

        System.out.println("\n=== Missing ===");
        System.out.println("Cancel 999: "
                + system.cancel(999));

        System.out.println("\n=== Consistency ===");
        System.out.println(
                "Size: " + system.size());
        System.out.println(
                "Consistent: " + system.isConsistent());

        System.out.println("\n=== Empty ===");
        ServiceRequestSystem empty =
                new ServiceRequestSystem();

        System.out.println("Next: "
                + empty.takeNext());
        System.out.println("Size: "
                + empty.size());
    }
}
