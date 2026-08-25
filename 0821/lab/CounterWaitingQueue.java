import java.util.ArrayDeque;
import java.util.Deque;

// 課堂實作題三：櫃台等候 Queue
//指定檔名：CounterWaitingQueue.java

class Customer {
    private final String id;
    private final String name;

    Customer(String id, String name) {
        this.id = (id == null) ? "UNKNOWN" : id.trim();
        this.name = (name == null) ? "Unknown" : name.trim(); //預設下一位顧客
    }

    @Override
    public String toString() {
        return id + " " + name;
    }
}

public class CounterWaitingQueue {
    private final Deque<Customer> queue = new ArrayDeque<>();

    void join(Customer c) {
        if (c == null) return;
        queue.offerLast(c);
    }

    String next() {
        Customer c = queue.peekFirst();
        return c == null ? "EMPTY" : c.toString();
    }

    String serve() {
        Customer c = queue.pollFirst();
        return c == null ? "EMPTY" : c.toString();
    }

    int waitingCount() {
        return queue.size();
    }

    public static void main(String[] args) {
        CounterWaitingQueue q = new CounterWaitingQueue();
        q.join(new Customer("C101", "Amy"));
        q.join(new Customer("C102", "Ben"));
        q.join(new Customer("C103", "Cara")); // 注意櫃檯等待

        System.out.println("next=" + q.next());
        System.out.println("serve=" + q.serve());
        System.out.println("serve=" + q.serve());
        System.out.println("count=" + q.waitingCount());
        System.out.println("serve=" + q.serve());
        System.out.println("serve(empty)=" + q.serve()); //了解下個服務與等待對象
    }
}