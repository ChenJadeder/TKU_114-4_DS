// 課堂實作題一：Recursive Call Report
// 指定檔名：RecursiveCallReport.java
public class RecursiveCallReport {
    static int sum(int[] data, int index) {
        if (index == data.length) {
            System.out.println("index=" + index + ", base case, return=0");
            return 0;
        }

        int currentValue = data[index];
        System.out.println("index=" + index + ", value=" + currentValue);

        int recursiveResult = sum(data, index + 1);
        int result = currentValue + recursiveResult;

        System.out.println("index=" + index
                + ", current=" + currentValue
                + ", recursiveResult=" + recursiveResult
                + ", return=" + result);

        return result;
    }

    private static void runCase(String name, int[] data) {
        System.out.println("=== " + name + " ===");
        int result = sum(data, 0);
        System.out.println("sum=" + result);
        System.out.println();
    }

    public static void main(String[] args) {
        runCase("normal", new int[]{4, 7, 2, 9});
        runCase("single", new int[]{5});
        runCase("empty", new int[]{});
    }
}
