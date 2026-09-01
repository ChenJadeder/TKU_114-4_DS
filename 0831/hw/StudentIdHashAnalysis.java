import java.util.Arrays;
// 課後作業六：學號 Collision 分析
// 指定檔名：StudentIdHashAnalysis.java
public class StudentIdHashAnalysis {

    static class AnalysisResult {
        int bucketCount;
        int[] bucketSizes;
        int collisions;
        int maxChain;
        double averageChain;

        AnalysisResult(int bucketCount, int[] bucketSizes,
                       int collisions, int maxChain, double averageChain) {
            this.bucketCount = bucketCount;
            this.bucketSizes = bucketSizes;
            this.collisions = collisions;
            this.maxChain = maxChain;
            this.averageChain = averageChain;
        }
    }

    public static AnalysisResult analyze(int[] studentIds, int bucketCount) {
        if (bucketCount <= 0) {
            throw new IllegalArgumentException("bucketCount must be > 0");
        }

        int[] bucketSizes = new int[bucketCount];

        // 先算每個學號會掉到哪個 bucket
        for (int id : studentIds) {
            int index = Math.floorMod(id, bucketCount);
            bucketSizes[index]++;
        }

        int collisions = 0;
        int maxChain = 0;
        int nonEmptyBuckets = 0;

        for (int count : bucketSizes) {
            if (count > 0) {
                nonEmptyBuckets++;

                // 第一筆不是 collision，後面的才算
                collisions += count - 1;

                if (count > maxChain) {
                    maxChain = count;
                }
            }
        }

        double averageChain;

        if (nonEmptyBuckets == 0) {
            averageChain = 0.0;
        } else {
            averageChain =
                    (double) studentIds.length / nonEmptyBuckets;
        }

        return new AnalysisResult(
                bucketCount,
                bucketSizes,
                collisions,
                maxChain,
                averageChain
        );
    }

    public static void printResult(AnalysisResult result) {
        System.out.println("bucket count = " + result.bucketCount);

        for (int i = 0; i < result.bucketSizes.length; i++) {
            System.out.println(
                    "bucket " + i + " = " + result.bucketSizes[i]
            );
        }

        System.out.println("collisions = " + result.collisions);
        System.out.println("max chain = " + result.maxChain);
        System.out.printf("average chain = %.2f%n", result.averageChain);
    }

    private static void check(String testName, boolean condition) {
        if (condition) {
            System.out.println("[PASS] " + testName);
        } else {
            System.out.println("[FAIL] " + testName);
        }
    }

    public static void main(String[] args) {
        int[] studentIds = {
            1001, 1006, 1011, 1016,
            1021, 1026, 1031, 1037
        };

        System.out.println("=== bucket count 5 ===");
        AnalysisResult result5 = analyze(studentIds, 5);
        printResult(result5);

        System.out.println();

        System.out.println("=== bucket count 7 ===");
        AnalysisResult result7 = analyze(studentIds, 7);
        printResult(result7);

        System.out.println();
        System.out.println("=== tests ===");

        // bucket 5 的預期分布
        check(
            "bucket count 5 distribution",
            Arrays.equals(
                result5.bucketSizes,
                new int[]{0, 7, 1, 0, 0}
            )
        );

        check("bucket count 5 collisions",
                result5.collisions == 6);

        check("bucket count 5 max chain",
                result5.maxChain == 7);

        // 所有輸入都應該被統計到
        int total = 0;
        for (int count : result5.bucketSizes) {
            total += count;
        }

        check("all student ids counted",
                total == studentIds.length);

        // empty input 邊界測試
        AnalysisResult empty = analyze(new int[]{}, 5);

        check("empty collisions",
                empty.collisions == 0);

        check("empty max chain",
                empty.maxChain == 0);

        check("empty average chain",
                empty.averageChain == 0.0);

        // single input 邊界測試
        AnalysisResult single = analyze(new int[]{12345}, 5);

        check("single item collision",
                single.collisions == 0);

        check("single item max chain",
                single.maxChain == 1);

        // 無效 bucket count
        try {
            analyze(studentIds, 0);
            check("invalid bucket count", false);
        } catch (IllegalArgumentException e) {
            check("invalid bucket count", true);
        }
    }
}
