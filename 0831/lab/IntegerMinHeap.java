import java.util.ArrayList;
import java.util.NoSuchElementException;
// 課堂實作題二：Min Heap 完整操作
// 指定檔名：IntegerMinHeap.java
public class IntegerMinHeap {
    private ArrayList<Integer> heap = new ArrayList<>();

    public void add(int value) {
        // 先加到最後面，再往上調整
        heap.add(value);

        int index = heap.size() - 1;

        while (index > 0) {
            int parent = (index - 1) / 2;

            if (heap.get(index) < heap.get(parent)) {
                swap(index, parent);
                index = parent;
            } else {
                break;
            }
        }
    }

    public int peek() {
        if (heap.isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }

        return heap.get(0);
    }

    public int removeMin() {
        if (heap.isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }

        int min = heap.get(0);
        int last = heap.remove(heap.size() - 1);

        // 如果剛剛只有一個元素，remove 後就不用調整
        if (!heap.isEmpty()) {
            heap.set(0, last);

            int index = 0;

            while (true) {
                int left = index * 2 + 1;
                int right = index * 2 + 2;
                int smaller = index;

                if (left < heap.size()
                        && heap.get(left) < heap.get(smaller)) {
                    smaller = left;
                }

                if (right < heap.size()
                        && heap.get(right) < heap.get(smaller)) {
                    smaller = right;
                }

                if (smaller == index) {
                    break;
                }

                swap(index, smaller);
                index = smaller;
            }
        }

        return min;
    }

    public int size() {
        return heap.size();
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    private void swap(int a, int b) {
        int temp = heap.get(a);
        heap.set(a, heap.get(b));
        heap.set(b, temp);
    }

    public static void main(String[] args) {
        IntegerMinHeap heap = new IntegerMinHeap();

        int[] data = {25, 40, 10, 50, 30, 10};

        for (int value : data) {
            heap.add(value);
        }

        System.out.println("size = " + heap.size());
        System.out.println("peek = " + heap.peek());

        System.out.print("remove order: ");

        while (!heap.isEmpty()) {
            System.out.print(heap.removeMin());

            if (!heap.isEmpty()) {
                System.out.print(" ");
            }
        }

        System.out.println();
        System.out.println("empty = " + heap.isEmpty());

        // 額外確認空 Heap 的例外
        try {
            heap.peek();
        } catch (NoSuchElementException e) {
            System.out.println("peek exception: " + e.getClass().getSimpleName());
        }

        try {
            heap.removeMin();
        } catch (NoSuchElementException e) {
            System.out.println("remove exception: " + e.getClass().getSimpleName());
        }
    }
}
