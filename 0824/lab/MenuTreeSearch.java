//課堂實作題六：樹狀選單搜尋
//指定檔名：MenuTreeSearch.java

class MenuNode {
    String name;
    MenuNode left;
    MenuNode right;

    MenuNode(String name) {
        this.name = name;
    }
}

public class MenuTreeSearch {
    static boolean contains(MenuNode node, String target) {
        if (node == null || target == null) return false;
        if (node.name.equals(target)) return true;
        return contains(node.left, target) || contains(node.right, target);
    }

    static int findDepth(MenuNode node, String target) {
        if (node == null || target == null) return -1;
        if (node.name.equals(target)) return 0;

        int leftDepth = findDepth(node.left, target);
        if (leftDepth != -1) return leftDepth + 1;

        int rightDepth = findDepth(node.right, target);
        if (rightDepth != -1) return rightDepth + 1;

        return -1;
    }

    static int countLeaves(MenuNode node) {
        if (node == null) return 0;
        if (node.left == null && node.right == null) return 1;
        return countLeaves(node.left) + countLeaves(node.right);
    }

    static void preorderDisplay(MenuNode node) {
        if (node == null) return;
        System.out.print(node.name + " ");
        preorderDisplay(node.left);
        preorderDisplay(node.right);
    }

    public static void main(String[] args) {
        MenuNode root = new MenuNode("MainMenu");
        root.left = new MenuNode("File");
        root.right = new MenuNode("Edit");
        root.left.left = new MenuNode("New");
        root.left.right = new MenuNode("Open");
        root.right.left = new MenuNode("Cut");

        System.out.print("Preorder Display: ");
        preorderDisplay(root);
        System.out.println("\n");

        System.out.println("Contains 'Open': " + contains(root, "Open"));
        System.out.println("Contains 'Save': " + contains(root, "Save"));

        System.out.println("Depth of 'MainMenu': " + findDepth(root, "MainMenu"));
        System.out.println("Depth of 'Open': " + findDepth(root, "Open"));
        System.out.println("Depth of 'Save': " + findDepth(root, "Save"));

        System.out.println("Leaf Count: " + countLeaves(root));
    }
}