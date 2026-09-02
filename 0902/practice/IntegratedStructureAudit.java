import java.util.*;
//依測試情境判斷 List、Queue、BST、Heap、Hash Table、Graph 的使用是否合理並以程式輸出診斷
public class IntegratedStructureAudit {

    enum Operation {
        INDEX_ACCESS,
        FIFO,
        SORTED_RANGE,
        HIGHEST_PRIORITY,
        KEY_LOOKUP,
        RELATION_TRAVERSAL
    }

    enum Structure {
        LIST,
        QUEUE,
        BST,
        HEAP,
        HASH_TABLE,
        GRAPH
    }

    // 保存測試情境
    static class Scenario {
        String name;
        Operation operation;
        Structure selected;

        Scenario(String name, Operation operation, Structure selected) {
            this.name = name;
            this.operation = operation;
            this.selected = selected;
        }
    }

    // 根據操作需求推薦資料結構
    public static Structure recommend(Operation operation) {
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
                return null;
        }
    }

    // 回傳推薦結構的主要 Big-O
    public static String complexity(Structure structure) {
        if (structure == null) {
            return "N/A";
        }

        switch (structure) {
            case LIST:
                return "get O(1), middle insert O(n)";
            case QUEUE:
                return "offer/poll O(1)";
            case BST:
                return "balanced O(log n), worst O(n)";
            case HEAP:
                return "peek O(1), add/remove O(log n)";
            case HASH_TABLE:
                return "average lookup O(1), worst O(n)";
            case GRAPH:
                return "BFS/DFS O(V+E)";
            default:
                return "N/A";
        }
    }

    // 比較實際選擇和建議結構
    public static void audit(Scenario scenario) {
        if (scenario == null || scenario.operation == null) {
            System.out.println("Missing scenario or operation");
            return;
        }

        Structure recommended = recommend(scenario.operation);

        System.out.println("Scenario: " + scenario.name);
        System.out.println("Selected: " + scenario.selected);
        System.out.println("Recommended: " + recommended);

        if (scenario.selected == recommended) {
            System.out.println("Result: reasonable");
        } else {
            System.out.println("Result: unreasonable");
        }

        System.out.println("Big-O: " + complexity(recommended));
        System.out.println();
    }

    public static void main(String[] args) {

        List<Scenario> tests = Arrays.asList(
            new Scenario("Random index access",
                    Operation.INDEX_ACCESS, Structure.LIST),

            new Scenario("Printer jobs",
                    Operation.FIFO, Structure.HEAP),

            new Scenario("Price range",
                    Operation.SORTED_RANGE, Structure.BST),

            new Scenario("Emergency priority",
                    Operation.HIGHEST_PRIORITY, Structure.HEAP),

            new Scenario("ID lookup",
                    Operation.KEY_LOOKUP, Structure.HASH_TABLE),

            new Scenario("Social network",
                    Operation.RELATION_TRAVERSAL, Structure.GRAPH)
        );

        // 一般案例
        for (Scenario test : tests) {
            audit(test);
        }

        // missing case
        audit(null);

        // empty case
        List<Scenario> emptyTests = new ArrayList<>();
        System.out.println("Empty test count: " + emptyTests.size());
    }
}
