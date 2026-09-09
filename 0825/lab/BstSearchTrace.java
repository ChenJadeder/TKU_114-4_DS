// 課堂實作題一：BST Search Trace
// 指定檔名：BstSearchTrace.java

class TraceNode {
    int value;
    TraceNode left;
    TraceNode right;

    TraceNode(int value) {
        this.value = value;
    }
}

class TraceBst {
    private TraceNode root;

    boolean add(int value) {
        if (root == null) {
            root = new TraceNode(value);
            return true;
        }

        TraceNode current = root;

        while (true) {
            if (value == current.value) {
                return false;
            }

            if (value < current.value) {
                if (current.left == null) {
                    current.left = new TraceNode(value);
                    return true;
                }
                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new TraceNode(value);
                    return true;
                }
                current = current.right;
            }
        }
    }

    boolean searchTrace(int target) {
        TraceNode current = root;
        int comparisons = 0;

        while (current != null) {
            comparisons++;
            System.out.println("current=" + current.value);

            if (target == current.value) {
                System.out.println("found " + target
                        + ", comparisons=" + comparisons);
                return true;
            }

            if (target < current.value) {
                System.out.println("direction=left");
                current = current.left;
            } else {
                System.out.println("direction=right");
                current = current.right;
            }
        }

        System.out.println("missing " + target
                + ", comparisons=" + comparisons);
        return false;
    }
}

public class BstSearchTrace {
    public static void main(String[] args) {
        TraceBst tree = new TraceBst();

        int[] values = {50, 30, 70, 20, 40, 60, 80};
        for (int i = 0; i < values.length; i++) {
            tree.add(values[i]);
        }

        System.out.println("--- find root ---");
        System.out.println("result=" + tree.searchTrace(50));

        System.out.println("--- find leaf ---");
        System.out.println("result=" + tree.searchTrace(20));

        System.out.println("--- find internal node ---");
        System.out.println("result=" + tree.searchTrace(70));

        System.out.println("--- find missing value ---");
        System.out.println("result=" + tree.searchTrace(65));

        System.out.println("--- empty tree ---");
        TraceBst empty = new TraceBst();
        System.out.println("result=" + empty.searchTrace(10));
    }
}