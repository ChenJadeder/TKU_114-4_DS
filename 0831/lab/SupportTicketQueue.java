import java.util.PriorityQueue;
import java.util.Comparator;
// 課堂實作題三：工作排程
// 指定檔名：SupportTicketQueue.java
public class SupportTicketQueue {

    static class Ticket {
        String id;
        int severity;
        int createdOrder;

        Ticket(String id, int severity, int createdOrder) {
            this.id = id;
            this.severity = severity;
            this.createdOrder = createdOrder;
        }

        @Override
        public String toString() {
            return id + "|" + severity + "|" + createdOrder;
        }
    }

    public static void main(String[] args) {
        PriorityQueue<Ticket> queue =
            new PriorityQueue<>(new Comparator<Ticket>() {
                @Override
                public int compare(Ticket a, Ticket b) {
                    // severity 大的先
                    if (a.severity != b.severity) {
                        return Integer.compare(b.severity, a.severity);
                    }

                    // severity 一樣，建立比較早的先
                    return Integer.compare(a.createdOrder, b.createdOrder);
                }
            });

        queue.offer(new Ticket("T1", 2, 0));
        queue.offer(new Ticket("T2", 5, 1));
        queue.offer(new Ticket("T3", 3, 2));
        queue.offer(new Ticket("T4", 5, 3));
        queue.offer(new Ticket("T5", 3, 4));

        // 要用 poll 才能得到完整的 priority 順序
        while (!queue.isEmpty()) {
            System.out.println(queue.poll());
        }
    }
}
