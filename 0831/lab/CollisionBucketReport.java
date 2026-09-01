import java.util.ArrayList;
//課堂實作題六：Collision Bucket Report
//指定檔名：`CollisionBucketReport.java`
public class CollisionBucketReport {

    private ArrayList<Integer>[] buckets;
    private int collisions;
    private int longestChain;

    @SuppressWarnings("unchecked")
    public CollisionBucketReport(int bucketCount) {
        if (bucketCount <= 0) {
            throw new IllegalArgumentException("bucketCount must be > 0");
        }

        buckets = (ArrayList<Integer>[]) new ArrayList[bucketCount];

        for (int i = 0; i < bucketCount; i++) {
            buckets[i] = new ArrayList<>();
        }

        collisions = 0;
        longestChain = 0;
    }

    public void add(int key) {
        // floorMod 可以讓負數 key 也得到合法 index
        int index = Math.floorMod(key, buckets.length);

        // bucket 原本已經有資料，這次加入就算 collision
        if (!buckets[index].isEmpty()) {
            collisions++;
        }

        // 重複 key 也保留
        buckets[index].add(key);

        if (buckets[index].size() > longestChain) {
            longestChain = buckets[index].size();
        }
    }

    public void addAll(int[] keys) {
        for (int key : keys) {
            add(key);
        }
    }

    public void printReport() {
        for (int i = 0; i < buckets.length; i++) {
            System.out.println("bucket " + i + ": " + buckets[i]);
        }

        System.out.println("collisions = " + collisions);
        System.out.println("longest chain = " + longestChain);
    }

    public static void main(String[] args) {
        int[] keys = {
            1, 6, 11, -4, 3, 8, 1
        };

        CollisionBucketReport report =
                new CollisionBucketReport(5);

        report.addAll(keys);
        report.printReport();

        System.out.println();

        // empty input
        System.out.println("empty input:");

        CollisionBucketReport empty =
                new CollisionBucketReport(5);

        empty.addAll(new int[]{});
        empty.printReport();
    }
}
