import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
//課後作業六：Traversal 測試報告
//檔名：TraversalTestReport.java
class TestNode {
    String val;
    TestNode left;
    TestNode right;

    TestNode(String val) {
        this.val = val;
    }
}

public class TraversalTestReport {

    // 四種走訪核心方法
    public static List<String> preorder(TestNode n) {
        List<String> res = new ArrayList<>();
        preHelper(n, res);
        return res;
    }
    private static void preHelper(TestNode n, List<String> res) {
        if (n == null) return;
        res.add(n.val);
        preHelper(n.left, res);
        preHelper(n.right, res);
    }

    public static List<String> inorder(TestNode n) {
        List<String> res = new ArrayList<>();
        inHelper(n, res);
        return res;
    }
    private static void inHelper(TestNode n, List<String> res) {
        if (n == null) return;
        inHelper(n.left, res);
        res.add(n.val);
        inHelper(n.right, res);
    }

    public static List<String> postorder(TestNode n) {
        List<String> res = new ArrayList<>();
        postHelper(n, res);
        return res;
    }
    private static void postHelper(TestNode n, List<String> res) {
        if (n == null) return;
        postHelper(n.left, res);
        postHelper(n.right, res);
        res.add(n.val);
    }

    public static List<String> levelOrder(TestNode n) {
        List<String> res = new ArrayList<>();
        if (n == null) return res;
        Queue<TestNode> q = new ArrayDeque<>();
        q.offer(n);
        while (!q.isEmpty()) {
            TestNode curr = q.poll();
            res.add(curr.val);
            if (curr.left != null) q.offer(curr.left);
            if (curr.right != null) q.offer(curr.right);
        }
        return res;
    }

    private static void runTest(String treeName, TestNode root, 
                                List<String> expPre, List<String> expIn, 
                                List<String> expPost, List<String> expLevel) {
        System.out.println("=== Test Case: " + treeName + " ===");
        
        List<String> actPre = preorder(root);
        List<String> actIn = inorder(root);
        List<String> actPost = postorder(root);
        List<String> actLevel = levelOrder(root);

        System.out.println("Preorder   | Exp: " + expPre + " | Act: " + actPre + " | Match: " + expPre.equals(actPre));
        System.out.println("Inorder    | Exp: " + expIn + " | Act: " + actIn + " | Match: " + expIn.equals(actIn));
        System.out.println("Postorder  | Exp: " + expPost + " | Act: " + actPost + " | Match: " + expPost.equals(actPost));
        System.out.println("LevelOrder | Exp: " + expLevel + " | Act: " + actLevel + " | Match: " + expLevel.equals(actLevel));
        System.out.println();
    }

    public static void main(String[] args) {
        // 1. Empty Tree
        runTest("Empty Tree", null, 
                Arrays.asList(), Arrays.asList(), Arrays.asList(), Arrays.asList());

        // 2. Single-Node Tree
        runTest("Single-Node Tree", new TestNode("A"), 
                Arrays.asList("A"), Arrays.asList("A"), Arrays.asList("A"), Arrays.asList("A"));

        // 3. Only-Left Tree (A -> B -> C)
        TestNode onlyLeft = new TestNode("A");
        onlyLeft.left = new TestNode("B");
        onlyLeft.left.left = new TestNode("C");
        runTest("Only-Left Tree", onlyLeft, 
                Arrays.asList("A", "B", "C"), Arrays.asList("C", "B", "A"), 
                Arrays.asList("C", "B", "A"), Arrays.asList("A", "B", "C"));

        // 4. Only-Right Tree (A -> B -> C)
        TestNode onlyRight = new TestNode("A");
        onlyRight.right = new TestNode("B");
        onlyRight.right.right = new TestNode("C");
        runTest("Only-Right Tree", onlyRight, 
                Arrays.asList("A", "B", "C"), Arrays.asList("A", "B", "C"), 
                Arrays.asList("C", "B", "A"), Arrays.asList("A", "B", "C"));

        // 5. Complete Tree
        TestNode complete = new TestNode("A");
        complete.left = new TestNode("B");
        complete.right = new TestNode("C");
        runTest("Complete Tree", complete, 
                Arrays.asList("A", "B", "C"), Arrays.asList("B", "A", "C"), 
                Arrays.asList("B", "C", "A"), Arrays.asList("A", "B", "C"));

        // 6. Irregular Tree
        TestNode irregular = new TestNode("A");
        irregular.left = new TestNode("B");
        irregular.left.right = new TestNode("D");
        irregular.right = new TestNode("C");
        runTest("Irregular Tree", irregular, 
                Arrays.asList("A", "B", "D", "C"), Arrays.asList("B", "D", "A", "C"), 
                Arrays.asList("D", "B", "C", "A"), Arrays.asList("A", "B", "C", "D"));
    }
}
