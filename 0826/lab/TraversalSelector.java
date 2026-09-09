// 課堂實作題二：Traversal Selector
// 指定檔名：TraversalSelector.java。

class ExpressionNode {
    String value;
    ExpressionNode left;
    ExpressionNode right;

    ExpressionNode(String value) {
        this.value = value;
    }

    ExpressionNode(String value, ExpressionNode left, ExpressionNode right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    boolean isOperator() {
        return left != null || right != null;
    }
}

public class TraversalSelector {
    private static ExpressionNode buildExpressionTree() {
        ExpressionNode add = new ExpressionNode(
                "+",
                new ExpressionNode("a"),
                new ExpressionNode("b")
        );

        ExpressionNode subtract = new ExpressionNode(
                "-",
                new ExpressionNode("c"),
                new ExpressionNode("d")
        );

        return new ExpressionNode("*", add, subtract);
    }

    private static String preorder(ExpressionNode node) {
        if (node == null) return "";

        String left = preorder(node.left);
        String right = preorder(node.right);

        return join(node.value, left, right);
    }

    private static String inorder(ExpressionNode node) {
        if (node == null) return "";

        if (!node.isOperator()) {
            return node.value;
        }

        return "(" + inorder(node.left)
                + " " + node.value + " "
                + inorder(node.right) + ")";
    }

    private static String postorder(ExpressionNode node) {
        if (node == null) return "";

        String left = postorder(node.left);
        String right = postorder(node.right);

        return join(left, right, node.value);
    }

    private static String join(String first, String second, String third) {
        String result = "";

        if (!first.isEmpty()) result += first;
        if (!second.isEmpty()) {
            if (!result.isEmpty()) result += " ";
            result += second;
        }
        if (!third.isEmpty()) {
            if (!result.isEmpty()) result += " ";
            result += third;
        }

        return result;
    }

    public static void main(String[] args) {
        ExpressionNode root = buildExpressionTree();

        System.out.println("prefix=" + preorder(root));
        System.out.println("infix=" + inorder(root));
        System.out.println("postfix=" + postorder(root));
    }
}
