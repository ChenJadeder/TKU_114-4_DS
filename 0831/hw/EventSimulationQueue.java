import java.util.PriorityQueue;
import java.util.Comparator;
// 課後作業二：活動事件模擬器
// 指定檔名：EventSimulationQueue.java
public class EventSimulationQueue {

    // 每個事件需要記錄的基本資料
    static class Event {
        int time;
        String type;
        int sequence;

        Event(int time, String type, int sequence) {
            this.time = time;
            this.type = type;
            this.sequence = sequence;
        }

        @Override
        public String toString() {
            return "time=" + time + ", type=" + type + ", sequence=" + sequence;
        }
    }

    private PriorityQueue<Event> queue;

    public EventSimulationQueue() {
        queue = new PriorityQueue<>(new Comparator<Event>() {
            @Override
            public int compare(Event a, Event b) {
                // 先看時間，時間一樣才用 sequence 決定
                if (a.time != b.time) {
                    return Integer.compare(a.time, b.time);
                }

                return Integer.compare(a.sequence, b.sequence);
            }
        });
    }

    public void addEvent(int time, String type, int sequence) {
        queue.offer(new Event(time, type, sequence));
    }

    public boolean cancelEvent(int sequence) {
        Event target = null;

        // 這裡只是找事件，不使用 PriorityQueue 的遍歷順序
        for (Event event : queue) {
            if (event.sequence == sequence) {
                target = event;
                break;
            }
        }

        if (target != null) {
            queue.remove(target);
            return true;
        }

        return false;
    }

    public void run() {
        System.out.println("Execution log:");

        // 照 priority 執行
        while (!queue.isEmpty()) {
            Event event = queue.poll();
            System.out.println(event);
        }
    }

    public static void main(String[] args) {
        EventSimulationQueue simulator = new EventSimulationQueue();

        // 測試一般時間和相同時間的事件
        simulator.addEvent(10, "Login", 0);
        simulator.addEvent(5, "Message", 1);
        simulator.addEvent(10, "Logout", 2);
        simulator.addEvent(5, "Backup", 3);
        simulator.addEvent(20, "Shutdown", 4);

        // sequence 2 在執行前取消
        boolean canceled = simulator.cancelEvent(2);
        System.out.println("Cancel sequence 2: " + canceled);

        simulator.run();
    }
}
