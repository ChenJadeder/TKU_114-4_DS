import java.util.Arrays;

class CircularIntQueue {
    private final int[] data;
    private int front;
    private int rear;
    private int size;

    CircularIntQueue(int capacity) {
        data = new int[Math.max(1, capacity)];
    }

    boolean enqueue(int value) {
        if (isFull()) return false;
        data[rear] = value;
        rear = (rear + 1) % data.length;
        size++;
        return true;
    }

    Integer dequeue() {
        if (isEmpty()) return null;
        int val = data[front];
        data[front] = 0;
        front = (front + 1) % data.length;
        size--;
        return val;
    }

    Integer peek() {
        return isEmpty() ? null : data[front];
    }

    boolean isEmpty() {
        return size == 0;
    }

    boolean isFull() {
        return size == data.length;
    }

    // 清空佇列
    void clear() {
        for (int i = 0; i < data.length; i++) data[i] = 0;
        front = 0;
        rear  = 0;
        size  = 0;
    }

    void printState() {
        System.out.println(Arrays.toString(data)
                + " front=" + front + " rear=" + rear + " size=" + size);
    }
}

public class CircularArrayQueueDemo {
    public static void main(String[] args) {
        CircularIntQueue queue = new CircularIntQueue(3);
        queue.enqueue(10);
        queue.enqueue(20);
        queue.printState();

        System.out.println("dequeue=" + queue.dequeue());
        queue.enqueue(30);
        queue.enqueue(40);
        queue.printState();

        System.out.println("full=" + queue.isFull());
        System.out.println("enqueue 50=" + queue.enqueue(50));
        System.out.println("peek=" + queue.peek());

        queue.clear();
        queue.printState(); // 全清 0、front/rear/size=0
        System.out.println("dequeue after clear=" + queue.dequeue()); // null
        System.out.println("peek after clear=" + queue.peek());       // null
    }
}
