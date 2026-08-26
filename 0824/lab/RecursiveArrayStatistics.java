//課堂實作題二：遞迴陣列統計
//指定檔名：RecursiveArrayStatistics.java

public class RecursiveArrayStatistics {
    public static int maximum(int[] values) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("Array cannot be null or empty");
        }
        return maxHelper(values, 0);
    }

    private static int maxHelper(int[] values, int index) {
        if (index == values.length - 1) {
            return values[index];
        }
        return Math.max(values[index], maxHelper(values, index + 1));
    }

    public static int minimum(int[] values) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("Array cannot be null or empty");
        }
        return minHelper(values, 0);
    }

    private static int minHelper(int[] values, int index) {
        if (index == values.length - 1) {
            return values[index];
        }
        return Math.min(values[index], minHelper(values, index + 1));
    }

    public static int countAbove(int[] values, int threshold) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("Array cannot be null or empty");
        }
        return countAboveHelper(values, threshold, 0);
    }

    private static int countAboveHelper(int[] values, int threshold, int index) {
        if (index == values.length) {
            return 0;
        }
        int match = values[index] > threshold ? 1 : 0;
        return match + countAboveHelper(values, threshold, index + 1);
    }

    public static void main(String[] args) {
        int[] data = {12, 45, 7, 23, 89, 34};
        System.out.println("Max: " + maximum(data));
        System.out.println("Min: " + minimum(data));
        System.out.println("Count (> 30): " + countAbove(data, 30));
    }
}