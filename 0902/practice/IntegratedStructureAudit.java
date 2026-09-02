import java.util.*;
// 依測試情境判斷 List、Queue、BST、Heap、Hash Table、Graph 的使用是否合理並以程式輸出診斷
public class IntegratedStructureAudit {

    // 操作需求類型
    enum Operation {
        INDEX_ACCESS,
        FIFO,
        SORTED_RANGE,
        HIGHEST_PRIORITY,
        KEY_LOOKUP,
        RELATION_TRAVERSAL
    }

    // 可選擇的資料結構
    enum Structure {
        LIST,
        QUEUE,
        BST,
        HEAP,
        HASH_TABLE,
        GRAPH
    }

    // 保存一筆測試情境
    static class Scenario {
        String name;
        Operation operation;
        Structure selectedStructure;

        Scenario(
                String name,
                Operation operation,
                Structure selectedStructure) {

            this.name = name;
            this.operation = operation;
            this.selectedStructure = selectedStructure;
        }
    }

    // 根據主要操作需求推薦資料結構
    public static Structure recommend(Operation operation) {

        // missing operation
        if (operation == null) {
            return null;
        }

        switch (operation) {
            case INDEX_ACCESS:
                return Structure.LIST;

            case FIFO:
                return Structure.QUEUE;

            case SORTED_RANGE:
                return Structure.BST;

            case HIGHEST_PRIORITY:
                return Structure.HEAP;

            case KEY_LOOKUP:
                return Structure.HASH_TABLE;

            case RELATION_TRAVERSAL:
                return Structure.GRAPH;

            default:
                throw new IllegalStateException(
                        "未處理的操作型態: " + operation);
        }
    }

    // 說明各資料結構主要操作的時間複雜度
    public static String complexity(Structure structure) {

        if (structure == null) {
            return "N/A";
        }

        switch (structure) {
            case LIST:
                return "index access O(1), middle insert O(n)";

            case QUEUE:
                return "offer/poll O(1)";

            case BST:
                return "balanced search O(log n), worst O(n)";

            case HEAP:
                return "peek O(1), add/remove O(log n)";

            case HASH_TABLE:
                return "average key lookup O(1), worst O(n)";

            case GRAPH:
                return "BFS/DFS O(V + E)";

            default:
                return "Unknown";
        }
    }

    // 判斷選擇的資料結構是否符合操作需求，並輸出診斷
    public static void audit(Scenario scenario) {

        System.out.println("----------------------------------------");

        // missing scenario
        if (scenario == null) {
            System.out.println("【診斷結果】: 缺少測試情境");
            return;
        }

        System.out.println("【情境名稱】: " + scenario.name);

        // missing operation
        if (scenario.operation == null) {
            System.out.println("【診斷結果】: 缺少操作需求");
            return;
        }

        // missing selected structure
        if (scenario.selectedStructure == null) {
            System.out.println("【操作需求】: " + scenario.operation);
            System.out.println("【診斷結果】: 缺少選擇的資料結構");
            return;
        }

        Structure recommended = recommend(scenario.operation);

        boolean isReasonable =
                scenario.selectedStructure == recommended;

        System.out.println(
                "【操作需求】: " + scenario.operation);

        System.out.println(
                "【選擇結構】: " + scenario.selectedStructure);

        System.out.println(
                "【建議結構】: " + recommended);

        if (isReasonable) {
            System.out.println("【診斷結果】: 合理");
        } else {
            System.out.println(
                    "【診斷結果】: 不合理，建議改用 "
                    + recommended);
        }

        // 說明推薦結構的主要 Big-O
        System.out.println(
                "【Big-O】: " + complexity(recommended));
    }

    public static void main(String[] args) {

        // 一般案例：同時包含合理與故意選錯的情境
        List<Scenario> scenarios = Arrays.asList(
                new Scenario(
                        "使用者清單隨機存取",
                        Operation.INDEX_ACCESS,
                        Structure.LIST),

                new Scenario(
                        "印表機工作佇列管理",
                        Operation.FIFO,
                        Structure.HEAP), // 故意

                new Scenario(
                        "商品價格區間查詢",
                        Operation.SORTED_RANGE,
                        Structure.HASH_TABLE), // 故意

                new Scenario(
                        "醫院急診優先看診",
                        Operation.HIGHEST_PRIORITY,
                        Structure.HEAP),

                new Scenario(
                        "使用者 ID 快速查找",
                        Operation.KEY_LOOKUP,
                        Structure.HASH_TABLE),

                new Scenario(
                        "社群網路關係走訪",
                        Operation.RELATION_TRAVERSAL,
                        Structure.GRAPH)
        );

        System.out.println("=== Normal Cases ===");

        for (Scenario scenario : scenarios) {
            audit(scenario);
        }

        // missing cases
        System.out.println("\n=== Missing Cases ===");

        audit(null);

        audit(new Scenario(
                "缺少操作需求",
                null,
                Structure.QUEUE));

        audit(new Scenario(
                "缺少資料結構",
                Operation.KEY_LOOKUP,
                null));

        // empty case
        System.out.println("\n=== Empty Case ===");

        List<Scenario> emptyScenarios = new ArrayList<>();

        if (emptyScenarios.isEmpty()) {
            System.out.println("沒有待診斷的測試情境");
        } else {
            for (Scenario scenario : emptyScenarios) {
                audit(scenario);
            }
        }
    }
}
