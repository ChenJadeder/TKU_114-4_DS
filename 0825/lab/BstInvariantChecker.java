// 課堂實作題六：BST Validation
// 指定檔名：BstInvariantChecker.java

class IntNode {
    int value;
    IntNode left;
    IntNode right;

    IntNode(int value) {
        this.value = value;
    }
}

public class BstInvariantChecker {

    static boolean isValidBst(IntNode root) {
        return check(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    // 每個 node 都必須在合法範圍內
    static boolean check(IntNode node, long min, long max) {
        if (node == null) {
            return true;
        }

        if (node.value <= min || node.value >= max) {
            return false;
        }

        return check(node.left, min, node.value)
                && check(node.right, node.value, max);
    }

    static IntNode makeValidTree() {
        IntNode root = new IntNode(50);
        root.left = new IntNode(30);
        root.right = new IntNode(70);

        root.left.left = new IntNode(20);
        root.left.right = new IntNode(40);

        root.right.left = new IntNode(60);
        root.right.right = new IntNode(80);

        return root;
    }

    static IntNode makeInvalidTree1() {
        IntNode root = new IntNode(50);
        root.left = new IntNode(30);
        root.right = new IntNode(70);

        root.left.right = new IntNode(40);
        root.left.right.right = new IntNode(60);

        return root;
    }

    static IntNode makeInvalidTree2() {
        IntNode root = new IntNode(50);
        root.left = new IntNode(30);
        root.right = new IntNode(70);

        root.right.left = new IntNode(60);
        root.right.left.left = new IntNode(40);

        return root;
    }

    static IntNode makeInvalidTree3() {
        IntNode root = new IntNode(50);
        root.left = new IntNode(30);
        root.right = new IntNode(70);

        root.left.left = new IntNode(20);
        root.left.left.right = new IntNode(35);

        return root;
    }

    public static void main(String[] args) {
        IntNode valid = makeValidTree();
        IntNode invalid1 = makeInvalidTree1();
        IntNode invalid2 = makeInvalidTree2();
        IntNode invalid3 = makeInvalidTree3();
       /*
	`invalid1`：`60` 位於 root `50` 的左子樹中。雖然 `60 > 40`，但它必須小於 root `50`，所以違規。
	`invalid2`：`40` 位於 root `50` 的右子樹中。雖然 `40 < 60`，但它必須大於 root `50`，所以違規。
	`invalid3`：`35` 位於 `30` 的左子樹中。雖然 `35 > 20`，但它仍必須小於 `30`，所以違規。
      */
        System.out.println("valid tree=" + isValidBst(valid));
        System.out.println("invalid tree 1=" + isValidBst(invalid1));
        System.out.println("invalid tree 2=" + isValidBst(invalid2));
        System.out.println("invalid tree 3=" + isValidBst(invalid3));
        System.out.println("empty tree=" + isValidBst(null));
    }
}