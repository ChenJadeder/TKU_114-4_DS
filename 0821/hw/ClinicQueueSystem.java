import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

// 課後作業二：診所掛號系統
//指定檔名：ClinicQueueSystem.java 
class Patient {
    private final String id;
    private final String name;

    Patient(String id, String name) {
        this.id = (id == null) ? "UNKNOWN" : id.trim();
        this.name = (name == null) ? "Unknown" : name.trim();
    }

    String getId() { return id; }

    @Override
    public String toString() {
        return id + " " + name;
    }
}

public class ClinicQueueSystem {
    private final Deque<Patient> waiting = new ArrayDeque<>();
    private final List<Patient> completed = new ArrayList<>();

    void register(Patient p) {
        if (p == null) return;
        waiting.offerLast(p);
        printState("register " + p);
    }

    // 取消指定病歷號
    boolean cancel(String id) {
        if (id == null) return false;
        boolean removed = false;
        Deque<Patient> temp = new ArrayDeque<>();
        while (!waiting.isEmpty()) {
            Patient p = waiting.pollFirst();
            if (!removed && id.equals(p.getId())) {
                removed = true;
            } else {
                temp.offerLast(p);
            }
        }
        waiting.addAll(temp);
        printState("cancel " + id + " -> " + removed);
        return removed;
    }

    // 查看下一位
    String peekNext() {
        Patient p = waiting.peekFirst();
        return p == null ? "EMPTY" : p.toString();
    }

    // 叫號
    String callNext() {
        Patient p = waiting.pollFirst();
        if (p == null) {
            printState("callNext (EMPTY)");
            return "EMPTY";
        }
        completed.add(p);
        printState("callNext " + p);
        return p.toString();
    }
//當日完成清單
    void printState(String step) {
        System.out.println(step + " -> waiting=" + waiting + " | completed=" + completed);
    }

    public static void main(String[] args) {
        ClinicQueueSystem sys = new ClinicQueueSystem();
        sys.register(new Patient("P001", "Amy"));
        sys.register(new Patient("P002", "Ben"));
        sys.register(new Patient("P003", "Cara"));
        System.out.println("next=" + sys.peekNext());
        sys.callNext();
        sys.cancel("P999"); // 不存在
        sys.cancel("P003"); 
        sys.callNext();
        sys.callNext();
        sys.callNext(); // 空
    }
}