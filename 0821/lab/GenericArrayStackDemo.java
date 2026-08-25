// 課堂實作題四：固定容量 Generic Stack
//指定檔名：GenericArrayStackDemo.java

class ArrayStack<T> {
    private final Object[] data;
    private int size;

    ArrayStack(int capacity) {
        if (capacity <= 0) capacity = 1;
        this.data = new Object[capacity];
        this.size = 0;
    }

    boolean push(T value) {
        if (value == null) return false;
        if (size == data.length) return false; // if fill
        data[size] = value;
        size++;
        return true;
    }

    @SuppressWarnings("unchecked")
    T pop() {
        if (size == 0) return null;
        size--;
        T v = (T) data[size];
        data[size] = null; // release 
        return v;
    }

    @SuppressWarnings("unchecked")
    T peek() {
        if (size == 0) return null;
        return (T) data[size - 1];
    }

    boolean isEmpty() {
        return size == 0;
    }

    boolean isFull() {
        return size == data.length;
    }

    int size() {
        return size;
    }
}

public class GenericArrayStackDemo {
    public static void main(String[] args) {
        ArrayStack<String> ss = new ArrayStack<>(2);
        System.out.println("push A=" + ss.push("A"));
        System.out.println("push B=" + ss.push("B"));
        System.out.println("push C=" + ss.push("C")); // false
        System.out.println("peek=" + ss.peek());
        System.out.println("pop=" + ss.pop());
        System.out.println("pop=" + ss.pop());
        System.out.println("pop(empty)=" + ss.pop());

        ArrayStack<Integer> si = new ArrayStack<>(3);
        si.push(10);
        si.push(20);
        si.push(30);
        System.out.println("full=" + si.isFull());
        System.out.println("pop=" + si.pop());
        System.out.println("peek=" + si.peek());
    }
}