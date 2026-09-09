// 課堂實作題三：Min、Max 與範圍
// 指定檔名：BstRangeReport.java

class RangeNode {
    int value;
    RangeNode left;
    RangeNode right;

    RangeNode(int value) {
        this.value = value;
    }
}

class RangeBst {
    private RangeNode root;

    boolean add(int value) {
        if (root == null) {
            root = new RangeNode(value);
            return true;
        }

        RangeNode current = root;

        while (true) {
            if (value == current.value) {
                return false;
            }

            if (value < current.value) {
                if (current.left == null) {
                    current.left = new RangeNode(value);
                    return true;
                }
                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new RangeNode(value);
                    return true;
                }
                current = current.right;
            }
        }
    }

    Integer minimum() {
        if (root == null) {
            return null;
        }

        RangeNode current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    Integer maximum() {
        if (root == null) {
            return null;
        }

        RangeNode current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }

    void printRange(int low, int high) {
        if (low > high) {
            System.out.println("range " + low + "~" + high + ": invalid");
            return;
        }

        System.out.print("range " + low + "~" + high + ": ");
        printRange(root, low, high);
        System.out.println();
    }

    private void printRange(RangeNode node, int low, int high) {
        if (node == null) {
            return;
        }

        // 若目前值大於 low，左子樹仍可能有符合範圍的值
        if (node.value > low) {
            printRange(node.left, low, high);
        }

        if (node.value >= low && node.value <= high) {
            System.out.print(node.value + " ");
        }

        // 若目前值小於 high，右子樹仍可能有符合範圍的值
        if (node.value < high) {
            printRange(node.right, low, high);
        }
    }
}

public class BstRangeReport {
    public static void main(String[] args) {
        RangeBst tree = new RangeBst();

        int[] values = {50, 30, 70, 20, 40, 60, 80, 35, 45, 65};

        for (int i = 0; i < values.length; i++) {
            tree.add(values[i]);
        }

        System.out.println("min=" + tree.minimum());
        System.out.println("max=" + tree.maximum());

        tree.printRange(30, 65);
        tree.printRange(20, 20);
        tree.printRange(75, 100);
        tree.printRange(70, 30);

        RangeBst empty = new RangeBst();
        System.out.println("empty min=" + empty.minimum());
        System.out.println("empty max=" + empty.maximum());
        empty.printRange(10, 20);
    }
}