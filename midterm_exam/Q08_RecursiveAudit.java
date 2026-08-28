public class Q08_RecursiveAudit {

    // sumValid() 從 index 開始加總 0 到 100 的值
    public static int sumValid(int[] data, int index) {
        if (data == null) return 0;
        if (index < 0) index = 0;
        if (index >= data.length) return 0; // Base Case ->走出陣列範圍

        int current = data[index];
        int rest = sumValid(data, index + 1); // 遞迴呼叫
        
        return (current >= 0 && current <= 100) ? current + rest : rest;
    }

    // countOccurrences() 從 index 開始計算 target 次數
    public static int countOccurrences(int[] data, int index, int target) {
        if (data == null) return 0;
        if (index < 0) index = 0;
        if (index >= data.length) return 0; // Base Case

        int rest = countOccurrences(data, index + 1, target); // 遞迴呼叫
        return (data[index] == target) ? 1 + rest : rest;
    }

    // isPalindrome() 忽略大小寫，但不忽略空白或標點
    public static boolean isPalindrome(String text, int left, int right) {
        if (text == null) return false;
        
        // 迴文條件:空字串或長度為 0 的範圍
        if (text.isEmpty() && left == 0 && right == -1) return true;
        
        // 防止無效索引
        if (left < 0 || right >= text.length()) return false;
        
        // Base Case
        if (left >= right) return true;

        char cl = Character.toLowerCase(text.charAt(left));
        char cr = Character.toLowerCase(text.charAt(right));
        if (cl != cr) return false;

        // 遞迴呼叫
        return isPalindrome(text, left + 1, right - 1);
    }

}