//課堂實作題一：遞迴數位統計
//指定檔名：RecursiveDigitReport.java
//核心計算不得使用 loop 或轉成 String。
public class RecursiveDigitReport {
    public static int digitSum(int number) {
        number = Math.abs(number);
        if (number < 10) {
            return number;
        }
        return (number % 10) + digitSum(number / 10);
    }

    public static int digitCount(int number) {
        number = Math.abs(number);
        if (number < 10) {
            return 1;
        }
        return 1 + digitCount(number / 10);
    }

    public static int countDigit(int number, int target) {
        number = Math.abs(number);
        target = Math.abs(target);
        if (number < 10) {
            return (number == target) ? 1 : 0;
        }
        int currentMatch = (number % 10 == target) ? 1 : 0;
        return currentMatch + countDigit(number / 10, target);
    }

    public static void main(String[] args) {
        int[] testCases = {50205, 0, -731};
        for (int num : testCases) {
            System.out.println("Number: " + num);
            System.out.println("  digitSum: " + digitSum(num));
            System.out.println("  digitCount: " + digitCount(num));
            System.out.println("  countDigit('0'): " + countDigit(num, 0));
            System.out.println("  countDigit('5'): " + countDigit(num, 5));
        }
    }
}