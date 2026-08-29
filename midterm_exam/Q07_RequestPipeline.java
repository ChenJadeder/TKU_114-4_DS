import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Q07_RequestPipeline {

    // 1. 括號配對檢查 
    public static boolean isBalanced(String text) {
        if (text == null) {
            return false;
        }
        
        Deque<Character> stack = new ArrayDeque<>();
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            switch (ch) {
                case '(':
                case '[':
                case '{':
                    stack.push(ch);
                    break;
                case ')':
                    if (stack.isEmpty() || stack.pop() != '(') return false;
                    break;
                case ']':
                    if (stack.isEmpty() || stack.pop() != '[') return false;
                    break;
                case '}':
                    if (stack.isEmpty() || stack.pop() != '{') return false;
                    break;
                default:
                    // 忽略其他非括號字元 token
                    break;
            }
        }
        return stack.isEmpty();
    }

    // 2. 優先權佇列處理 (雙 Deque)
    public static List<String> process(String[] commands) {
        Deque<String> urgentQueue = new ArrayDeque<>();
        Deque<String> normalQueue = new ArrayDeque<>();
        List<String> results = new ArrayList<>();

        if (commands == null) {
            return results;
        }

        for (String cmd : commands) {
            if (cmd == null) continue;
            String s = cmd.trim();
            if (s.isEmpty()) continue;

            String[] parts = s.split("\\s+", 2);
            String op = parts[0];

            if ("NORMAL".equals(op)) {
                if (parts.length == 2) {
                    String id = parts[1].trim();
                    if (!id.isEmpty()) {
                        normalQueue.addLast(id);
                    }
                }
            } else if ("URGENT".equals(op)) {
                if (parts.length == 2) {
                    String id = parts[1].trim();
                    if (!id.isEmpty()) {
                        urgentQueue.addLast(id);
                    }
                }
            } else if ("PROCESS".equals(op)) {
                if (urgentQueue.isEmpty() && normalQueue.isEmpty()) {
                    results.add("EMPTY");
                } else if (!urgentQueue.isEmpty()) {
                    results.add(urgentQueue.pollFirst());
                } else {
                    results.add(normalQueue.pollFirst());
                }
            }
        }
        return results;
    }//A14997777 
}
