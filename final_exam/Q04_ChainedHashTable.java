import java.util.ArrayList;
import java.util.List;

public class Q04_ChainedHashTable {

    // 用來存一組 key 和 value
    private static class Entry {
        int key;
        String value;

        Entry(int key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    // 每一個 bucket 都是一個 List
    private final List<List<Entry>> buckets;
    private int count;

    public Q04_ChainedHashTable(int bucketCount) {
        // bucket 數量不能是 0 或負數
        if (bucketCount <= 0) {
            throw new IllegalArgumentException(
                    "bucketCount must be greater than 0");
        }

        buckets = new ArrayList<List<Entry>>();

        // 建立所有 bucket
        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<Entry>());
        }

        count = 0;
    }

    // 計算 key 要放在哪一個 bucket
    private int getIndex(int key) {
        // floorMod 可以讓負數 key 也得到正確的 index
        return Math.floorMod(key, buckets.size());
    }

    // 新增 key 和 value
    public void put(int key, String value) {
        int index = getIndex(key);
        List<Entry> chain = buckets.get(index);

        // 如果 key 已經存在，只更新 value
        for (Entry entry : chain) {
            if (entry.key == key) {
                entry.value = value;
                return;
            }
        }

        // 不同 key 即使發生 collision 也保留
        chain.add(new Entry(key, value));
        count++;
    }

    // 根據 key 尋找 value
    public String get(int key) {
        int index = getIndex(key);
        List<Entry> chain = buckets.get(index);

        for (Entry entry : chain) {
            if (entry.key == key) {
                return entry.value;
            }
        }

        return null;
    }

    // 移除指定的 key
    public boolean remove(int key) {
        int index = getIndex(key);
        List<Entry> chain = buckets.get(index);

        for (int i = 0; i < chain.size(); i++) {
            if (chain.get(i).key == key) {
                chain.remove(i);
                count--;
                return true;
            }
        }

        // 沒找到就沒有移除
        return false;
    }

    // 回傳目前有多少筆資料
    public int size() {
        return count;
    }

    // 找出所有 bucket 中最長的 chain
    public int longestChain() {
        int longest = 0;

        for (List<Entry> chain : buckets) {
            if (chain.size() > longest) {
                longest = chain.size();
            }
        }

        return longest;
    }
}
