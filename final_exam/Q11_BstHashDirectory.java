import java.util.*;

//Q11: 使用 BST 與 HashMap 雙索引管理 ID 與名稱
 //所有操作後需維持兩者索引一致

public class Q11_BstHashDirectory {
    // Node 供 BST 使用
    private static class Node {
        int id;
        String name;
        Node left;
        Node right;

        Node(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    private Node root; // BST 根節點
    private final Map<Integer, String> idToName; // HashMap 索引
    private int size; // 紀錄筆數

    public Q11_BstHashDirectory() {
        root = null;
        idToName = new HashMap<>();
        size = 0;
    }

    /**
     * 新增 ID 及對應姓名（ID 必須 > 0，name.trim() 非空）
     * @param id ID
     * @param name 姓名
     * @return 新增成功回傳 true，重複 ID 回傳 false
     */
    public boolean add(int id, String name) {
        // 檢查 ID 與 name 有效性
        if (id <= 0) return false;
        String trimmedName = name.trim();
        if (trimmedName.isEmpty()) return false;

        // 如果 ID 已存在，直接回傳 false
        if (idToName.containsKey(id)) {
            return false;
        }

        // 更新 HashMap
        idToName.put(id, trimmedName);

        // 更新 BST
        root = addToBst(root, id, trimmedName);
        size++;
        return true;
    }

    //遞迴新增節點至 BST
     
    private Node addToBst(Node node, int id, String name) {
        if (node == null) {
            return new Node(id, name);
        }

        if (id < node.id) {
            node.left = addToBst(node.left, id, name);
        } else if (id > node.id) {
            node.right = addToBst(node.right, id, name);
        }
        // 重複 ID 已由上層檢查，此處不考慮
        return node;
    }

    //依據 ID 查找姓名
    public String findName(int id) {
        return idToName.get(id); // HashMap 提供 O(1) 查找
    }

    // 移除指定 ID 的資料
    public boolean remove(int id) {
        if (!idToName.containsKey(id)) {
            return false;
        }

        // 更新 HashMap
        idToName.remove(id);

        // 更新 BST
        root = removeFromBst(root, id);
        size--;
        return true;
    }

    //運用遞迴從 BST 移除節點
     
    private Node removeFromBst(Node node, int id) {
        if (node == null) return null;

        if (id < node.id) {
            node.left = removeFromBst(node.left, id);
        } else if (id > node.id) {
            node.right = removeFromBst(node.right, id);
        } else {
            // 找到節點，依據子節樹狀況刪除
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            } else {
                // 有兩個子節點：找右子樹最小值取代
                Node minNode = findMin(node.right);
                node.id = minNode.id;
                node.name = minNode.name;
                node.right = removeFromBst(node.right, minNode.id);
            }
        }
        return node;
    }

    //找尋子樹最小值節點
    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    /**
     * 查詢指定區間 [low, high] 內的 ID 列表（遞增順序）
     * @param low 下界
     * @param high 上界
     * @return ID 列表，low > high 回傳空集合
     */
    public List<Integer> idsBetween(int low, int high) {
        if (low > high) {
            return new ArrayList<>();
        }
        List<Integer> result = new ArrayList<>();
        collectIdsInRange(root, low, high, result);
        return result;
    }

    //按中序收集符合條件的 ID
    private void collectIdsInRange(Node node, int low, int high, List<Integer> result) {
        if (node == null) return;

        if (node.id > low) {
            collectIdsInRange(node.left, low, high, result);
        }

        if (node.id >= low && node.id <= high) {
            result.add(node.id);
        }

        if (node.id < high) {
            collectIdsInRange(node.right, low, high, result);
        }
    }

    //回傳目前紀錄筆數
    public int size() {
        return size;
    }
}
