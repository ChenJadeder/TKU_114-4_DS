// 概念 6：資料結構選擇與 Big-O
// 同一系統常同時維護多種索引，例如 HashMap 依 id 查詢、PriorityQueue 取下一筆、Graph 保存路線。
public class DataStructureSelector {
    enum Requirement {
        INDEX_ACCESS, FIFO, LIFO, SORTED_RANGE,
        NEXT_PRIORITY, KEY_LOOKUP, RELATION_TRAVERSAL
    }

    static String choose(Requirement requirement) {
        if (requirement == null) return "UNKNOWN";
        return switch (requirement) {
            case INDEX_ACCESS -> "ArrayList";
            case FIFO -> "ArrayDeque as Queue";
            case LIFO -> "ArrayDeque as Stack";
            case SORTED_RANGE -> "Balanced BST / TreeMap";
            case NEXT_PRIORITY -> "Heap / PriorityQueue";
            case KEY_LOOKUP -> "HashMap";
            case RELATION_TRAVERSAL -> "Graph adjacency list";
        };
    }

    public static void main(String[] args) {
        for (Requirement requirement : Requirement.values()) {
            System.out.println(requirement + " -> " + choose(requirement));
        }
        System.out.println(choose(null));
    }
}
