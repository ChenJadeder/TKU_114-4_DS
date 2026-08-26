//課堂實作題三：Binary Tree 結構報表
//檔名：BinaryTreeStructureReport.java

class ReportNode {
    int value;
    ReportNode left;
    ReportNode right;

    ReportNode(int value) {
        this.value = value;
    }
}

public class BinaryTreeStructureReport {
    static int size(ReportNode node) {
        return node == null ? 0 : 1 + size(node.left) + size(node.right);
    }

    static int leafCount(ReportNode node) {
        if (node == null) return 0;
        if (node.left == null && node.right == null) return 1;
        return leafCount(node.left) + leafCount(node.right);
    }

    static int height(ReportNode node) {
        return node == null ? -1 : 1 + Math.max(height(node.left), height(node.right));
    }

    static void printLeaves(ReportNode node) {
        if (node == null) return;
        if (node.left == null && node.right == null) {
            System.out.print(node.value + " ");
            return;
        }
        printLeaves(node.left);
        printLeaves(node.right);
    }

    static void generateReport(String title, ReportNode root) {
        System.out.println("=== " + title + " ===");
        System.out.println("Root: " + (root == null ? "null" : root.value));
        System.out.print("Leaves: ");
        printLeaves(root);
        System.out.println();
        System.out.println("Size: " + size(root));
        System.out.println("Leaf Count: " + leafCount(root));
        System.out.println("Height: " + height(root));
        System.out.println();
    }

    public static void main(String[] args) {
        // 建立 7 個節點的 Binary Tree
        ReportNode root = new ReportNode(10);
        root.left = new ReportNode(20);
        root.right = new ReportNode(30);
        root.left.left = new ReportNode(40);
        root.left.right = new ReportNode(50);
        root.right.left = new ReportNode(60);
        root.right.right = new ReportNode(70);

        generateReport("7-Node Tree", root);
        generateReport("Single-Node Tree", new ReportNode(99));
        generateReport("Empty Tree", null);
    }
}