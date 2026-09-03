import java.util.ArrayList;
import java.util.List;

public class Q03_MinHeapRemove {

    // 自己用 ArrayList 儲存 heap
    private ArrayList<Integer> heap;

    public Q03_MinHeapRemove(List<Integer> values) {
        heap = new ArrayList<Integer>();

        // 加入真實體
        if (values != null) {
            for (Integer value : values) {
                if (value != null) {
                    heap.add(value);
                }
            }
        }

        // bottom-up heapify
        // 從最後一個有子節點的位置開始往前整理
        for (int i = heap.size() / 2 - 1; i >= 0; i--) {
            bubbleDown(i);
        }
    }

    // 移除並回傳 heap 中最小的數字
    public Integer removeMin() {
        if (heap.size() == 0) {
            return null;
        }

        Integer min = heap.get(0);

        // 先取出最後一個元素
        Integer last = heap.remove(heap.size() - 1);

        // 如果移除後還有資料，把最後元素補到 root
        if (heap.size() > 0) {
            heap.set(0, last);
            bubbleDown(0);
        }

        return min;
    }

    // bubble-down，讓較大的父節點往下移
    private void bubbleDown(int index) {
        while (true) {
            int left = index * 2 + 1;
            int right = index * 2 + 2;
            int smallest = index;

            // 找父節點、左子節點中比較小的
            if (left < heap.size()
                    && heap.get(left) < heap.get(smallest)) {
                smallest = left;
            }

            // 再和右子節點比較
            if (right < heap.size()
                    && heap.get(right) < heap.get(smallest)) {
                smallest = right;
            }

            // index 已經是最小的，表示不用再往下交換
            if (smallest == index) {
                break;
            }

            // 交換兩個位置
            Integer temp = heap.get(index);
            heap.set(index, heap.get(smallest));
            heap.set(smallest, temp);

            index = smallest;
        }
    }

    
    public Integer peek() {
        if (heap.size() == 0) {
            return null;
        }

        return heap.get(0);
    }

    // 回傳目前元素數量
    public int size() {
        return heap.size();
    }

    // 避免外部直接修改內部，因此改用新Arraylist
    public List<Integer> snapshot() {
        return new ArrayList<Integer>(heap);
    }
}
