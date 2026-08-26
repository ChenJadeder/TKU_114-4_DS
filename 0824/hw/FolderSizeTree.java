import java.util.ArrayList;
import java.util.List;
//課後作業四：目錄大小累加
//檔名：FolderSizeTree.java

class FolderNode {
    String name;
    int ownSize;
    FolderNode left;
    FolderNode right;

    FolderNode(String name, int ownSize) {
        this.name = name;
        this.ownSize = ownSize;
    }
}

public class FolderSizeTree {

    // Postorder 累加目錄大小
    public static int calculateTotalSize(FolderNode node) {
        if (node == null) {
            return 0;
        }
        int leftTotal = calculateTotalSize(node.left);
        int rightTotal = calculateTotalSize(node.right);
        return node.ownSize + leftTotal + rightTotal;
    }

    public static void findMaxSubtree(FolderNode root) {
        FolderNode[] maxFolder = new FolderNode[1];
        int[] maxSize = new int[]{-1};
        findMaxSubtreeHelper(root, maxFolder, maxSize);

        if (maxFolder[0] != null) {
            System.out.println("Max Subtree Folder: " + maxFolder[0].name + " (Total Size: " + maxSize[0] + " MB)");
        }
    }

    private static int findMaxSubtreeHelper(FolderNode node, FolderNode[] maxFolder, int[] maxSize) {
        if (node == null) return 0;

        int total = node.ownSize + findMaxSubtreeHelper(node.left, maxFolder, maxSize)
                                + findMaxSubtreeHelper(node.right, maxFolder, maxSize);

        if (total > maxSize[0]) {
            maxSize[0] = total;
            maxFolder[0] = node;
        }
        return total;
    }

    public static List<String> getLeafFolders(FolderNode node) {
        List<String> leaves = new ArrayList<>();
        findLeafFoldersHelper(node, leaves);
        return leaves;
    }

    private static void findLeafFoldersHelper(FolderNode node, List<String> leaves) {
        if (node == null) return;
        if (node.left == null && node.right == null) {
            leaves.add(node.name);
            return;
        }
        findLeafFoldersHelper(node.left, leaves);
        findLeafFoldersHelper(node.right, leaves);
    }

    public static void main(String[] args) {
        // 建立目錄樹結構
        FolderNode root = new FolderNode("Root", 10);
        root.left = new FolderNode("Documents", 20);
        root.right = new FolderNode("Pictures", 5);
        root.left.left = new FolderNode("Work", 100);
        root.left.right = new FolderNode("Personal", 50);

        System.out.println("Total System Size: " + calculateTotalSize(root) + " MB");
        findMaxSubtree(root);
        System.out.println("Leaf Folders: " + getLeafFolders(root));
    }
}
