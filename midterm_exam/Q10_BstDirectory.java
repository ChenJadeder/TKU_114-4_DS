import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Q10_BstDirectory {

    // 內部節點類別 左右與根兄弟
    private static class Node {
        int value;
        Node left;
        Node right;

        Node(int v) {
            this.value = v;
        }
    }

    private Node root;
    private int size;

    // 新增node
    public boolean add(int value) {
        if (contains(value)) {
            return false;
        }
        if (root == null) {
            root = new Node(value);
            size = 1;
            return true;
        }
        Node cur = root;
        while (true) {
            if (value < cur.value) {
                if (cur.left == null) {
                    cur.left = new Node(value);
                    size++;
                    return true;
                } else {
                    cur = cur.left;
                }
            } else {
                if (cur.right == null) {
                    cur.right = new Node(value);
                    size++;
                    return true;
                } else {
                    cur = cur.right;
                }
            }
        }
    }

    // 搜尋數值是否存在 (迭代式)
    public boolean contains(int value) {
        Node cur = root;
        while (cur != null) {
            if (value == cur.value) return true;
            cur = (value < cur.value) ? cur.left : cur.right;
        }
        return false;
    }

    public int size() {
        return size;
    }

    // 回傳路徑軌跡
    public List<Integer> searchPath(int target) {
        List<Integer> path = new ArrayList<>();
        Node cur = root;
        while (cur != null) {
            path.add(cur.value);
            if (target == cur.value) {
                break;
            } else if (target < cur.value) {
                cur = cur.left;
            } else {
                cur = cur.right;
            }
        }
        return path;
    }

    // inorder
    public List<Integer> inorder() {
        List<Integer> result = new ArrayList<>();
        Deque<Node> stack = new ArrayDeque<>();
        Node cur = root;
        while (cur != null || !stack.isEmpty()) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            cur = stack.pop();
            result.add(cur.value);
            cur = cur.right;
        }
        return result;
    }

    // 驗證BST
    public boolean isValid() {
        return isValidHelper(root, null, null);
    }

    // 驗證範圍
    private boolean isValidHelper(Node node, Integer min, Integer max) {
        if (node == null) return true;
        if (min != null && node.value <= min) return false;
        if (max != null && node.value >= max) return false;
        return isValidHelper(node.left, min, node.value) 
            && isValidHelper(node.right, node.value, max);
    }

}