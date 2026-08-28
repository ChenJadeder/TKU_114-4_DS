import java.util.ArrayList;
import java.util.List;

public class Q05_BoundedBox<T extends Comparable<T>> {
    private final int capacity;
    private final List<T> items;

    // 1單元以上才是正常
    public Q05_BoundedBox(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("capacity 必須大於等於 1");
        }
        this.capacity = capacity;
        this.items = new ArrayList<>();
    }

    // 但是拒絕overlord和null資料
    public boolean add(T value) {
        if (value == null || isFull()) {
            return false;
        }
        items.add(value);
        return true;
    }

    public int size() {
        return items.size();
    }

    public boolean isFull() {
        return items.size() >= capacity;
    }

    // find min
    public T minimum() {
        if (items.isEmpty()) {
            return null;
        }
        T min = items.get(0);
        for (int i = 1; i < items.size(); i++) {
            T cur = items.get(i);
            if (cur.compareTo(min) < 0) {
                min = cur;
            }
        }
        return min;
    }

    // find max
    public T maximum() {
        if (items.isEmpty()) {
            return null;
        }
        T max = items.get(0);
        for (int i = 1; i < items.size(); i++) {
            T cur = items.get(i);
            if (cur.compareTo(max) > 0) {
                max = cur;
            }
        }
        return max;
    }

    //  > threshold 的數量
    public int countGreaterThan(T threshold) {
        if (threshold == null) {
            return 0;
        }
        int count = 0;
        for (T v : items) {
            if (v.compareTo(threshold) > 0) {
                count++;
            }
        }
        return count;
    }

    // 回傳Snapchat
    public List<T> snapshot() {
        return new ArrayList<>(items);
    }
//A14997777
}