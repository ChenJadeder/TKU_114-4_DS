// 課堂實作題二：Duplicate Policy
// 指定檔名：BstDuplicateCounter.java

class CountNode {
    int key;
    int count;
    CountNode left;
    CountNode right;

    CountNode(int key) {
        this.key = key;
        this.count = 1;
    }
}

class DuplicateCounterBst {
    private CountNode root;

    void add(int key) {
        if (root == null) {
            root = new CountNode(key);
            return;
        }

        CountNode current = root;

        while (true) {
            if (key == current.key) {
                current.count++;
                return;
            }

            if (key < current.key) {
                if (current.left == null) {
                    current.left = new CountNode(key);
                    return;
                }
                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new CountNode(key);
                    return;
                }
                current = current.right;
            }
        }
    }

    void inorder() {
        inorder(root);
        System.out.println();
    }

    private void inorder(CountNode node) {
        if (node == null) {
            return;
        }

        inorder(node.left);
        System.out.print(node.key + "(" + node.count + ") ");
        inorder(node.right);
    }

    int distinctSize() {
        return distinctSize(root);
    }

    private int distinctSize(CountNode node) {
        if (node == null) {
            return 0;
        }

        return 1 + distinctSize(node.left) + distinctSize(node.right);
    }

    int totalCount() {
        return totalCount(root);
    }

    private int totalCount(CountNode node) {
        if (node == null) {
            return 0;
        }

        return node.count
                + totalCount(node.left)
                + totalCount(node.right);
    }
}

public class BstDuplicateCounter {
    public static void main(String[] args) {
        DuplicateCounterBst tree = new DuplicateCounterBst();

        int[] values = {50, 30, 70, 20, 40, 70, 30, 70, 20, 60};

        for (int i = 0; i < values.length; i++) {
            tree.add(values[i]);
        }

        System.out.print("inorder=");
        tree.inorder();

        System.out.println("distinct nodes=" + tree.distinctSize());
        System.out.println("total inserted values=" + tree.totalCount());
    }
}