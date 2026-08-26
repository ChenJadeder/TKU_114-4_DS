//課後作業一：遞迴字串工具
//指定檔名：RecursiveTextTools.java
public class RecursiveTextTools {

    public static String reverse(String text) {
        if (text == null || text.length() <= 1) {
            return text;
        }
        return reverse(text.substring(1)) + text.charAt(0);
    }

    public static boolean isPalindrome(String text) {
        if (text == null) {
            return false;
        }
        // 忽略空白與非英文字母，並統一轉小寫
        String cleaned = text.replaceAll("[^a-zA-Z]", "").toLowerCase();
        return checkPalindrome(cleaned, 0, cleaned.length() - 1);
    }

    private static boolean checkPalindrome(String text, int left, int right) {
        if (left >= right) {
            return true; // 指標重疊或交錯
        }
        if (text.charAt(left) != text.charAt(right)) {
            return false;
        }
        return checkPalindrome(text, left + 1, right - 1);
    }

    public static int countCharacter(String text, char target) {
        if (text == null || text.isEmpty()) {
            return 0;
        }
        int count = (text.charAt(0) == target) ? 1 : 0;
        return count + countCharacter(text.substring(1), target);
    }

    public static void main(String[] args) {
        String[] testStrings = {"", "A", "Level", "A man a plan a canal Panama", "Hello World"};

        System.out.println("=== Reverse Test ===");
        for (String s : testStrings) {
            System.out.println("\"" + s + "\" -> \"" + reverse(s) + "\"");
        }

        System.out.println("\n=== IsPalindrome Test ===");
        for (String s : testStrings) {
            System.out.println("\"" + s + "\" -> " + isPalindrome(s));
        }

        System.out.println("\n=== CountCharacter Test ===");
        System.out.println("Count 'l' in \"Level\": " + countCharacter("Level", 'l'));
        System.out.println("Count 'a' in \"Panama\": " + countCharacter("Panama", 'a'));
    }
}
