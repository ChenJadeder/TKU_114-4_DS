// 課堂實作題四：三種 Delete Case
// 指定檔名：BstDeleteCases.java

class DeleteNode {
    int value;
    DeleteNode left;
    DeleteNode right;

    DeleteNode(int value) {
        this.value = value;
    }
}

class DeleteBst {
    private DeleteNode root;

    boolean add(int value) {
        if (root == null) {
            root = new DeleteNode(value);
            return true;
        }

        DeleteNode current = root;

        while (true) {
            if (value == current.value) {
                return false;
            }

            if (value < current.value) {
                if (current.left == null) {
                    current.left = new DeleteNode(value);
                    return true;
                }
                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new DeleteNode(value);
                    return true;
                }
                current = current.right;
            }
        }
    }

    boolean contains(int value) {
        DeleteNode current = root;

        while (current != null) {
            if (value == current.value) {
                return true;
            }

            if (value < current.value) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        return false;
    }

    boolean remove(int value) {
        if (!contains(value)) {
            return false;
        }

        root = remove(root, value);
        return true;
    }

    private DeleteNode remove(DeleteNode node, int value) {
        if (node == null) {
            return null;
        }

        if (value < node.value) {
            node.left = remove(node.left, value);
        } else if (value > node.value) {
            node.right = remove(node.right, value);
        } else {
            // leaf 或只有 right child
            if (node.left == null) {
                return node.right;
            }

            // 只有 left child
            if (node.right == null) {
                return node.left;
            }

            // two children：使用 right subtree 的 minimum 作 successor
            DeleteNode successor = minimumNode(node.right);
            node.value = successor.value;
            node.right = remove(node.right, successor.value);
        }

        return node;
    }

    private DeleteNode minimumNode(DeleteNode node) {
        DeleteNode current = node;

        while (current.left != null) {
            current = current.left;
        }

        return current;
    }

    int size() {
        return size(root);
    }

    private int size(DeleteNode node) {
        if (node == null) {
            return 0;
        }

        return 1 + size(node.left) + size(node.right);
    }

    int height() {
        return height(root);
    }

    private int height(DeleteNode node) {
        if (node == null) {
            return -1;
        }

        int leftHeight = height(node.left);
        int rightHeight = height(node.right);

        return 1 + Math.max(leftHeight, rightHeight);
    }

    boolean isValid() {
        return isValid(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private boolean isValid(DeleteNode node, long min, long max) {
        if (node == null) {
            return true;
        }

        if (node.value <= min || node.value >= max) {
            return false;
        }

        return isValid(node.left, min, node.value)
                && isValid(node.right, node.value, max);
    }

    void inorder() {
        inorder(root);
        System.out.println();
    }

    private void inorder(DeleteNode node) {
        if (node == null) {
            return;
        }

        inorder(node.left);
        System.out.print(node.value + " ");
        inorder(node.right);
    }

    void printReport(String label) {
        System.out.print(label + " inorder=");
        inorder();
        System.out.println("size=" + size()
                + ", height=" + height()
                + ", valid=" + isValid());
    }
}

public class BstDeleteCases {
    public static void main(String[] args) {
        DeleteBst tree = new DeleteBst();

        int[] values = {50, 30, 70, 20, 40, 60, 80, 65};

        for (int i = 0; i < values.length; i++) {
            tree.add(values[i]);
        }

        tree.printReport("before");

        // Case 1: leaf node
        System.out.println("remove leaf 20=" + tree.remove(20));
        tree.printReport("after leaf");

        // Case 2: node 60 has one right child 65
        System.out.println("remove one-child 60=" + tree.remove(60));
        tree.printReport("after one-child");

        // Case 3: root 50 has two children
        System.out.println("remove two-children 50=" + tree.remove(50));
        tree.printReport("after two-children");

        // Missing value
        System.out.println("remove missing 999=" + tree.remove(999));
        tree.printReport("after missing");
    }
}