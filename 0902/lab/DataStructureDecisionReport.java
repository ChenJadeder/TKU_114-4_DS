//課堂實作題六：資料結構選擇報告
//指定檔名：DataStructureDecisionReport.java

import java.util.ArrayList;
import java.util.List;
// 要記得資料結構不是選哪個最快；而是哪個比較符合你的常識。
class StructureDecision {
    String requirement;
    String structure;
    String reason;
    String complexity;

    StructureDecision(String requirement, String structure,
                      String reason, String complexity) {
        this.requirement = requirement;
        this.structure = structure;
        this.reason = reason;
        this.complexity = complexity;
    }
}

public class DataStructureDecisionReport {
    static StructureDecision choose(String requirement) {
        if (requirement == null || requirement.trim().isEmpty()) {
            return new StructureDecision(
                    "UNKNOWN",
                    "UNKNOWN",
                    "需求不可為 null 或空白",
                    "N/A"
            );
        }

        return switch (requirement.trim().toUpperCase()) {
            case "INDEX_ACCESS" -> new StructureDecision(
                    requirement,
                    "ArrayList",
                    "需要依 index 快速取得元素",
                    "get(index): O(1)"
            );

            case "APPEND_LIST" -> new StructureDecision(
                    requirement,
                    "ArrayList",
                    "資料主要加在尾端，且需要保留加入順序",
                    "add(last): amortized O(1)"
            );

            case "FIFO" -> new StructureDecision(
                    requirement,
                    "ArrayDeque as Queue",
                    "先加入的資料必須先處理",
                    "offer/poll: O(1)"
            );

            case "LIFO" -> new StructureDecision(
                    requirement,
                    "ArrayDeque as Stack",
                    "最後加入的資料必須最先處理",
                    "push/pop: O(1)"
            );

            case "KEY_LOOKUP" -> new StructureDecision(
                    requirement,
                    "HashMap",
                    "需要依唯一 key 快速查詢資料",
                    "get/put: average O(1)"
            );

            case "KEY_MEMBERSHIP" -> new StructureDecision(
                    requirement,
                    "HashSet",
                    "只需判斷資料是否存在，不需要保存 value",
                    "contains/add: average O(1)"
            );

            case "SORTED_RANGE" -> new StructureDecision(
                    requirement,
                    "Balanced BST / TreeMap",
                    "需要依 key 排序，並查詢指定 key 範圍",
                    "get/put: O(log n), range: O(log n + k)"
            );

            case "SORTED_MIN_MAX" -> new StructureDecision(
                    requirement,
                    "Balanced BST / TreeSet",
                    "需要保持排序，並取得最小或最大 key",
                    "add/remove/first/last: O(log n)"
            );

            case "NEXT_PRIORITY" -> new StructureDecision(
                    requirement,
                    "PriorityQueue / Heap",
                    "每次都要處理目前優先權最高的資料",
                    "peek: O(1), offer/poll: O(log n)"
            );

            case "RELATION_TRAVERSAL" -> new StructureDecision(
                    requirement,
                    "Graph adjacency list",
                    "需要保存多對多關係並走訪相鄰 vertex",
                    "BFS/DFS: O(V + E)"
            );

            case "SHORTEST_UNWEIGHTED_PATH" -> new StructureDecision(
                    requirement,
                    "Graph adjacency list + Queue",
                    "需要找無權重 Graph 中最少 edge 的路徑",
                    "BFS: O(V + E)"
            );

            case "UNDO_OPERATION" -> new StructureDecision(
                    requirement,
                    "ArrayDeque as Stack",
                    "最新操作必須最先被復原",
                    "push/pop: O(1)"
            );

            default -> new StructureDecision(
                    requirement,
                    "UNKNOWN",
                    "找不到對應的資料結構選擇規則",
                    "N/A"
            );
        };
    }

    static List<StructureDecision> createReport(String[] requirements) {
        List<StructureDecision> result = new ArrayList<>();

        if (requirements == null) {
            return result;
        }

        for (String requirement : requirements) {
            result.add(choose(requirement));
        }

        return result;
    }

    static void printReport(List<StructureDecision> report) {
        if (report == null || report.isEmpty()) {
            System.out.println("沒有可輸出的需求資料");
            return;
        }

        for (int i = 0; i < report.size(); i++) {
            StructureDecision decision = report.get(i);

            System.out.println((i + 1) + ". requirement="
                    + decision.requirement);
            System.out.println("   structure=" + decision.structure);
            System.out.println("   reason=" + decision.reason);
            System.out.println("   Big-O=" + decision.complexity);
        }
    }

    public static void main(String[] args) {
        String[] requirements = {
                "INDEX_ACCESS",
                "APPEND_LIST",
                "FIFO",
                "LIFO",
                "KEY_LOOKUP",
                "KEY_MEMBERSHIP",
                "SORTED_RANGE",
                "SORTED_MIN_MAX",
                "NEXT_PRIORITY",
                "RELATION_TRAVERSAL",
                "SHORTEST_UNWEIGHTED_PATH",
                "UNDO_OPERATION"
        };

        printReport(createReport(requirements));

        System.out.println();
        System.out.println("unknown=" + choose("RANDOM_ACCESS_TREE").structure);

        System.out.println();
        printReport(createReport(new String[]{}));
    }
}
