import java.util.ArrayList;
import java.util.List;

public class Q11_BstDeletion {

    private static class Node {
        int value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
        }
    }

    private Node root;
    private int size;

    public Q11_BstDeletion() {
        this.root = null;
        this.size = 0;
    }

    // 1. 新增 
    public boolean add(int value) {
        if (contains(value)) {
            return false;
        }
        root = addHelper(root, value);
        size++;
        return true;
    }

    private Node addHelper(Node node, int value) {
        if (node == null) return new Node(value);
        if (value < node.value) {
            node.left = addHelper(node.left, value);
        } else if (value > node.value) {
            node.right = addHelper(node.right, value);
        }
        return node;
    }

    // 2. 刪除 
    public boolean remove(int value) {
        if (!contains(value)) {
            return false;
        }
        root = removeHelper(root, value);
        size--;
        return true;
    }

    private Node removeHelper(Node node, int value) {
        if (node == null) return null;

        if (value < node.value) {
            node.left = removeHelper(node.left, value);
        } else if (value > node.value) {
            node.right = removeHelper(node.right, value);
        } else {
            // 無子節點 或 只有單一子節點
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            //  兩個子節點 -> 使用右子樹最小值 (inorder successor)
            Node minNode = findMin(node.right);
            node.value = minNode.value;
            node.right = removeHelper(node.right, minNode.value);
        }
        return node;
    }

    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    // 3. 搜尋
    public boolean contains(int value) {
        Node cur = root;
        while (cur != null) {
            if (value == cur.value) return true;
            cur = (value < cur.value) ? cur.left : cur.right;
        }
        return false;
    }

    // 4. all node
    public int size() {
        return size;
    }

    // 5. Inorder 走訪
    public List<Integer> inorder() {
        List<Integer> result = new ArrayList<>();
        inorderHelper(root, result);
        return result;
    }

    private void inorderHelper(Node node, List<Integer> result) {
        if (node == null) return;
        inorderHelper(node.left, result);
        result.add(node.value);
        inorderHelper(node.right, result);
    }

    // 6. BST 合法性檢查 (全域 boundary)
    public boolean isValid() {
        return isValidHelper(root, null, null);
    }

    private boolean isValidHelper(Node node, Integer min, Integer max) {
        if (node == null) return true;
        if (min != null && node.value <= min) return false;
        if (max != null && node.value >= max) return false;
        return isValidHelper(node.left, min, node.value) 
            && isValidHelper(node.right, node.value, max);
    }//A1499777 infix
}
