// 課堂實作題五：Tree Bug Lab
// 指定檔名：TreeBugLab.java
// 不需要先建一棵很大的 tree 找到 哪一條 reference、哪一個 traversal 時機、哪一個 boundary 判斷
import java.util.ArrayList;
import java.util.List;

class BugNode {
    int value;
    BugNode left;
    BugNode right;

    BugNode(int value) {
        this.value = value;
    }
}

public class TreeBugLab {
    private static boolean buggyContains(BugNode root, int target) {
        BugNode current = root;

        while (current != null) {
            if (target == current.value) return true;

            current = target < current.value
                    ? current.right
                    : current.left;
        }

        return false;
    }

    private static boolean fixedContains(BugNode root, int target) {
        BugNode current = root;

        while (current != null) {
            if (target == current.value) return true;

            current = target < current.value
                    ? current.left
                    : current.right;
        }

        return false;
    }

    private static void buggyInorder(BugNode node, List<Integer> result) {
        if (node == null) return;

        result.add(node.value);
        buggyInorder(node.left, result);
        buggyInorder(node.right, result);
    }

    private static void fixedInorder(BugNode node, List<Integer> result) {
        if (node == null) return;

        fixedInorder(node.left, result);
        result.add(node.value);
        fixedInorder(node.right, result);
    }

    private static List<Integer> buggyInorder(BugNode root) {
        List<Integer> result = new ArrayList<>();
        buggyInorder(root, result);
        return result;
    }

    private static List<Integer> fixedInorder(BugNode root) {
        List<Integer> result = new ArrayList<>();
        fixedInorder(root, result);
        return result;
    }

    private static BugNode buggyRemove(BugNode node, int target) {
        if (node == null) return null;

        if (target < node.value) {
            node.left = buggyRemove(node.left, target);
        } else if (target > node.value) {
            node.right = buggyRemove(node.right, target);
        } else {
            if (node.left == null || node.right == null) {
                return null;
            }
        }

        return node;
    }

    private static BugNode fixedRemove(BugNode node, int target) {
        if (node == null) return null;

        if (target < node.value) {
            node.left = fixedRemove(node.left, target);
        } else if (target > node.value) {
            node.right = fixedRemove(node.right, target);
        } else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            BugNode successor = minimumNode(node.right);
            node.value = successor.value;
            node.right = fixedRemove(node.right, successor.value);
        }

        return node;
    }

    private static BugNode minimumNode(BugNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    private static boolean buggyIsValid(BugNode node) {
        if (node == null) return true;

        if (node.left != null && node.left.value >= node.value) {
            return false;
        }

        if (node.right != null && node.right.value <= node.value) {
            return false;
        }

        return buggyIsValid(node.left) && buggyIsValid(node.right);
    }

    private static boolean fixedIsValid(BugNode root) {
        return fixedIsValid(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private static boolean fixedIsValid(BugNode node, long low, long high) {
        if (node == null) return true;

        if (node.value <= low || node.value >= high) {
            return false;
        }

        return fixedIsValid(node.left, low, node.value)
                && fixedIsValid(node.right, node.value, high);
    }

    private static void searchDirectionCase() {
        BugNode root = new BugNode(10);
        root.right = new BugNode(20);

        System.out.println("=== Search Direction Bug ===");
        System.out.println("buggyFind20=" + buggyContains(root, 20));
        System.out.println("fixedFind20=" + fixedContains(root, 20));
        System.out.println();
    }

    private static void inorderCase() {
        BugNode root = new BugNode(2);
        root.left = new BugNode(1);
        root.right = new BugNode(3);

        System.out.println("=== Inorder Order Bug ===");
        System.out.println("buggyInorder=" + buggyInorder(root));
        System.out.println("fixedInorder=" + fixedInorder(root));
        System.out.println();
    }

    private static void deleteChildCase() {
        BugNode buggyRoot = new BugNode(10);
        buggyRoot.right = new BugNode(20);

        BugNode fixedRoot = new BugNode(10);
        fixedRoot.right = new BugNode(20);

        buggyRoot = buggyRemove(buggyRoot, 10);
        fixedRoot = fixedRemove(fixedRoot, 10);

        System.out.println("=== Delete Child Loss Bug ===");
        System.out.println("buggyTree=" + buggyInorder(buggyRoot));
        System.out.println("fixedTree=" + fixedInorder(fixedRoot));
        System.out.println();
    }

    private static void deepValidationCase() {
        BugNode root = new BugNode(50);
        root.left = new BugNode(30);
        root.left.right = new BugNode(60);

        System.out.println("=== Deep Validation Bug ===");
        System.out.println("buggyValid=" + buggyIsValid(root));
        System.out.println("fixedValid=" + fixedIsValid(root));
        System.out.println();
    }

    public static void main(String[] args) {
        searchDirectionCase();
        inorderCase();
        deleteChildCase();
        deepValidationCase();
    }
}