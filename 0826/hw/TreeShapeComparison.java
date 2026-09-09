//課後作業四：Tree Shape Comparison
//指定檔名：TreeShapeComparison.java

//因 insertion order 不同. BST 的 height 與搜尋成本就可能大幅不同
// 如果資料總是依排序順序插入，BST 會逐漸退化成 linked list。
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

        return 1 + Math.max(height(node.left), height(node.right));
    }

    int searchComparisons(int target) {
        int comparisons = 0;
        ShapeNode current = root;

        while (current != null) {
            comparisons++;

            if (target == current.value) {
                return comparisons;
            }

            current = target < current.value
                    ? current.left
                    : current.right;
        }

        return comparisons;
    }

    int totalSearchComparisons(int[] values) {
        int total = 0;

        for (int value : values) {
            total += searchComparisons(value);
        }

        return total;
    }
}

public class TreeShapeComparison {
    private static ShapeBst buildTree(int[] insertionOrder) {
        ShapeBst tree = new ShapeBst();

        for (int value : insertionOrder) {
            tree.add(value);
        }

        return tree;
    }

    private static void report(String name, ShapeBst tree,
                               int[] allKeys, int missingKey) {
        System.out.println(name);
        System.out.println("height=" + tree.height());
        System.out.println("existingComparisonTotal="
                + tree.totalSearchComparisons(allKeys));
        System.out.println("missingKey=" + missingKey);
        System.out.println("missingComparisonCount="
                + tree.searchComparisons(missingKey));
        System.out.println();
    }

    public static void main(String[] args) {
        int[] allKeys = {
                1, 2, 3, 4, 5,
                6, 7, 8, 9, 10,
                11, 12, 13, 14, 15
        };

        int[] ascending = {
                1, 2, 3, 4, 5,
                6, 7, 8, 9, 10,
                11, 12, 13, 14, 15
        };

        int[] descending = {
                15, 14, 13, 12, 11,
                10, 9, 8, 7, 6,
                5, 4, 3, 2, 1
        };

        int[] nearBalanced = {
                8, 4, 12, 2, 6,
                10, 14, 1, 3, 5,
                7, 9, 11, 13, 15
        };

        int missingKey = 16;

        ShapeBst ascendingTree = buildTree(ascending);
        ShapeBst descendingTree = buildTree(descending);
        ShapeBst balancedTree = buildTree(nearBalanced);

        report("ascending tree", ascendingTree, allKeys, missingKey);
        report("descending tree", descendingTree, allKeys, missingKey);
        report("near balanced tree", balancedTree, allKeys, missingKey);
    }
}