public class BstOperationAudit {

    static class Node {
        int value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
        }
    }

    static class Bst {
        Node root;
        private boolean found;

        // 新增節點（拒絕 duplicate）
        boolean add(int value) {
            if (root == null) {
                root = new Node(value);
                return true;
            }

            Node cur = root;
            while (true) {
                if (value == cur.value) {
                    return false;
                }
                if (value < cur.value) {
                    if (cur.left == null) {
                        cur.left = new Node(value);
                        return true;
                    }
                    cur = cur.left;
                } else {
                    if (cur.right == null) {
                        cur.right = new Node(value);
                        return true;
                    }
                    cur = cur.right;
                }
            }
        }

        // 刪除指定值的節點
        boolean remove(int value) {
            found = false;
            root = removeNode(root, value);
            return found;
        }

        private Node removeNode(Node node, int value) {
            if (node == null) {
                return null;
            }

            if (value < node.value) {
                node.left = removeNode(node.left, value);
            } else if (value > node.value) {
                node.right = removeNode(node.right, value);
            } else {
                found = true;
                if (node.left == null) return node.right;
                if (node.right == null) return node.left;

                // Two-child case：使用右子樹最小值 (inorder successor)
                Node successor = node.right;
                while (successor.left != null) {
                    successor = successor.left;
                }
                node.value = successor.value;
                node.right = removeNode(node.right, successor.value);
            }
            return node;
        }

        // 中序走訪
        String inorder() {
            StringBuilder sb = new StringBuilder();
            inorderHelper(root, sb);
            return sb.toString().trim();
        }

        private void inorderHelper(Node node, StringBuilder sb) {
            if (node == null) return;
            inorderHelper(node.left, sb);
            sb.append(node.value).append(' ');
            inorderHelper(node.right, sb);
        }

        // 節點總數
        int size(Node node) {
            return node == null ? 0 : 1 + size(node.left) + size(node.right);
        }

        // 樹的高度
        int height(Node node) {
            if (node == null) return 0;
            return 1 + Math.max(height(node.left), height(node.right));
        }

        // 全域驗證 BST 合法性
        boolean isValid() {
            return isValidHelper(root, Long.MIN_VALUE, Long.MAX_VALUE);
        }

        private boolean isValidHelper(Node node, long low, long high) {
            if (node == null) return true;
            if (node.value <= low || node.value >= high) return false;
            return isValidHelper(node.left, low, node.value)
                && isValidHelper(node.right, node.value, high);
        }
    }

    static void audit(Bst tree, String operation, boolean result) {
        System.out.println("operation = " + operation);
        System.out.println("  result   = " + result);
        System.out.println("  inorder  = " + tree.inorder());
        System.out.println("  size     = " + tree.size(tree.root));
        System.out.println("  height   = " + tree.height(tree.root));
        System.out.println("  valid    = " + tree.isValid());
        System.out.println();
    }

    public static void main(String[] args) {
        Bst tree = new Bst();

        int[] build = {50, 30, 70, 20, 40, 60, 80};
        for (int v : build) {
            tree.add(v);
        }
        audit(tree, "建立 50,30,70,20,40,60,80", true);

        audit(tree, "add(30) duplicate", tree.add(30));
        audit(tree, "remove(99) missing", tree.remove(99));
        audit(tree, "remove(20) leaf", tree.remove(20));
        audit(tree, "remove(30) one child", tree.remove(30));
        audit(tree, "remove(50) two children(root)", tree.remove(50));

        audit(tree, "remove(40) leaf", tree.remove(40));
        audit(tree, "remove(60) one child(root)", tree.remove(60));
        audit(tree, "remove(70) one child(root)", tree.remove(70));
        audit(tree, "remove(80) leaf(root)", tree.remove(80));

        audit(tree, "remove(1) on empty tree", tree.remove(1));
        audit(tree, "add(100) 重新開始", tree.add(100));
    }
}
