import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
//課後作業三：Traversal 結果集合
//檔名：TraversalResultCollector.java
class StringNode {
    String value;
    StringNode left;
    StringNode right;

    StringNode(String value) {
        this.value = value;
    }
}

public class TraversalResultCollector {

    public static List<String> preorder(StringNode root) {
        List<String> result = new ArrayList<>();
        preorderHelper(root, result);
        return result;
    }

    private static void preorderHelper(StringNode node, List<String> result) {
        if (node == null) return;
        result.add(node.value);
        preorderHelper(node.left, result);
        preorderHelper(node.right, result);
    }

    public static List<String> inorder(StringNode root) {
        List<String> result = new ArrayList<>();
        inorderHelper(root, result);
        return result;
    }

    private static void inorderHelper(StringNode node, List<String> result) {
        if (node == null) return;
        inorderHelper(node.left, result);
        result.add(node.value);
        inorderHelper(node.right, result);
    }

    public static List<String> postorder(StringNode root) {
        List<String> result = new ArrayList<>();
        postorderHelper(root, result);
        return result;
    }

    private static void postorderHelper(StringNode node, List<String> result) {
        if (node == null) return;
        postorderHelper(node.left, result);
        postorderHelper(node.right, result);
        result.add(node.value);
    }

    public static List<String> levelOrder(StringNode root) {
        List<String> result = new ArrayList<>();
        if (root == null) return result;

        Queue<StringNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            StringNode current = queue.poll();
            result.add(current.value);
            if (current.left != null) queue.offer(current.left);
            if (current.right != null) queue.offer(current.right);
        }
        return result;
    }

    public static void main(String[] args) {
        // Complete Tree
        StringNode complete = new StringNode("A");
        complete.left = new StringNode("B");
        complete.right = new StringNode("C");
 //Where is full tree ?   P.S. Complete Tree != Full tree
      
        // Left-Skewed Tree
        StringNode skewed = new StringNode("X");
        skewed.left = new StringNode("Y");
        skewed.left.left = new StringNode("Z");

        System.out.println("=== Complete Tree Traversals ===");
        System.out.println("Preorder:   " + preorder(complete));
        System.out.println("Inorder:    " + inorder(complete));
        System.out.println("Postorder:  " + postorder(complete));
        System.out.println("LevelOrder: " + levelOrder(complete));

        System.out.println("\n=== Left-Skewed Tree Traversals ===");
        System.out.println("Preorder:   " + preorder(skewed));
        System.out.println("Inorder:    " + inorder(skewed));

        System.out.println("\n=== Empty Tree Traversal ===");
        System.out.println("Preorder:   " + preorder(null));
    }
}
