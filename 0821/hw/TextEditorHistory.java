import java.util.ArrayDeque;
import java.util.Deque;

// 課後作業一：文字編輯 Undo/Redo
//指定檔名：TextEditorHistory.java

public class TextEditorHistory {
    private final Deque<String> undo = new ArrayDeque<>();
    private final Deque<String> redo = new ArrayDeque<>();

    void apply(String action) {
        if (action == null || action.trim().length() == 0) return;
        undo.push(action.trim());
        redo.clear(); // 清空 redo
        printState("apply(" + action + ")");
    }

    String undo() {
        String act = undo.pollFirst();
        if (act == null) {
            printState("undo(none)");
            return "EMPTY";
        }
        redo.push(act);
        printState("undo");
        return act;
    }

    String redo() {
        String act = redo.pollFirst();
        if (act == null) {
            printState("redo(none)");
            return "EMPTY"; //移除空stack
        }
        undo.push(act);
        printState("redo");
        return act;
    }

    String current() {
        String top = undo.peek();
        return top == null ? "EMPTY" : top;
    }

    void printState(String step) {
        System.out.println(step + " -> undo=" + undo + " | redo=" + redo);
    }

    public static void main(String[] args) {
        TextEditorHistory h = new TextEditorHistory();
        h.apply("Type A");
        h.apply("Type B");
        h.apply("Delete B");
        System.out.println("current=" + h.current());
        h.undo();
        h.undo();
        h.redo();
        h.apply("Type C"); // 清空 redo
        h.undo();
        h.undo();
        h.undo(); 
    }
}