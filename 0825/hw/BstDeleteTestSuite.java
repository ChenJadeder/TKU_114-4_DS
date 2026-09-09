// 課後作業四：完整 Delete 測試
// 指定檔名：BstDeleteTestSuite.java

class TestNode {
    int value;
    TestNode left;
    TestNode right;

    TestNode(int value) {
        this.value = value;
    }
}

class TestBst {
    private TestNode root;

    boolean add(int value) {
        if (root == null) {
            root = new TestNode(value);
            return true;
        }

        TestNode current = root;

        while (true) {
            if (value == current.value) {
                return false;
            }

            if (value < current.value) {
                if (current.left == null) {
                    current.left = new TestNode(value);
                    return true;
                }
                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new TestNode(value);
                    return true;
                }
                current = current.right;
            }
        }
    }

    boolean contains(int value) {
        TestNode current = root;

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

    private TestNode remove(TestNode node, int value) {
        if (node == null) {
            return null;
        }

        if (value < node.value) {
            node.left = remove(node.left, value);
        } else if (value > node.value) {
            node.right = remove(node.right, value);
        } else {
            if (node.left == null) {
                return node.right;
            }

            if (node.right == null) {
                return node.left;
            }

            TestNode successor = minimumNode(node.right);
            node.value = successor.value;
            node.right = remove(node.right, successor.value);
        }

        return node;
    }

    private TestNode minimumNode(TestNode node) {
        TestNode current = node;

        while (current.left != null) {
            current = current.left;
        }

        return current;
    }

    int size() {
        return size(root);
    }

    private int size(TestNode node) {
        if (node == null) {
            return 0;
        }

        return 1 + size(node.left) + size(node.right);
    }

    boolean isValid() {
        return isValid(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private boolean isValid(TestNode node, long min, long max) {
        if (node == null) {
            return true;
        }

        if (node.value <= min || node.value >= max) {
            return false;
        }

        return isValid(node.left, min, node.value)
                && isValid(node.right, node.value, max);
    }

    String inorderText() {
        StringBuilder result = new StringBuilder();
        inorderText(root, result);
        return result.toString().trim();
    }

    private void inorderText(TestNode node, StringBuilder result) {
        if (node == null) {
            return;
        }

        inorderText(node.left, result);
        result.append(node.value).append(" ");
        inorderText(node.right, result);
    }

    void printState(String label) {
        System.out.println(label
                + " inorder=[" + inorderText() + "]"
                + " size=" + size()
                + " valid=" + isValid());
    }
}

public class BstDeleteTestSuite {

    static void addAll(TestBst tree, int[] values) {
        for (int i = 0; i < values.length; i++) {
            tree.add(values[i]);
        }
    }

    public static void main(String[] args) {
        // 1. Empty tree
        TestBst empty = new TestBst();
        System.out.println("empty remove 10=" + empty.remove(10));
        empty.printState("empty");

        // 2. Missing value
        TestBst missing = new TestBst();
        addAll(missing, new int[]{50, 30, 70});
        System.out.println("missing remove 999=" + missing.remove(999));
        missing.printState("missing");

        // 3. Single root
        TestBst single = new TestBst();
        single.add(50);
        System.out.println("single remove 50=" + single.remove(50));
        single.printState("single after remove");

        // 4. Root with one child
        TestBst oneChild = new TestBst();
        oneChild.add(50);
        oneChild.add(30);
        System.out.println("one child root remove 50=" + oneChild.remove(50));
        oneChild.printState("one child after remove");

        // 5. Root with two children
        TestBst twoChildren = new TestBst();
        addAll(twoChildren, new int[]{50, 30, 70, 20, 40, 60, 80});
        System.out.println("two children root remove 50="
                + twoChildren.remove(50));
        twoChildren.printState("two children after remove");

        // 6. Delete continuously until empty
        TestBst continuous = new TestBst();
        addAll(continuous, new int[]{50, 30, 70, 20, 40, 60, 80});

        int[] deleteOrder = {20, 30, 50, 70, 40, 60, 80};

        for (int i = 0; i < deleteOrder.length; i++) {
            int value = deleteOrder[i];
            System.out.println("continuous remove " + value
                    + "=" + continuous.remove(value));
            continuous.printState("after " + value);
        }

        System.out.println("remove after empty="
                + continuous.remove(999));
    }
}