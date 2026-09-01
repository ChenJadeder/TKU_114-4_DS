// 課堂實作題五：Heap Validator
// 指定檔名：HeapPropertyValidator.java
import java.util.ArrayList;

public class IntegerStringHashTable {

    static class Entry {
        int key;
        String value;

        Entry(int key, String value) {
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
    public IntegerStringHashTable(int capacity) {
        buckets = (ArrayList<Entry>[]) new ArrayList[capacity];

        for (int i = 0; i < capacity; i++) {
            buckets[i] = new ArrayList<>();
        }

        size = 0;
    }

    private int getBucketIndex(int key) {
        return Math.floorMod(key, buckets.length);
    }

    public void put(int key, String value) {
        int index = getBucketIndex(key);

        // key 只更新
        for (Entry entry : buckets[index]) {
            if (entry.key == key) {
                entry.value = value;
                return;
            }
        }

        buckets[index].add(new Entry(key, value));
        size++;
    }

    public String get(int key) {
        int index = getBucketIndex(key);

        for (Entry entry : buckets[index]) {
            if (entry.key == key) {
                return entry.value;
            }
        }

        return null;
    }

    public boolean containsKey(int key) {
        int index = getBucketIndex(key);

        for (Entry entry : buckets[index]) {
            if (entry.key == key) {
                return true;
            }
        }

        return false;
    }

    public String remove(int key) {
        int index = getBucketIndex(key);

        for (int i = 0; i < buckets[index].size(); i++) {
            Entry entry = buckets[index].get(i);

            if (entry.key == key) {
                buckets[index].remove(i);
                size--;
                return entry.value;
            }
        }

        return null;
    }

    public int size() {
        return size;
    }

    public void bucketReport() {
        for (int i = 0; i < buckets.length; i++) {
            System.out.print("bucket " + i + ": ");

            for (Entry entry : buckets[i]) {
                System.out.print("[" + entry + "] ");
            }

            System.out.println();
        }
    }

    public static void main(String[] args) {
        IntegerStringHashTable table =
                new IntegerStringHashTable(5);

        // 1, 6, 11 都會進入 bucket 1
        table.put(1, "Apple");
        table.put(6, "Banana");
        table.put(11, "Cat");
        table.put(3, "Dog");

        System.out.println("size = " + table.size());
        System.out.println("get 6 = " + table.get(6));
        System.out.println("contains 11 = " + table.containsKey(11));

        // 相同 key 應該更新,並放入不同bucket
        table.put(6, "Blueberry");
        System.out.println("updated 6 = " + table.get(6));
        System.out.println("size after update = " + table.size());

        System.out.println("remove 1 = " + table.remove(1));
        System.out.println("size after remove = " + table.size());

        // 額外確認負數 key
        table.put(-4, "Negative");

        System.out.println("bucket report:");
        table.bucketReport();
    }
}
