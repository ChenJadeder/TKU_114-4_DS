//課後作業二：Binary Tree 統計系統
//檔名：BinaryTreeStatistics.java
class IntNode {
    int value;
    IntNode left;
    IntNode right;

    IntNode(int value) {
        this.value = value;
    }
}

public class BinaryTreeStatistics {

    public static int size(IntNode node) {
        return node == null ? 0 : 1 + size(node.left) + size(node.right);
    }

    public static int sum(IntNode node) {
        return node == null ? 0 : node.value + sum(node.left) + sum(node.right);
    }

    public static int maximum(IntNode node) {
        if (node == null) {
            throw new IllegalArgumentException("Cannot find maximum of an empty tree.");
        }
        return maxHelper(node);
    }

    private static int maxHelper(IntNode node) {
        if (node == null) {
            return Integer.MIN_VALUE; // 極小值
        }
        int leftMax = maxHelper(node.left);
        int rightMax = maxHelper(node.right);
        return Math.max(node.value, Math.max(leftMax, rightMax));
    }

    public static int leafCount(IntNode node) {
        if (node == null) {
            return 0;
        }
        if (node.left == null && node.right == null) {
            return 1;
        }
        return leafCount(node.left) + leafCount(node.right);
    }

    public static int height(IntNode node) {
        return node == null ? -1 : 1 + Math.max(height(node.left), height(node.right));
    }

    public static boolean contains(IntNode node, int target) {
        if (node == null) {
            return false;
        }
        if (node.value == target) {
            return true;
        }
        return contains(node.left, target) || contains(node.right, target);
    }

    public static void main(String[] args) {
        IntNode root = new IntNode(15);
        root.left = new IntNode(28);
        root.right = new IntNode(7);
        root.left.left = new IntNode(42);
        root.right.right = new IntNode(19);

        System.out.println("Size: " + size(root));
        System.out.println("Sum: " + sum(root));
        System.out.println("Maximum: " + maximum(root));
        System.out.println("Leaf Count: " + leafCount(root));
        System.out.println("Height: " + height(root));
        System.out.println("Contains 42: " + contains(root, 42));
        System.out.println("Contains 99: " + contains(root, 99));

        // 測試樹為空時的例外處理
        try {
            maximum(null);
        } catch (IllegalArgumentException e) {
            System.out.println("Empty tree maximum check: " + e.getMessage());
        }
    }
}
