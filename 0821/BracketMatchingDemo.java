import java.util.ArrayDeque;
import java.util.Deque;

public class BracketMatchingDemo {
    static boolean isBalanced(String expression) {
        if (expression == null) {
            return false;
        }

        Deque<Character> stack = new ArrayDeque<>();
        for (char symbol : expression.toCharArray()) {
            if (symbol == '(' || symbol == '[' || symbol == '{') {
                stack.push(symbol);
            } else if (symbol == ')' || symbol == ']' || symbol == '}') {
                if (stack.isEmpty() || !matches(stack.pop(), symbol)) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    // 回傳第一個錯誤位置；全對回傳 -1；若左括號剩餘，回傳expression length
    static int firstErrorIndex(String expression) {
        if (expression == null) return 0; 
        Deque<Character> stack = new ArrayDeque<>();
        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);
            if (ch == '(' || ch == '[' || ch == '{') {
                stack.push(ch);
            } else if (ch == ')' || ch == ']' || ch == '}') {
                if (stack.isEmpty()) return i;              // 
                char open = stack.pop();
                if (!matches(open, ch)) return i;           // 型別不對
            }
        }
        return stack.isEmpty() ? -1 : expression.length();   // 剩左括號
    }

    static boolean matches(char open, char close) {
        return (open == '(' && close == ')')
            || (open == '[' && close == ']')
            || (open == '{' && close == '}');
    }

    public static void main(String[] args) {
        String[] expressions = { "{[()]}", "([)]", "(()", "a + (b * c)", "" };

        for (String exp : expressions) {
            System.out.println(exp + " -> " + isBalanced(exp)
                    + ", firstErrorIndex=" + firstErrorIndex(exp));
        }
    }
}
