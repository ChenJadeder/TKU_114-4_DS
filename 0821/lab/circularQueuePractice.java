import java.util.Arrays;

//課堂實作題六：Circular queue 狀態追蹤
//指定檔名：CircularQueuePractice.java

class CircularQueue<T> {
    private final Object[] data;
    private int front;
    private int rear;
    private int size;

    CircularQueue(int capacity) {
        if (capacity <= 0) capacity = 1;
        data = new Object[capacity];
        front = 0;
        rear = 0;
        size = 0;
    }

    boolean enqueue(T value) {
        if (isFull()) return false;
        data[rear] = value;
        rear = (rear + 1) % data.length;
        size++;
        return true;
    }

    @SuppressWarnings("unchecked")
    T dequeue() {
        if (isEmpty()) return null;
        T v = (T) data[front];
        data[front] = null;
        front = (front + 1) % data.length;
        size--;
        return v;
    }

    boolean isEmpty() { return size == 0; }
    boolean isFull()  { return size == data.length; }

    void printState() {
        System.out.println(Arrays.toString(data) + " front=" + front + " rear=" + rear + " size=" + size);
    }
}

public class CircularQueuePractice {
    public static void main(String[] args) {
        CircularQueue<String> q = new CircularQueue<>(4);

        // 序列戰爭
        q.enqueue("A"); q.printState();
        q.enqueue("B"); q.printState();
        q.enqueue("C"); q.printState();

        System.out.println("dequeue=" + q.dequeue()); q.printState();
        System.out.println("dequeue=" + q.dequeue()); q.printState();

        q.enqueue("D"); q.printState();
        q.enqueue("E"); q.printState();
        q.enqueue("F"); q.printState(); // if full ,then enqueue may fail（容量4以上可能會滿）

        System.out.println("dequeue=" + q.dequeue()); q.printState();
        System.out.println("enqueue G=" + q.enqueue("G")); q.printState();

        // FIFO to take all
        while (!q.isEmpty()) {
            System.out.println("out=" + q.dequeue());
        }
    }
}