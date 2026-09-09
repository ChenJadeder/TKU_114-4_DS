// 課後作業五：Tree Shape Experiment
// 指定檔名：BstShapeExperiment.java

class ShapeNode {
    int value;
    ShapeNode left;
    ShapeNode right;

    ShapeNode(int value) {
        this.value = value;
    }
}

class ShapeBst {
    private ShapeNode root;

    boolean add(int value) {
        if (root == null) {
            root = new ShapeNode(value);
            return true;
        }

        ShapeNode current = root;

        while (true) {
            if (value == current.value) {
                return false;
            }

            if (value < current.value) {
                if (current.left == null) {
                    current.left = new ShapeNode(value);
                    return true;
                }
                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new ShapeNode(value);
                    return true;
                }
                current = current.right;
            }
        }
    }

    int height() {
        return height(root);
    }

    private int height(ShapeNode node) {
        if (node == null) {
            return -1;
        }

        int leftHeight = height(node.left);
        int rightHeight = height(node.right);

        return 1 + Math.max(leftHeight, rightHeight);
    }

    int size() {
        return size(root);
    }

    private int size(ShapeNode node) {
        if (node == null) {
            return 0;
        }

        return 1 + size(node.left) + size(node.right);
    }

    // 找到時回傳比較次數，找不到時回傳 -1
    int searchComparisons(int target) {
        ShapeNode current = root;
        int comparisons = 0;

        while (current != null) {
            comparisons++;

            if (target == current.value) {
                return comparisons;
            }

            if (target < current.value) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        return -1;
    }

    int totalSearchComparisons(int[] values) {
        int total = 0;

        for (int i = 0; i < values.length; i++) {
            int count = searchComparisons(values[i]);

            if (count > 0) {
                total += count;
            }
        }

        return total;
    }

    void inorder() {
        inorder(root);
        System.out.println();
    }

    private void inorder(ShapeNode node) {
        if (node == null) {
            return;
        }

        inorder(node.left);
        System.out.print(node.value + " ");
        inorder(node.right);
    }
}

public class BstShapeExperiment {

    static ShapeBst buildTree(int[] order) {
        ShapeBst tree = new ShapeBst();

        for (int i = 0; i < order.length; i++) {
            tree.add(order[i]);
        }

        return tree;
    }

    static void printReport(String label, ShapeBst tree, int[] allValues) {
        System.out.println("--- " + label + " ---");
        System.out.print("inorder=");
        tree.inorder();

        System.out.println("size=" + tree.size());
        System.out.println("height=" + tree.height());
        System.out.println("total search comparisons="
                + tree.totalSearchComparisons(allValues));

        System.out.println("search 1 comparisons="
                + tree.searchComparisons(1));
        System.out.println("search 8 comparisons="
                + tree.searchComparisons(8));
        System.out.println("search 15 comparisons="
                + tree.searchComparisons(15));
        System.out.println();
    }

    public static void main(String[] args) {
        int[] allValues = {
            1, 2, 3, 4, 5,
            6, 7, 8, 9, 10,
            11, 12, 13, 14, 15
        };

        // 升冪插入：會形成只有 right child 的 skewed tree
        int[] sortedOrder = {
            1, 2, 3, 4, 5,
            6, 7, 8, 9, 10,
            11, 12, 13, 14, 15
        };

        // 先插入中間值，盡量形成平衡的 tree
        int[] balancedOrder = {
            8, 4, 12, 2, 6,
            10, 14, 1, 3, 5,
            7, 9, 11, 13, 15
        };

        // 同樣 15 個值，但採另一種較不規則的插入順序
        int[] mixedOrder = {
            8, 3, 12, 1, 6,
            10, 14, 2, 5, 7,
            9, 11, 13, 15, 4
        };

        ShapeBst sortedTree = buildTree(sortedOrder);
        ShapeBst balancedTree = buildTree(balancedOrder);
        ShapeBst mixedTree = buildTree(mixedOrder);

        printReport("sorted insert order", sortedTree, allValues);
        printReport("balanced insert order", balancedTree, allValues);
        printReport("mixed insert order", mixedTree, allValues);
    }
}
