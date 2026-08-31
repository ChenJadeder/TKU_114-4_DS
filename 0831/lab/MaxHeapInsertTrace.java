import java.util.ArrayList;
//課堂實作題一：Max Heap Insert Trace
//指定檔名：MaxHeapInsertTrace.java

public class MaxHeapInsertTrace {
    private ArrayList<Integer> heap = new ArrayList<>();

    public void add(int value) {
        // 新的值先放最後面，才不會破壞 complete binary tree
        heap.add(value);

        int index = heap.size() - 1;

        // Max Heap: child 比 parent 大的話就往上交換
        while (index > 0) {
            int parent = (index - 1) / 2;

            if (heap.get(index) > heap.get(parent)) {
                int temp = heap.get(index);
                heap.set(index, heap.get(parent));
                heap.set(parent, temp);

                index = parent;
            } else {
                break;
            }
        }
    }

    public int peekMax() {
        // Max Heap 最大值一定在 index 0
        return heap.get(0);
    }

    public ArrayList<Integer> snapshot() {
        // 回傳 copy，避免外面直接修改 heap
        return new ArrayList<>(heap);
    }

    public static void main(String[] args) {
        MaxHeapInsertTrace maxHeap = new MaxHeapInsertTrace();
        int[] data = {25, 40, 10, 50, 30, 50};

        for (int value : data) {
            maxHeap.add(value);
            System.out.println(maxHeap.snapshot());
        }

        System.out.println("root = " + maxHeap.peekMax());
    }
}
