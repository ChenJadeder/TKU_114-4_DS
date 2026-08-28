import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Q09_BinarySearchTree {

    public static class Node {
        public int val;
        public Node left;
        public Node right;

        public Node(int val) {
            this.val = val;
        }
    }

    private Node root;

    public Q09_BinarySearchTree() {
        this.root = null;
    }

    // 1. BST 節點新增 
    public void insert(int val) {
        root = insertRecursive(root, val);
    }

    private Node insertRecursive(Node current, int val) {
        if (current == null) {
            return new Node(val);
        }
        if (val < current.val) {
            current.left = insertRecursive(current.left, val);
        } else if (val > current.val) {
            current.right = insertRecursive(current.right, val);
        }
        return current;
    }

    // 2. BST 搜尋 
    public boolean contains(int val) {
        return containsRecursive(root, val);
    }

    private boolean containsRecursive(Node current, int val) {
        if (current == null) return false;
        if (val == current.val) return true;
        
        return val < current.val 
            ? containsRecursive(current.left, val) 
            : containsRecursive(current.right, val);
    }

    // 3. Preorder: Root -> Left -> Right
    public List<Integer> preorder() {
        List<Integer> result = new ArrayList<>();
        preorderHelper(root, result);
        return result;
    }

    private void preorderHelper(Node node, List<Integer> result) {
        if (node == null) return;
        result.add(node.val);
        preorderHelper(node.left, result);
        preorderHelper(node.right, result);
    }

    // 4. Inorder: Left -> Root -> Right
    public List<Integer> inorder() {
        List<Integer> result = new ArrayList<>();
        inorderHelper(root, result);
        return result;
    }

    private void inorderHelper(Node node, List<Integer> result) {
        if (node == null) return;
        inorderHelper(node.left, result);
        result.add(node.val);
        inorderHelper(node.right, result);
    }

    // 5. Postorder: Left -> Right -> Root
    public List<Integer> postorder() {
        List<Integer> result = new ArrayList<>();
        postorderHelper(root, result);
        return result;
    }

    private void postorderHelper(Node node, List<Integer> result) {
        if (node == null) return;
        postorderHelper(node.left, result);
        postorderHelper(node.right, result);
        result.add(node.val);
    }

    // 6.Level-Order
    public List<Integer> levelOrder() {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;

        Queue<Node> queue = new ArrayDeque<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            result.add(current.val);

            // 避免  NullPointerException
            if (current.left != null) queue.offer(current.left);
            if (current.right != null) queue.offer(current.right);
        }
        return result;
    }
//A14997777 Printfix
}