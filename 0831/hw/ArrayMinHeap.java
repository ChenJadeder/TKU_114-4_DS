import java.util.Arrays;
import java.util.NoSuchElementException;
// 課後作業三：可調整容量 Min Heap
// 指定檔名：ArrayMinHeap.java
public class ArrayMinHeap {
    private int[] heap;
    private int size;

    public ArrayMinHeap() {
        heap = new int[4];
        size = 0;
    }

    public void add(int value) {
        // 陣列滿了就擴充成兩倍
        if (size == heap.length) {
            int[] newHeap = new int[heap.length * 2];

            for (int i = 0; i < heap.length; i++) {
                newHeap[i] = heap[i];
            }

            heap = newHeap;
        }

        heap[size] = value;
        int index = size;
        size++;

        // bubble-up
        while (index > 0) {
            int parent = (index - 1) / 2;

            if (heap[index] < heap[parent]) {
                swap(index, parent);
                index = parent;
            } else {
                break;
            }
        }
    }

    public int peek() {
        if (size == 0) {
            throw new NoSuchElementException("Heap is empty");
        }

        return heap[0];
    }

    public int remove() {
        if (size == 0) {
            throw new NoSuchElementException("Heap is empty");
        }

        int min = heap[0];

        heap[0] = heap[size - 1];
        size--;

        int index = 0;

        // bubble-down
        while (true) {
            int left = index * 2 + 1;
            int right = index * 2 + 2;
            int smaller = index;

            if (left < size && heap[left] < heap[smaller]) {
                smaller = left;
            }

            if (right < size && heap[right] < heap[smaller]) {
                smaller = right;
            }

            if (smaller == index) {
                break;
            }

            swap(index, smaller);
            index = smaller;
        }

        return min;
    }

    public int[] snapshot() {
        // 只回傳目前真的有資料的部分
        return Arrays.copyOf(heap, size);
    }

    private void swap(int a, int b) {
        int temp = heap[a];
        heap[a] = heap[b];
        heap[b] = temp;
    }

    public static void main(String[] args) {
        ArrayMinHeap heap = new ArrayMinHeap();

        // 20 筆，初始容量只有 4，所以一定會測到擴充
        int[] data = {
            42, 18, 7, 33, 25,
            60, 3, 15, 50, 12,
            8, 45, 1, 30, 22,
            70, 6, 17, 40, 10
        };

        for (int value : data) {
            heap.add(value);
        }

        System.out.println("after add:");
        System.out.println(Arrays.toString(heap.snapshot()));
        System.out.println("peek = " + heap.peek());

        System.out.println("remove order:");

        while (heap.snapshot().length > 0) {
            System.out.print(heap.remove());

            if (heap.snapshot().length > 0) {
                System.out.print(" ");
            }
        }

        System.out.println();
    }
}
