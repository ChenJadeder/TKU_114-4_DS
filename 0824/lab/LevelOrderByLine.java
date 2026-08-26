import java.util.ArrayDeque;
import java.util.Queue;
//課堂實作題五：逐層分行輸出
//指定檔名：LevelOrderByLine.java

class LineNode {
    String value;
    LineNode left;
    LineNode right;

    LineNode(String value) {
        this.value = value;
    }
}

public class LevelOrderByLine {
    static void printLevelByLine(LineNode root) {
        if (root == null) {
            System.out.println("Empty tree.");
            return;
        }

        Queue<LineNode> queue = new ArrayDeque<>();
        queue.offer(root);
        int level = 0;

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            System.out.print("Level " + level + " (Nodes: " + levelSize + "): ");

            for (int i = 0; i < levelSize; i++) {
                LineNode current = queue.poll();
                System.out.print(current.value + " ");

                if (current.left != null) queue.offer(current.left);
                if (current.right != null) queue.offer(current.right);
            }
            System.out.println();
            level++;
        }
    }

    public static void main(String[] args) {
        LineNode root = new LineNode("A");
        root.left = new LineNode("B");
        root.right = new LineNode("C");
        root.left.left = new LineNode("D");
        root.left.right = new LineNode("E");
        root.right.right = new LineNode("F");

        printLevelByLine(root);
        System.out.println("\nTesting Empty Tree:");
        printLevelByLine(null);
    }
}