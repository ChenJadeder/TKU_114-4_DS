import java.util.ArrayList;
import java.util.List;

public class Q02_MinHeapInsert {

    // 用 ArrayList 存放 min heap 的資料
    private ArrayList<Integer> heap = new ArrayList<Integer>();

    // 加入新的數字
    public void add(int value) {
        // 先把新資料放到最後面
        heap.add(value);

        int index = heap.size() - 1;

        // bubble-up：和父節點比較
        while (index > 0) {
            int parent = (index - 1) / 2;

            // 如果父節點已經比較小，就不用交換了
            if (heap.get(parent) <= heap.get(index)) {
                break;
            }

            // 交換父節點和目前節點
            int temp = heap.get(parent);
            heap.set(parent, heap.get(index));
            heap.set(index, temp);

            
            index = parent;
        }
    }

    /
    public Integer peek() {
        if (heap.size() == 0) {
            return null;
        }

        return heap.get(0);
    }

    // 回傳目前 heap 有幾個元素
    public int size() {
        return heap.size();
    }

    // 回傳目前 heap 的內容
    // 要建立新的 List，避免外面直接修改原本的 heap
    public List<Integer> snapshot() {
        return new ArrayList<Integer>(heap);
    }

    // 檢查規則
    public boolean isValidMinHeap() {
        for (int i = 0; i < heap.size(); i++) {
            int left = i * 2 + 1;
            int right = i * 2 + 2;

            // 父節點不能比左子節點大
            if (left < heap.size() && heap.get(i) > heap.get(left)) {
                return false;
            }

            // 父節點不能比右子節點大
            if (right < heap.size() && heap.get(i) > heap.get(right)) {
                return false;
            }
        }

        return true;
    }
}
