// 課堂實作題一：可擴充 Hash Table
// 指定檔名：`ResizableStringMap.java`
import java.util.ArrayList;

public class ResizableStringMap {

    static class Entry {
        String key;
        String value;

        Entry(String key, String value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }
    }

    private ArrayList<Entry>[] buckets;
    private int size;

    @SuppressWarnings("unchecked")
    public ResizableStringMap(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("capacity must be > 0");
        }

        buckets = (ArrayList<Entry>[]) new ArrayList[initialCapacity];

        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new ArrayList<>();
        }

        size = 0;
    }

    private int getBucketIndex(String key) {
        return Math.floorMod(key.hashCode(), buckets.length);
    }

    public void put(String key, String value) {
        int index = getBucketIndex(key);

        // key 已存在就更新，不算新的 entry
        for (Entry entry : buckets[index]) {
            if (entry.key.equals(key)) {
                entry.value = value;
                return;
            }
        }

        buckets[index].add(new Entry(key, value));
        size++;

        if (loadFactor() > 0.75) {
            resize();
        }
    }

    public String get(String key) {
        int index = getBucketIndex(key);

        for (Entry entry : buckets[index]) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }

        return null;
    }

    public int size() {
        return size;
    }

    public double loadFactor() {
        return (double) size / buckets.length;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        ArrayList<Entry>[] oldBuckets = buckets;

        int newCapacity = oldBuckets.length * 2 + 1;

        buckets = (ArrayList<Entry>[]) new ArrayList[newCapacity];

        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new ArrayList<>();
        }

        // bucket 數量變了，所以每個 key 都要重新算 index
        for (ArrayList<Entry> bucket : oldBuckets) {
            for (Entry entry : bucket) {
                int newIndex = getBucketIndex(entry.key);
                buckets[newIndex].add(entry);
            }
        }
    }

    public void bucketReport() {
        for (int i = 0; i < buckets.length; i++) {
            System.out.println("bucket " + i + ": " + buckets[i]);
        }
    }

    public static void main(String[] args) {
        ResizableStringMap map = new ResizableStringMap(3);

        map.put("A", "Apple");
        map.put("B", "Banana");

        System.out.println("before resize:");
        System.out.println("size = " + map.size());
        System.out.printf("load factor = %.2f%n", map.loadFactor());
        map.bucketReport();

        // 第三個新 key 加入後 3 / 3 > 0.75
        // bucket count 應從 3 變成 7
        map.put("C", "Cat");

        System.out.println();
        System.out.println("after resize:");
        System.out.println("size = " + map.size());
        System.out.printf("load factor = %.2f%n", map.loadFactor());
        map.bucketReport();

        // 確認 rehash 後仍然查得到舊資料
        System.out.println();
        System.out.println("get A = " + map.get("A"));
        System.out.println("get B = " + map.get("B"));
        System.out.println("get C = " + map.get("C"));

        // 相同 key 只更新
        map.put("B", "Blueberry");

        System.out.println("updated B = " + map.get("B"));
        System.out.println("size after update = " + map.size());
    }
}
