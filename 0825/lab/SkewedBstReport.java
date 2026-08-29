// 課堂實作題五：Skewed Tree Report
// 指定檔名：SkewedBstReport.java

// ============================================================
public class SkewedBstReport {
//  Node：樹的基本單位
// static 巢狀類別包含 Node 和 Bst 附在主類別裡面。
static class Node {
    int value;    // 這個 node 存的數字（key）
    Node left;    // 左邊的 node；null 代表「這裡沒有東西」
    Node right;   // 右邊的 node

    Node(int value) {
        this.value = value;
        // left、right 不指定就自動是 null
    }
}

// ---------- Bst：只裝 int 的搜尋樹 ----------
// BST property（對照概念 1）：對「每一個」node 而言，
// 左 subtree 全部 < 它 < 右 subtree 全部，且不接受 duplicate key。
static class Bst {
    Node root;
    // ※ 用 long：n 很大時比較次數加總可能超過 int 上限（約 21 億）
    long compareCount;   // search 的比較次數

    // ===== insert（對照概念 4）=====
    // 沿著 search path 找第一個 null child，把新 node 接上去，
    // 所以新加入的 node 一定先成為 leaf。
    // 注意：insert 過程的比較「不」計入 search comparison count。
    // 回傳值：true = 插入成功；false = 已存在，拒絕（講義概念 5）。
    boolean add(int value) {
        if (root == null) {              // 空樹：新 node 直接當 root
            root = new Node(value);
            return true;
        }
        Node current = root;
        while (true) {
            if (value == current.value) {
                return false;            // duplicate：拒絕，樹完全不變
            } else if (value < current.value) {
                if (current.left == null) {
                    current.left = new Node(value);  // 找到空位，接上
                    return true;
                }
                current = current.left;  // 還沒到leaf，繼續往左
            } else {
                if (current.right == null) {
                    current.right = new Node(value);
                    return true;
                }
                current = current.right; // 繼續往右
            }
        }
    }

    // 對照概念 3
    boolean contains(int target) {
        Node current = root;
        while (current != null) {
            compareCount++;              // 這一行就是「比對一次 key」
            if (target == current.value) {
                return true;             // 相等：找到了
            } else if (target < current.value) {
                current = current.left;  // 較小：只搜 left subtree
            } else {
                current = current.right; // 較大：只搜 right subtree
            }
        }
        return false;                    // 走到 null 還沒遇到：不存在
    }

    // 量測前歸零，避免累加上一次的數字
    void resetCompareCount() {
        compareCount = 0;
    }

    // 對照概念 7
    int size(Node node) {
        if (node == null) {
            return 0;                    // 空樹沒有 node
        }
        return 1 + size(node.left) + size(node.right);
    }

    // 對照概念 7 height 空樹 = 0，單一 node = 1。

    int height(Node node) {
        if (node == null) {
            return 0;
        }
        int leftHeight = height(node.left);
        int rightHeight = height(node.right);
        return 1 + Math.max(leftHeight, rightHeight);
    }
}


static void addBalancedOrder(Bst tree, int[] sorted, int lo, int hi) {
    if (lo > hi) {
        return;                  // 這一段已經沒有數字了
    }
    int mid = (lo + hi) / 2;     // 取中間當這一段的 root
    tree.add(sorted[mid]);
    addBalancedOrder(tree, sorted, lo, mid - 1);   // 左半段
    addBalancedOrder(tree, sorted, mid + 1, hi);   // 右半段
}

// 對一棵建好的樹印出 size、height 和兩種 search 量測。
// keys 必須是已排序的同一批資料（兩棵樹查同一組 key 才公平）。
static void printReport(String title, Bst tree, int[] keys) {
    // 量測一：把每個 key 都查一次，取平均比較次數
    tree.resetCompareCount();
    for (int key : keys) {
        tree.contains(key);
    }
    // 先轉成 double 再除，否則整數除法會把小數無條件捨去
    double average = (double) tree.compareCount / keys.length;

    // 量測二：單獨報「查最大值」的比較次數（skewed 版的 worst case）
    tree.resetCompareCount();
    int maxKey = keys[keys.length - 1];  // 資料已排序，最後一個就是最大
    tree.contains(maxKey);
    long maxCompare = tree.compareCount;

    System.out.println("=== " + title + " ===");
    System.out.println("size（node 數）              = " + tree.size(tree.root));
    System.out.println("height（以節點數計）         = " + tree.height(tree.root));
    System.out.println("全部 key 各查一次，平均比較  = " + average);
    System.out.println("查最大值 " + maxKey + " 的比較次數     = " + maxCompare);
    System.out.println();
}

public static void main(String[] args) {
    // n 取 2^10 - 1 = 1023：平衡版會剛好是 10 層full tree
    int n = 1023;

    // 產生 1..n（已排序）
    int[] keys = new int[n];
    for (int i = 0; i < n; i++) {
        keys[i] = i + 1;
    }

    // 樹一：排序順序 insert（1, 2, 3, ..., n）
    // 每個新值都比樹裡所有值大，一路往右接 -> skewed tree
    Bst skewedTree = new Bst();
    for (int key : keys) {
        skewedTree.add(key);
    }

    // 樹二：平衡順序 insert
    Bst balancedTree = new Bst();
    addBalancedOrder(balancedTree, keys, 0, n - 1);

    // 驗證講義概念 5：duplicate 會被拒絕，樹不變
    int sizeBefore = balancedTree.size(balancedTree.root);
    boolean added = balancedTree.add(512);   // 512 已存在（它是 root）
    System.out.println("[驗證 duplicates] add(512) 回傳 " + added
            + "，size 從 " + sizeBefore + " 變成 "
            + balancedTree.size(balancedTree.root));
    System.out.println();

    // 同一批 key，分別量兩棵樹
    printReport("排序順序建立（skewed）", skewedTree, keys);
    printReport("平衡順序建立（balanced）", balancedTree, keys);
}
}
