import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
//課後作業五：組織架構報表
//檔名：OrganizationTreeReport.java
class UnitNode {
    String name;
    UnitNode left;
    UnitNode right;

    UnitNode(String name) {
        this.name = name;
    }
}

public class OrganizationTreeReport {

    public static String findParent(UnitNode root, String target) {
        if (root == null || target == null || root.name.equals(target)) {
            return null; // Root 
        }
        return findParentHelper(root, target);
    }

    private static String findParentHelper(UnitNode node, String target) {
        if (node == null) return null;

        if ((node.left != null && node.left.name.equals(target)) ||
            (node.right != null && node.right.name.equals(target))) {
            return node.name;
        }

        String leftParent = findParentHelper(node.left, target);
        if (leftParent != null) return leftParent;

        return findParentHelper(node.right, target);
    }

    public static int findDepth(UnitNode node, String target) {
        if (node == null || target == null) return -1;
        if (node.name.equals(target)) return 0;

        int left = findDepth(node.left, target);
        if (left != -1) return left + 1;

        int right = findDepth(node.right, target);
        if (right != -1) return right + 1;

        return -1;
    }

    public static List<String> pathFromRoot(UnitNode root, String target) {
        List<String> path = new ArrayList<>();
        if (findPathHelper(root, target, path)) {
            return path;
        }
        return Collections.emptyList(); // 找不到時回傳空列表
    }

    private static boolean findPathHelper(UnitNode node, String target, List<String> path) {
        if (node == null) return false;

        path.add(node.name);
        if (node.name.equals(target)) return true;

        if (findPathHelper(node.left, target, path) || findPathHelper(node.right, target, path)) {
            return true;
        }

        path.remove(path.size() - 1); // Backtrack 
        return false;
    }

    public static void printByLevel(UnitNode root) {
        if (root == null) {
            System.out.println("Empty Organization Tree.");
            return;
        }
        Queue<UnitNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            for (int i = 0; i < levelSize; i++) {
                UnitNode current = queue.poll();
                System.out.print(current.name + " ");
                if (current.left != null) queue.offer(current.left);
                if (current.right != null) queue.offer(current.right);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        UnitNode root = new UnitNode("CEO");
        root.left = new UnitNode("VP_Sales");
        root.right = new UnitNode("VP_Tech");
        root.left.left = new UnitNode("Domestic_Sales");
        root.right.left = new UnitNode("R&D");

        System.out.println("=== Level Report ===");
        printByLevel(root);

        System.out.println("\n=== Search Reports ===");
        System.out.println("Parent of 'R&D': " + findParent(root, "R&D"));
        System.out.println("Parent of 'HR' (NotExist): " + findParent(root, "HR"));

        System.out.println("Depth of 'Domestic_Sales': " + findDepth(root, "Domestic_Sales"));
        System.out.println("Depth of 'HR': " + findDepth(root, "HR"));

        System.out.println("Path to 'R&D': " + pathFromRoot(root, "R&D"));
        System.out.println("Path to 'HR': " + pathFromRoot(root, "HR"));
        
        // 驗證
        System.out.println("Path from null root: " + pathFromRoot(null, "CEO"));
    }
}
